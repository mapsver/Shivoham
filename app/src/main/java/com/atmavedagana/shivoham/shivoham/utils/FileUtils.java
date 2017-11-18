package com.atmavedagana.shivoham.shivoham.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.atmavedagana.shivoham.shivoham.AVGMediaItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiv on 11/8/2017.
 */

public class FileUtils {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String JSON_EXT = ".json";
    private static String PDF_EXT = ".pdf";
    private static String MP3_EXT = ".mp3";

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permissionR = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionW = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionR != PackageManager.PERMISSION_GRANTED
                || permissionW != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static String getMediaStorageRootFolder(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    public static ArrayList<AVGMediaItem> getAllMediaItemNames(String dirPath) {
        ArrayList<AVGMediaItem> returnList = new ArrayList<>();

        File root = new File(dirPath);
        File[] files = root.listFiles();

        for (File f : files) {
            if (f.isFile() && f.getPath().endsWith(".json")) {
                String mediaName = f.getPath().replaceFirst("[.][^.]+$", "");
                File pdfFile = new File(mediaName + PDF_EXT);
                File mp3File = new File(mediaName + MP3_EXT);
                String mediaCoreFileName = f.getName();

                if (pdfFile.isFile() && mp3File.isFile()) {
                    returnList.add(new AVGMediaItem("Medha Suktam", "VE410", "", "", "", f.getPath(), mp3File.getPath(), pdfFile.getPath()));
                }
            }
        }

        return returnList;
    }
}
