package com.atmavedagana.shivoham.shivoham;

/**
 * Created by shiv on 10/26/2017.
 */

public class AVGMediaItem {

    private String mDisplayTitle = "";
    private String mDisplayDescription = "";
    private DownloadFileSet mDownloadFileSet;

    public AVGMediaItem(String displayTitle, String description, String jsonFromPath, String audioFromPath, String pdfFromPath,
                        String jsonToPath, String audioToPath, String pdfToPath) {
        setmDisplayTitle(displayTitle);
        setmDisplayDescription(description);
        mDownloadFileSet = new DownloadFileSet (jsonFromPath, audioFromPath, pdfFromPath, jsonToPath, audioToPath, pdfToPath);
    }

    public String getmDisplayTitle() {
        return mDisplayTitle;
    }

    public void setmDisplayTitle(String mDisplayTitle) {
        this.mDisplayTitle = mDisplayTitle;
    }

    public String getmDisplayDescription() {
        return mDisplayDescription;
    }

    public void setmDisplayDescription(String mDisplayDescription) {
        this.mDisplayDescription = mDisplayDescription;
    }

    public String getmJsonFileGetFromPath() {
        return mDownloadFileSet.mJsonFileGetFromPath;
    }

    public void setmJsonFileGetFromPath(String mJsonFileGetFromPath) {
        mDownloadFileSet.mJsonFileGetFromPath = mJsonFileGetFromPath;
    }

    public String getmAudioFileGetFromPath() {
        return mDownloadFileSet.mAudioFileGetFromPath;
    }

    public void setmAudioFileGetFromPath(String mAudioFileGetFromPath) {
        mDownloadFileSet.mAudioFileGetFromPath = mAudioFileGetFromPath;
    }

    public String getmPDFFileGetFromPath() {
        return mDownloadFileSet.mPDFFileGetFromPath;
    }

    public void setmPDFFileGetFromPath(String mPDFFileGetFromPath) {
        mDownloadFileSet.mPDFFileGetFromPath = mPDFFileGetFromPath;
    }

    public String getmJsonFileSaveToPath() {
        return mDownloadFileSet.mJsonFileSaveToPath;
    }

    public void setmJsonFileSaveToPath(String mJsonFileSaveToPath) {
        mDownloadFileSet.mJsonFileSaveToPath = mJsonFileSaveToPath;
    }

    public String getmAudioFileSaveToPath() {
        return mDownloadFileSet.mAudioFileSaveToPath;
    }

    public void setmAudioFileSaveToPath(String mAudioFileSaveToPath) {
        mDownloadFileSet.mAudioFileSaveToPath = mAudioFileSaveToPath;
    }

    public String getmPDFFileSaveToPath() {
        return mDownloadFileSet.mPDFFileSaveToPath;
    }

    public void setmPDFFileSaveToPath(String mPDFFileSaveToPath) {
        mDownloadFileSet.mPDFFileSaveToPath = mPDFFileSaveToPath;
    }

    public static class DownloadFileSet {
        public String mJsonFileGetFromPath = "";
        public String mAudioFileGetFromPath = "";
        public String mPDFFileGetFromPath = "";
        public String mJsonFileSaveToPath = "";
        public String mAudioFileSaveToPath = "";
        public String mPDFFileSaveToPath = "";

        public DownloadFileSet(String jsonFromPath, String audioFromPath, String pdfFromPath,
                            String jsonToPath, String audioToPath, String pdfToPath) {
            mJsonFileGetFromPath = jsonFromPath;
            mAudioFileGetFromPath = audioFromPath;
            mPDFFileGetFromPath = pdfFromPath;
            mJsonFileSaveToPath = jsonToPath;
            mAudioFileSaveToPath = audioToPath;
            mPDFFileSaveToPath = pdfToPath;
        }

        public String getmJsonFileGetFromPath() {
            return mJsonFileGetFromPath;
        }

        public void setmJsonFileGetFromPath(String mJsonFileGetFromPath) {
            mJsonFileGetFromPath = mJsonFileGetFromPath;
        }

        public String getmAudioFileGetFromPath() {
            return mAudioFileGetFromPath;
        }

        public void setmAudioFileGetFromPath(String mAudioFileGetFromPath) {
            mAudioFileGetFromPath = mAudioFileGetFromPath;
        }

        public String getmPDFFileGetFromPath() {
            return mPDFFileGetFromPath;
        }

        public void setmPDFFileGetFromPath(String mPDFFileGetFromPath) {
            mPDFFileGetFromPath = mPDFFileGetFromPath;
        }

        public String getmJsonFileSaveToPath() {
            return mJsonFileSaveToPath;
        }

        public void setmJsonFileSaveToPath(String mJsonFileSaveToPath) {
            mJsonFileSaveToPath = mJsonFileSaveToPath;
        }

        public String getmAudioFileSaveToPath() {
            return mAudioFileSaveToPath;
        }

        public void setmAudioFileSaveToPath(String mAudioFileSaveToPath) {
            mAudioFileSaveToPath = mAudioFileSaveToPath;
        }

        public String getmPDFFileSaveToPath() {
            return mPDFFileSaveToPath;
        }

        public void setmPDFFileSaveToPath(String mPDFFileSaveToPath) {
            mPDFFileSaveToPath = mPDFFileSaveToPath;
        }
    }

}
