package rs.de.mappmedia.ftp;


import android.content.Context;
import android.os.AsyncTask;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.ftp
 * Class:      FTPDownloader
 */

public class FTPDownloader extends AsyncTask<String, Void, Void> {

    private FTPClient ftpClient;
    private Context context;

    public FTPDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ftpClient = new FTPClient();
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }


    @Override
    protected Void doInBackground(String... params) {
        connect(params[0], params[1], params[2]);
        downloadFile(params[3], params[4]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        disconnect();
        OnDownloadDoneListener listener = ((OnDownloadDoneListener)context);
        if(listener != null) {
            listener.onDownloadDone();
        }
    }

    private void connect(String host, String user, String password) {
        int reply;
        try {
            ftpClient.connect(host);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            } else {
                ftpClient.login(user, password);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String remoteFilePath, String localFilePath) {
        try  {
            FileOutputStream fos = context.openFileOutput(localFilePath, Context.MODE_PRIVATE);
            ftpClient.retrieveFile(remoteFilePath, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static interface OnDownloadDoneListener {
        public void onDownloadDone();
    }


}
