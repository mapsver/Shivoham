/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atmavedagana.shivoham.shivoham;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.atmavedagana.shivoham.shivoham.utils.FileUtils;

import java.io.File;

//import com.dropbox.client2.DropboxAPI;
//import com.dropbox.client2.ProgressListener;
//import com.dropbox.client2.android.AndroidAuthSession;
//import com.dropbox.client2.session.AppKeyPair;


public class MainActivity extends AppCompatActivity {

    private String mlocalJsonFileDownloadPath = null;
    private String mlocalPDFFileDownloadPath = null;
    private String mlocalAudioFileDownloadPath = null;
    private String mdropBoxPDFFileDownloadPath = null;
    private String mdropBoxAudioFileDownloadPath = null;

    public static String mlocalDirPath = null;


    private TextView mtv_results;

    private BaseDownloader mDownloaderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileUtils.verifyStoragePermissions(this);

        mtv_results = (TextView) findViewById(R.id.tv_results);

        mlocalDirPath = FileUtils.getMediaStorageRootFolder(this);
        mlocalPDFFileDownloadPath = mlocalDirPath + "/firstPDFDownloadJGD.pdf";
        mlocalAudioFileDownloadPath = mlocalDirPath + "/firstAudioDownloadJGD.mp3";

        mdropBoxAudioFileDownloadPath = "/MedhaSuktam/MedhaSuktam.mp3";
        mdropBoxPDFFileDownloadPath = "/MedhaSuktam/MedhaSuktam lesson.pdf";

        mDownloaderClient = new DropBoxDownloader();
        mDownloaderClient.setupDownloaderClient();

    }

    public void onClickDownloadFromDropboxButton(View v) {

    }

    public void openPDFV2(String downloadFilePath) {
        String[] strArr = new String[2];
        strArr[0] = downloadFilePath;
        strArr[1] = mlocalAudioFileDownloadPath;
        Intent openPDFIntent = new Intent(this, PDF_Activity.class);
        openPDFIntent.putExtra(Intent.ACTION_OPEN_DOCUMENT, strArr);

        startActivity(openPDFIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickOpenPDF(View v) {
        try {
            File file = new File(mlocalPDFFileDownloadPath);
            File audfile = new File(mlocalAudioFileDownloadPath);

            if (audfile == null || !audfile.exists()) {
                new AlertDialog.Builder(this)
                        .setMessage("JGD ! Audio file does'nt exist ! Unable to proceed.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return;
            }

            if (file != null && file.exists()) {
                //  OpenPDFInNewActivity(mlocalPDFFileDownloadPath + ".pdf");
                openPDFV2(mlocalPDFFileDownloadPath);
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("JGD ! PDF  file does'nt exist ! Unable to proceed.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}