package com.atmavedagana.shivoham.shivoham;

import com.atmavedagana.shivoham.shivoham.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiv on 11/8/2017.
 */

public class AVGMediaBrowser {

    public enum BROWSE_TYPE { BROWSE_LOCAL_ONLY , BROWSE_DROPBOX_AND_LOCAL };
    private String mlocalDirPath;

    AVGMediaBrowser(String browseRootDir) {
        mlocalDirPath = browseRootDir;
    }

    ArrayList<AVGMediaItem> getMediaItems(BROWSE_TYPE browseType) {
        //tempFunToDownloadMedhaSuktam();

        ArrayList<AVGMediaItem> localList =  getLocalMediaItemNames();
        ArrayList<AVGMediaItem> dropboxList =  getDropBoxMediaItemNames();

        localList.addAll(dropboxList);

        return localList;
    }

    ArrayList<AVGMediaItem> getLocalMediaItemNames() {
        return FileUtils.getAllMediaItemNames(mlocalDirPath);
    }

    ArrayList<AVGMediaItem> getDropBoxMediaItemNames() {
        return new ArrayList<AVGMediaItem>();
    }

    void tempFunToDownloadMedhaSuktam() {
        String mlocalJsonFileDownloadPath = null;
        String mlocalPDFFileDownloadPath = null;
        String mlocalAudioFileDownloadPath = null;
        String mdropBoxJSONFileDownloadPath = null;
        String mdropBoxPDFFileDownloadPath = null;
        String mdropBoxAudioFileDownloadPath = null;
        BaseDownloader mDownloaderClient;

        mlocalJsonFileDownloadPath = mlocalDirPath + "/firstDownloadJGD.json";
        mlocalPDFFileDownloadPath = mlocalDirPath + "/firstDownloadJGD.pdf";
        mlocalAudioFileDownloadPath = mlocalDirPath + "/firstDownloadJGD.mp3";

        mdropBoxJSONFileDownloadPath = "/MedhaSuktam/MedhaSuktam.json";
        mdropBoxAudioFileDownloadPath = "/MedhaSuktam/MedhaSuktam.mp3";
        mdropBoxPDFFileDownloadPath = "/MedhaSuktam/MedhaSuktam.pdf";

        mDownloaderClient = new DropBoxDownloader();
        mDownloaderClient.setupDownloaderClient();

        // Get files and folder metadata from Dropbox root directory
        AVGMediaItem.DownloadFileSet medhaFilesFromDropBox = new AVGMediaItem.DownloadFileSet(
                mdropBoxJSONFileDownloadPath, mdropBoxAudioFileDownloadPath, mdropBoxPDFFileDownloadPath,
                mlocalJsonFileDownloadPath, mlocalAudioFileDownloadPath, mlocalPDFFileDownloadPath);

        mDownloaderClient.downloadFileSet(medhaFilesFromDropBox);
    }
}
