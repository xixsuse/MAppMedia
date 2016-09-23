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
 * Class:      FTPDownloadAsyncTask
 */

public class FTPDownloadAsyncTask extends AsyncTask<String, Void, Void> {

    /**
     * Holds the instance of the ftp client containing the necessary data
     * to connect to the FTP server
     */
    private FTPClient ftpClient;

    /**
     * Holds the context instance of the application
     */
    private Context context;

    /**
     * Holds the listener that listens to the status of this async task
     */
    private OnStatusListener statusListener;

    /**
     * The private constructor of FTPDownloadAsyncTask, in order to only get
     * a built instance by the Builder.
     *
     * @param context - the context of the application
     */
    private FTPDownloadAsyncTask(Context context) {
        this.context = context;
    }

    public static class Builder {

        private FTPDownloadAsyncTask ftpDownloadAsyncTask;

        private String host;
        private String user;
        private String password;
        private String remoteFilePath;
        private String localFilePath;

        public Builder(Context context) {
            ftpDownloadAsyncTask = new FTPDownloadAsyncTask(context);
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRemoteFilePath(String remoteFilePath) {
            this.remoteFilePath = remoteFilePath;
            return this;
        }

        public Builder setLocalFilePath(String localFilePath) {
            this.localFilePath = localFilePath;
            return this;
        }

        public Builder setStatusListener(OnStatusListener statusListener) {
            ftpDownloadAsyncTask.statusListener = statusListener;
            return this;
        }

        private boolean isNecessaryDataSet() {
            return host != null && user != null && password != null
                    && remoteFilePath != null && localFilePath != null;
        }

        public boolean execute() {
            if(!isNecessaryDataSet()) {
                return false;
            }
            ftpDownloadAsyncTask.execute(host, user, password, remoteFilePath, localFilePath);
            return true;
        }

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
        onDownloadDoneEvent(statusListener);
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

    private static void onDownloadDoneEvent(OnStatusListener statusListener) {
        if(statusListener != null) {
            statusListener.onDownloadDone();
        }
    }

    public static interface OnStatusListener {
        public void onDownloadDone();
    }


}
