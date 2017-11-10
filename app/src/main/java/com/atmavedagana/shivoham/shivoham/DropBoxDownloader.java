package com.atmavedagana.shivoham.shivoham;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadErrorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by shiv on 10/26/2017.
 */

public class DropBoxDownloader extends BaseDownloader {

    private DbxClientV2 mDropBoxclient = null;
    private String accessToken = "A2BJyMofitAAAAAAAAAADyP4R4YBUNRbbFgaH8KlMMKktJIfI6vVWrE5366Ilxyw";

    @Override
    public void setupDownloaderClient() {
        DbxRequestConfig config = new DbxRequestConfig("T04b-03-Exercise-ShareText");
        String clientID = config.getClientIdentifier();
        mDropBoxclient = new DbxClientV2(config, accessToken); //Resources.getSystem().getString(R.string.ACCESSTOKEN));
    }

    @Override
    public void downloadFile(String fromPath, String toPath, boolean overrideExistingFile) {
        File file = new File(toPath);
        if (file.exists()) {
            if (overrideExistingFile)
                file.delete();
            else
                return;
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            mDropBoxclient.files().download(fromPath)
                    .download(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DownloadErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getListOfFileNames() {

    }
}
