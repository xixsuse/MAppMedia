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

    /**
     * Static builder class that constructs the FTPDownloadAsyncTask object instance.
     */
    public static class Builder {

        /**
         * Holds the FTPDownloadAsyncTask instance that will be constructed
         * by this builder
         */
        private FTPDownloadAsyncTask ftpDownloadAsyncTask;

        /**
         * Holds the host address of the target FTP server
         */
        private String host;

        /**
         * Holds the user name to connect to the server with
         */
        private String user;

        /**
         * Holds the password that belongs to the user name
         */
        private String password;

        /**
         * Holds the path to the target file of the FTP server
         */
        private String remoteFilePath;

        /**
         * Holds the path to the local file of the device
         */
        private String localFilePath;

        /**
         * The constructor of Builder
         *
         * @param context - context of the application
         */
        public Builder(Context context) {
            ftpDownloadAsyncTask = new FTPDownloadAsyncTask(context);
        }

        /**
         * Sets the host to this FTPDownloadAsyncTask instance.
         *
         * @param host - the host
         * @return builder
         */
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * Sets the user to this FTPDownloadAsyncTask instance.
         *
         * @param user - the user
         * @return builder
         */
        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets the password to this FTPDownloadAsyncTask instance.
         *
         * @param password - the password
         * @return builder
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the remote file path to this FTPDownloadAsyncTask instance.
         *
         * @param remoteFilePath - the remote file path
         * @return builder
         */
        public Builder setRemoteFilePath(String remoteFilePath) {
            this.remoteFilePath = remoteFilePath;
            return this;
        }

        /**
         * Sets the local file path to this FTPDownloadAsyncTask instance.
         *
         * @param localFilePath - the local file path
         * @return builder
         */
        public Builder setLocalFilePath(String localFilePath) {
            this.localFilePath = localFilePath;
            return this;
        }

        /**
         * Sets the status listener to this FTPDownloadAsyncTask instance.
         *
         * @param statusListener - the status listener
         * @return builder
         */
        public Builder setStatusListener(OnStatusListener statusListener) {
            ftpDownloadAsyncTask.statusListener = statusListener;
            return this;
        }

        /**
         * Returns whether or not all necessary data for FTP connection are set.
         *
         * @return whether or not all data are set
         */
        private boolean areNecessaryDataSet() {
            return host != null && user != null && password != null
                    && remoteFilePath != null && localFilePath != null;
        }

        /**
         * Executes the task of downloading the targeted file.
         *
         * @return whether or not the task was executed
         */
        public boolean execute() {
            if(!areNecessaryDataSet()) {
                return false;
            }
            ftpDownloadAsyncTask.execute(host, user, password, remoteFilePath, localFilePath);
            return true;
        }

    }

    /**
     * Is called when the task gets prepared for working in background.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ftpClient = new FTPClient();
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }


    /**
     * Main method. Is called when executing the main task of this class. Connects to
     * the passed server and tries to download the targeted file.
     *
     * @param params - the params for connection
     * @return don't matter :D
     */
    @Override
    protected Void doInBackground(String... params) {
        connect(params[0], params[1], params[2]);
        downloadFile(params[3], params[4]);
        return null;
    }

    /**
     * Is called when the main task of this class was executed.
     * @param aVoid - don't matter
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    /**
     * Connects to a FTP server with the passed connection data.
     * If no connection is made, the method fires events to the status
     * listener.
     *
     * @param host -  the host to connect to
     * @param user - the user to connect with
     * @param password - the password that belongs to the users account
     */
    private void connect(String host, String user, String password) {
        int reply;
        try {
            ftpClient.connect(host);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                onFailDisconnectEvent(statusListener);
            } else {
                ftpClient.login(user, password);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
            }
        } catch(IOException e) {
            onConnectionFailed(statusListener);
            e.printStackTrace();
        }
    }

    /**
     * Downloads the targeted file of the FTP server to the device.
     * If the download is failed, the method fires events to the status
     * listener.
     *
     * @param remoteFilePath - the remote path where the file lies on the server
     * @param localFilePath - the local path where to store the file on
     */
    private void downloadFile(String remoteFilePath, String localFilePath) {
        try  {
            FileOutputStream fos = context.openFileOutput(localFilePath, Context.MODE_PRIVATE);
            ftpClient.retrieveFile(remoteFilePath, fos);
            fos.close();
            onDownloadDoneEvent(statusListener);
        } catch (IOException e) {
            onDownloadFileFailed(statusListener);
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the current open FTP connection.
     */
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

    /**
     * Fires the event onDownloadDone if the passed status listener is not null.
     *
     * @param statusListener - the status listener to fire the event on
     */
    private static void onDownloadDoneEvent(OnStatusListener statusListener) {
        if(statusListener != null) {
            statusListener.onDownloadDone();
        }
    }

    /**
     * Fires the event onFailDisconnect if the passed status listener is not null.
     *
     * @param statusListener - the status listener to fire the event on
     */
    private static void onFailDisconnectEvent(OnStatusListener statusListener) {
        if(statusListener != null) {
            statusListener.onFailDisconnect();
        }
    }

    /**
     * Fires the event onConnectionFailed if the passed status listener is not null.
     *
     * @param statusListener - the status listener to fire the event on
     */
    private static void onConnectionFailed(OnStatusListener statusListener) {
        if(statusListener != null) {
            statusListener.onConnectionFailed();
        }
    }

    /**
     * Fires the event onDownloadFileFailed if the passed status listener is not null.
     *
     * @param statusListener - the status listener to fire the event on
     */
    private static void onDownloadFileFailed(OnStatusListener statusListener) {
        if(statusListener != null) {
            statusListener.onDownloadFileFailed();
        }
    }

    /**
     * The OnStatusListener interface
     */
    public static interface OnStatusListener {
        public void onDownloadDone();
        public void onDownloadFileFailed();
        public void onConnectionFailed();
        public void onFailDisconnect();
    }


}
