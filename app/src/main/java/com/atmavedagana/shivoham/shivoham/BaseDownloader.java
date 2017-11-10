package com.atmavedagana.shivoham.shivoham;

import android.os.AsyncTask;

/**
 * Created by shiv on 10/26/2017.
 */



public abstract class BaseDownloader {

    public abstract void setupDownloaderClient();
    public abstract void downloadFile(String fromPath, String toPath, boolean overrideExistingFile);
    public abstract void getListOfFileNames();

    public void downloadFileSet(AVGMediaItem.DownloadFileSet downloadFileSet) {
        DownloaderAsyncTask downloaderAsyncTask = new DownloaderAsyncTask();
        downloaderAsyncTask.execute(downloadFileSet);
    }

    public class DownloaderAsyncTask extends AsyncTask<AVGMediaItem.DownloadFileSet, Void, String> {

        @Override
        protected String doInBackground(AVGMediaItem.DownloadFileSet... paths) {
            AVGMediaItem.DownloadFileSet listOfFiles = paths[0];

            // Download the file.
            if (listOfFiles.getmJsonFileGetFromPath() != "" )
                downloadFile(listOfFiles.getmJsonFileGetFromPath(), listOfFiles.getmJsonFileSaveToPath(), true);

            if (listOfFiles.getmPDFFileGetFromPath() != "" )
                downloadFile(listOfFiles.getmPDFFileGetFromPath(), listOfFiles.getmPDFFileSaveToPath(), true);

            if (listOfFiles.getmAudioFileGetFromPath() != "" )
                downloadFile(listOfFiles.getmAudioFileGetFromPath(), listOfFiles.getmAudioFileSaveToPath(), true);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
}
