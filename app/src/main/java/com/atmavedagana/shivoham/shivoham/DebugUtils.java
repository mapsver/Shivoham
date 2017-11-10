package com.atmavedagana.shivoham.shivoham;

/**
 * Created by shiv on 10/24/2017.
 */

public class DebugUtils {

    /*
    public void setupTouchMaskListenerForXAndY() {
        mtv_touchMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mCurrTouchDownX = motionEvent.getX();
                    mCurrTouchDownY = motionEvent.getY();

                    mCurrentPlayPostion = player.getContentPosition() / 10;

                    player.setPlayWhenReady(true);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mCurrTouchUpX = motionEvent.getX();
                    mCurrTouchUpY = motionEvent.getY();
                    showCoordsUponClick();

                    createAndAddMarkerUsingSelection();


                    player.setPlayWhenReady(false);
                }
                return true;
            }
        });
    }

    public void setupTouchMaskListenerDebug() {
    //debug
        mtv_touchMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mCurrTouchDownX = motionEvent.getX();
                    mCurrTouchDownY = motionEvent.getY();

                    //mCurrentPlayPostion = player.getContentPosition() / 10;
                    //player.setPlayWhenReady(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mCurrTouchUpX = motionEvent.getX();
                    mCurrTouchUpY = motionEvent.getY();
                    showCoordsUponClick();

                    HighlightBox hb = createMarkerUsingSelection();
                    DrawDebugBox a = new DrawDebugBox(hb);
                    a.highlightText();

                    //createAndAddMarkerUsingSelection();
                    //player.setPlayWhenReady(false);
                }
                return true;
            }
        });
    }




    static DropboxAPI<AndroidAuthSession> dropboxAPI;
    private AndroidAuthSession buildSession()
    {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        session.setOAuth2AccessToken(ACCESSTOKEN);
        return session;
    }
    static final int UploadFromSelectApp = 9501;
    static final int UploadFromFilemanager = 9502;
    public static String DropboxUploadPathFrom = "";
    public static String DropboxUploadName = "";
    public static String DropboxDownloadPathFrom = "";
    public static String DropboxDownloadPathTo = "";

    // Dropbox API v1
    private void DownloadFromDropboxFromPath (final String downloadPathTo, final String downloadPathFrom)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Download file ...", Toast.LENGTH_SHORT).show();
                Thread th = new Thread(new Runnable() {
                    public void run() {
                        //String finalPath = DropboxDownloadPathTo + DropboxDownloadPathFrom.substring(DropboxDownloadPathFrom.lastIndexOf('.'));
                        File file = new File(downloadPathTo);
                        if (file.exists())
                            file.delete();
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                         //   MainActivity.dropboxAPI.getFile(downloadPathFrom, null, outputStream, null);
                            getMain().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "File successfully downloaded.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
            }
        });
    }

    private void UploadToDropboxFromPath (String uploadPathFrom, String uploadPathTo)
    {
        Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
        final String uploadPathF = uploadPathFrom;
        final String uploadPathT = uploadPathTo;
        Thread th = new Thread(new Runnable()
        {
            public void run()
            {
                File tmpFile = null;
                try
                {
                    tmpFile = new File(uploadPathF);
                }
                catch (Exception e) {e.printStackTrace();}
                FileInputStream fis = null;
                try
                {
                    fis = new FileInputStream(tmpFile);
                }
                catch (FileNotFoundException e) {e.printStackTrace();}
                try
                {
                    dropboxAPI.putFileOverwrite(uploadPathT, fis, tmpFile.length(), null);
                }
                catch (Exception e) {}
                getMain().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        th.start();
    }

    protected String doInBackground(DownloadFileSet... paths) {
    DownloadFileSet listOfFiles = paths[0];

    try {
        // Get files and folder metadata from Dropbox root directory
//                ListFolderResult result = mDropBoxclient.files().listFolder("/MedhaSuktam");
//
//                for (Metadata metadata : result.getEntries()) {
//                    //metadata.
//                    String fileName = metadata.getName();
//                    String localFileName = mlocalDirPath + "/" + metadata.getName();
//                    File file = new File(localFileName);
//                    if (file.exists())
//                        file.delete();
//                    try (OutputStream outputStream = new FileOutputStream(file)) {
//                        mDropBoxclient.files().download(metadata.getPathLower())
//                                .download(outputStream);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //mtv_results.append(metadata.getPathLower());
//                }
//
//                    if (!result.getHasMore()) {
//                        break;
//                    }
//
//                    result = mDropBoxclient.files().listFolderContinue(result.getCursor());

       }


    private void setupDropbox() {
        //AndroidAuthSession session = buildSession();
        //dropboxAPI = new DropboxAPI<AndroidAuthSession>(session);

        //DbxRequestConfig config = DbxRequestConfig.newBuilder("T04b-03-Exercise-ShareText")
         //       .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
          //      .build();
        DbxRequestConfig config = new DbxRequestConfig("T04b-03-Exercise-ShareText");
        String clientID = config.getClientIdentifier();
        mDropBoxclient = new DbxClientV2(config, getString(R.string.ACCESSTOKEN));

        // Get current account info
        //FullAccount account = client.users().getCurrentAccount();
        //System.out.println(account.getName().getDisplayName());
    }

    private void UploadToDropboxFromSelectedApp (String uploadName)
    {
        DropboxUploadName = uploadName;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*//*"); -- remove the extra /
        startActivityForResult(Intent.createChooser(intent, "Upload from ..."), UploadFromSelectApp);
    }

    private void UploadToDropboxFromFilemanager (String uploadName)
    {
        DropboxUploadName = uploadName;
        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        intent.putExtra("CONTENT_TYPE", "*//*"); -- remove the extra /
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, UploadFromFilemanager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == UploadFromFilemanager)
        {
            final Uri currFileURI = intent.getData();
            final String pathFrom = currFileURI.getPath();
            Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
            Thread th = new Thread(new Runnable()
            {
                public void run()
                {
                    getMain().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            UploadToDropboxFromPath(pathFrom, "/db-test/" + DropboxUploadName + pathFrom.substring(pathFrom.lastIndexOf('.')));
                            Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            th.start();
        }
        if (requestCode == UploadFromSelectApp)
        {
            Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
            final Uri uri = intent.getData();

            DropboxUploadPathFrom = getPath(getApplicationContext(), uri);
            if(DropboxUploadPathFrom == null) {
                DropboxUploadPathFrom = uri.getPath();
            }
            Thread th = new Thread(new Runnable(){
                public void run() {
                    try
                    {
                        final File file = new File(DropboxUploadPathFrom);
                        InputStream inputStream = getContentResolver().openInputStream(uri);

                        dropboxAPI.putFile("/db-test/" + DropboxUploadName + file.getName().substring(file.getName().lastIndexOf("."),
                                file.getName().length()), inputStream, file.length(), null, new ProgressListener(){
                            @Override
                            public long progressInterval() {return 100;}
                            @Override
                            public void onProgress(long arg0, long arg1){}
                        });
                        getMain().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {e.printStackTrace();}
                }
            });
            th.start();
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public String getPath(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Audio.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            if(s!=null) {
                cursor.close();
                return s;
            }
        }
        catch(Exception e){}
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            if(s!=null) {
                cursor.close();
                return s;
            }
        }
        catch(Exception e){}
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            cursor.close();
            return s;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public MainActivity getMain()
    {
        return this;
    }

    public void OpenPDFInNewActivity(String downloadFilePath)
    {
        try {
            File file = new File(downloadFilePath);

            if (file != null && file.exists()) {
                //Toast.makeText(this, "JGD ! This file exists !",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setMessage("JGD ! This file exists !")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else
                return;
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Intent openPDFIntent = new Intent(this, OpenPDFActivity.class);
        openPDFIntent.putExtra(Intent.ACTION_OPEN_DOCUMENT, downloadFilePath);

        startActivity(openPDFIntent);
    }

    private void openWebPage(String url) {

    Uri webpage = Uri.parse(url);


    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);


        if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    }
}
    private void showMap(Uri geoLocation) {

    }
    public void shareText(String textToShare) {
        String mimeType = "text/plain";
        String windowTitle = "Choose the program to share to";

        ShareCompat.IntentBuilder.from(this)
                .setType(mimeType)
                .setChooserTitle(windowTitle)
                .setText(textToShare)
                .startChooser();
    }
*/
/*
    public class DrawDebugBox {
        HighlightBox mCurrMarkerBox = null;
        DrawDebugBox(HighlightBox hb) {mCurrMarkerBox = hb; }
        private float getXCoordBasedOnZoom(float origCoord) {
            float zoomedCoord = origCoord;
            if ((mCurrZoomLevel != 1)) {
                float scaledCoord = -1 * (origCoord * mCurrZoomLevel);
                //zoomedCoord = Math.abs(mCurrXOffset - scaledCoord);
                zoomedCoord = (mCurrXOffset - scaledCoord);
            }
            return zoomedCoord;
        }
        private float getYCoordBasedOnZoom(float origCoord) {
            float zoomedCoord = origCoord;
            if ((mCurrZoomLevel != 1)) {
                float scaledCoord = -1 * (origCoord * mCurrZoomLevel);
                //zoomedCoord = Math.abs(mCurrYOffset - scaledCoord);
                zoomedCoord = (mCurrYOffset - scaledCoord);
            }
            return zoomedCoord;
        }
        public HighlightBox getCurrentBBoxBasedOnZoom() {
            float scaleFactorX = mCurrOptViewWidth/mCurrMarkerBox.getRefViewOptWidth();
            float scaleFactorY = mCurrOptViewHeight/mCurrMarkerBox.getRefViewOptHeight();

            float halfOffsetDiffX = (mCurrViewWidth - mCurrOptViewWidth) / 2;
            float halfOffsetDiffY = (mCurrViewHeight - mCurrOptViewHeight) / 2;

            float halfRefOffsetDiffX = (mCurrMarkerBox.getRefViewWidth() - mCurrMarkerBox.getRefViewOptWidth()) / 2;
            float halfRefOffsetDiffY = (mCurrMarkerBox.getRefViewHeight() - mCurrMarkerBox.getRefViewOptHeight()) / 2;

            // on Nexus9, OptSize=1298x1680, Size=1520x1680. (1520-1298)/2 = 111.0f which is the padding on both sides
            float startX = (mCurrMarkerBox.getStartX() - halfRefOffsetDiffX ) * scaleFactorX; // + halfOffsetDiffX;
            float startY = (mCurrMarkerBox.getStartY() - halfRefOffsetDiffY ) * scaleFactorY; // + halfOffsetDiffY;
            float endX   = (mCurrMarkerBox.getEndX()   - halfRefOffsetDiffX ) * scaleFactorX; // + halfOffsetDiffX;
            float endY   = (mCurrMarkerBox.getEndY()   - halfRefOffsetDiffY ) * scaleFactorY; // + halfOffsetDiffY;

            float translatedStartX = getXCoordBasedOnZoom(startX) + (mCurrZoomLevel == 1 ? halfOffsetDiffX : 0);; //
            float translatedStartY = getYCoordBasedOnZoom(startY) + (mCurrZoomLevel == 1 ? halfOffsetDiffY : 0);; //
            float translatedEndX = getXCoordBasedOnZoom(endX)     + (mCurrZoomLevel == 1 ? halfOffsetDiffX : 0);; //
            float translatedEndY = getYCoordBasedOnZoom(endY)     + (mCurrZoomLevel == 1 ? halfOffsetDiffY : 0);; //

            return mCurrMarkerBox.getCopyWithNewCoords(translatedStartX, translatedStartY, translatedEndX, translatedEndY);
        }
        public boolean highlightText() {
            if (mCurrMarkerBox != null) {
                if (mCurrPage != mCurrMarkerBox.getPageNum()) {
                    hideHighlighterLayout();
                    return false;
                }

                HighlightBox newCoord = getCurrentBBoxBasedOnZoom();
                if (false) {
                    hideHighlighterLayout();
                    return false;
                }
                float startX = newCoord.getStartX();
                float startY = newCoord.getStartY();
                float endX = newCoord.getEndX();
                float endY = newCoord.getEndY();

                updateHighlighterLayout(startX, startY, endX, endY);
            }
            return true;
        }
    }

    public void toastInfo() {
        //RectF pageRelativeBounds = part.getPageRelativeBounds();
        String toastMsg = "OnDraw\nPageWxH:" ; //+ String.valueOf(pageWidth) + "," + String.valueOf(pageHeight);
        if (localToast != null)
            localToast.cancel();
        localToast = Toast.makeText(PDF_Activity.this, toastMsg, Toast.LENGTH_LONG);
        localToast.show();
    }

    public void continueExperimentation() {
        try {
            mPdfView.zoomTo(2);
            //mPdfView.moveTo(100,100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCoordsUponClick() {
        String touchLoc = String.valueOf(mCurrTouchDownX) + ", " + String.valueOf(mCurrTouchDownY) + "\n"
                + String.valueOf(mCurrTouchUpX) + ", " + String.valueOf(mCurrTouchUpY);

        //mtv_coords.setText(touchLoc);
    }

    public void showToastUponClick(String btnText) {
        // Pos always stays at (0,13.0).. not sure what that is
        String toastMsg;
        String buttonText = btnText;
        float posX = mPdfView.getX();
        float posY = mPdfView.getY();
        float offX = mPdfView.getCurrentXOffset();
        float offY = mPdfView.getCurrentYOffset();
        float zoomLevel = mPdfView.getZoom();
        float pageW = mPdfView.getWidth();
        float pageH = mPdfView.getHeight();
        float pageOptW = mPdfView.getOptimalPageWidth();
        float pageOptH = mPdfView.getOptimalPageHeight();

        String pos =        "Pos:       " + String.valueOf(posX) + ", " + String.valueOf(posY);
        String off =        "Offset:    " + String.valueOf(offX) + ", " + String.valueOf(offY);
        String size =       "Size(WxH): " + String.valueOf(pageW) + ", " + String.valueOf(pageH);
        String optSize =    "OptSize:   " + String.valueOf(pageOptW) + ", " + String.valueOf(pageOptH);
        String touchLoc =   "TouchLoc:  " + String.valueOf(mCurrTouchUpX) + ", " + String.valueOf(mCurrTouchUpY);

        String zoom = "Zoom:  " + String.valueOf(zoomLevel);
        toastMsg = buttonText +"\n" + off + "\n" + pos + "\n" + size+ "\n" + optSize + "\n" + touchLoc + "\n" + zoom;

        if (localToast != null)
            localToast.cancel();
        localToast = Toast.makeText(PDF_Activity.this, toastMsg, Toast.LENGTH_LONG);
        localToast.show();
    }

    public void animateHighlighter() {
        float sizeX = mtv_touchMask.getScaleX();
        int i = 0;
        while (i < 10) {
            sizeX -= 4;
            i++;
            mtv_touchMask.animate().xBy(sizeX).setDuration(700).start();
        }
    }
*/
}
