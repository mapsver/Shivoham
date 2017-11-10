package com.atmavedagana.shivoham.shivoham;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by shiv on 10/24/2017.
 */

class HighlightManager {

    private long timeTolerance = 50; //
    private int coordsIdx = 0;
    private ArrayList<HighlightBox> mCoordsList = new ArrayList<>();
    private String highlightLookupFile;
    public PDF_Activity.PDFViewStats mPdfviewStats;

    public void add(HighlightBox hBox) {
        mCoordsList.add(hBox);
        hBox.setIndex(coordsIdx);
        setCoordsIdx(coordsIdx + 1);
    }
    public HighlightBox get(int index) {
        return mCoordsList.get(index);
    }
    public int size() {
        return mCoordsList.size();
    }
    public void setCoordsIdx(int coordsIdx) {
        this.coordsIdx = coordsIdx;
    }
    private HighlightBox mCurrHighlighterPosition = null;

    public int getCurrentPageNum() { return getCurrHighlighterPosition().getPageNum(); }

    public void saveCoordsListToFile(OutputStreamWriter outputWriter) {
        try {
            for (HighlightBox hb : mCoordsList) {
                String s = "add(new HighlightBox(" + hb.getStartX() + "f, " + hb.getStartY() + "f, " +
                        hb.getEndX() + "f, " + hb.getEndY() + "f, " +
                        hb.getStartTime() + ", " + hb.getPageNum() + ", " +
                        hb.getRefViewWidth() + "f, " + hb.getRefViewHeight() + "f, " +
                        hb.getRefViewOptWidth() + "f, " + hb.getRefViewOptHeight() + "f));";

                outputWriter.write(s);
                outputWriter.write("\n\r");
            }
            outputWriter.write("\n\r");
            outputWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupCoordsList(String textFilePath) {

        // TODO: 9/28/2017 @Shiv: Parse highlightLookupFile to extract coords.
        // TODO: 9/28/2017 @Shiv: ensure that no two startTimes are within the timeTolerance
        highlightLookupFile = textFilePath;

        add(new HighlightBox(190.17041f, 248.27344f, 228.20508f, 304.28906f, 0, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(237.20288f, 248.27344f, 465.4109f, 304.28906f, 259, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(474.4087f, 248.27344f, 615.53906f, 304.28906f, 628, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(627.5691f, 248.27344f, 838.7373f, 304.28906f, 915, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(196.16895f, 307.27734f, 333.27832f, 365.34375f, 1294, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(325.2693f, 307.27734f, 509.4441f, 365.34375f, 1454, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(515.4426f, 307.27734f, 680.6001f, 365.34375f, 1768, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(688.60913f, 307.27734f, 773.6763f, 365.34375f, 2014, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(204.17798f, 729.5039f, 451.40332f, 782.53125f, 2309, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(462.41162f, 729.5039f, 571.50586f, 782.53125f, 2594, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(580.50366f, 729.5039f, 709.63696f, 782.53125f, 2766, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(727.63257f, 729.5039f, 826.74023f, 782.53125f, 2941, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(166.14331f, 790.5586f, 348.30762f, 849.5625f, 3216, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(357.30542f, 790.5586f, 545.46826f, 849.5625f, 3485, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(556.47656f, 790.5586f, 715.6355f, 849.5625f, 3734, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(721.63403f, 790.5586f, 794.7041f, 849.5625f, 3968, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(298.27588f, 1192.7461f, 380.34375f, 1254.7969f, 4151, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(389.34155f, 1192.7461f, 509.4441f, 1254.7969f, 4318, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(524.4734f, 1192.7461f, 732.64233f, 1254.7969f, 4493, 1, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(269.239f, 133.19531f, 448.40405f, 195.2461f, 4886, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(448.40405f, 133.19531f, 565.5073f, 195.2461f, 5233, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(574.5051f, 133.19531f, 691.6084f, 195.2461f, 5461, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(292.2444f, 855.59766f, 460.40112f, 911.6133f, 5683, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(474.4087f, 855.59766f, 615.53906f, 911.6133f, 5951, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(633.5676f, 855.59766f, 738.64087f, 911.6133f, 6155, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(175.14111f, 911.6133f, 278.23682f, 972.6094f, 6387, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(292.2444f, 911.6133f, 465.4109f, 972.6094f, 6701, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(474.4087f, 911.6133f, 600.5427f, 972.6094f, 6981, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(618.5383f, 911.6133f, 785.7063f, 972.6094f, 7189, 2, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(213.17578f, 535.4414f, 330.27905f, 597.4336f, 7534, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(342.30908f, 535.4414f, 524.4734f, 597.4336f, 7834, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(536.47046f, 535.4414f, 653.5737f, 597.4336f, 8109, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(665.60376f, 535.4414f, 817.74243f, 597.4336f, 8260, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(163.14404f, 594.4453f, 272.23828f, 661.47656f, 8526, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(284.23535f, 594.4453f, 345.30835f, 661.47656f, 8739, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(363.30396f, 594.4453f, 460.40112f, 661.47656f, 8856, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(468.41016f, 594.4453f, 589.5344f, 661.47656f, 9000, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(600.5427f, 594.4453f, 803.7019f, 661.47656f, 9285, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(284.23535f, 1145.6953f, 392.34082f, 1213.7227f, 9601, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(401.33862f, 1145.6953f, 509.4441f, 1213.7227f, 9795, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(518.47485f, 1145.6953f, 603.542f, 1213.7227f, 10017, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(618.5383f, 1145.6953f, 744.67236f, 1213.7227f, 10144, 3, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(281.23608f, 131.20313f, 509.4441f, 192.25781f, 10331, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(521.4741f, 131.20313f, 744.67236f, 192.25781f, 10594, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(240.20215f, 192.25781f, 457.40186f, 257.29688f, 10899, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(477.40796f, 192.25781f, 583.5029f, 257.29688f, 11210, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(595.53296f, 192.25781f, 727.63257f, 257.29688f, 11411, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
        add(new HighlightBox(380.34375f, 717.4922f, 377.34448f, 770.51953f, 11665, 4, 1056.0f, 1392.0f, 1056.0f, 1366.0f));
    }

    public boolean isMarkerPresentForPos(long currPlayPos) {
        // TODO: 9/28/2017 @Shiv: optimize this !!
        for (HighlightBox b : mCoordsList) {
            long bPos = b.getStartTime();
            if (isCurrPosAroundTarget(currPlayPos, bPos, timeTolerance)) {
                setCurrHighlighterPosition(b);
                return true;
            }
        }
        return false;
    }

    private boolean isCurrPosAroundTarget(long currPos, long targetPos, long tolerance) {
        return Math.abs(currPos - targetPos) < tolerance;
    }

    public long moveToPrevMarker() throws NoMarkerFoundException {
        // TODO: 9/28/2017 @Shiv: optimize this !!
        int curIndex = getCurrHighlighterPosition().getIndex();
        for (HighlightBox b : mCoordsList) {
            int index = b.getIndex();
            if (index == curIndex-1) {
                setCurrHighlighterPosition(b);
                return getCurrHighlighterPosition().getStartTime() * 10;
            }
        }
        throw new NoMarkerFoundException();
    }

    public long moveToNextMarker() throws NoMarkerFoundException {
        // TODO: 9/28/2017 @Shiv: optimize this !!
        int curIndex = getCurrHighlighterPosition().getIndex();
        for (HighlightBox b : mCoordsList) {
            int index = b.getIndex();
            if (index == curIndex+1) {
                setCurrHighlighterPosition(b);
                return getCurrHighlighterPosition().getStartTime() * 10;
            }
        }
        throw (new NoMarkerFoundException());
    }

    public HighlightBox getCurrHighlighterPosition() {
        return mCurrHighlighterPosition;
    }

    public void setCurrHighlighterPosition(HighlightBox mCurrHighlighterPosition) {
        this.mCurrHighlighterPosition = mCurrHighlighterPosition;
    }

    public class NoMarkerFoundException extends Exception {
    }


    // these methods are the only ones that need PdfViewStats.. make separate class ?!
    private float getXCoordBasedOnZoom(float origCoord) {
        float zoomedCoord = origCoord;
        if ((mPdfviewStats.mCurrZoomLevel != 1)) {
            float scaledCoord = -1 * (origCoord * mPdfviewStats.mCurrZoomLevel);
            // since scrolling is horizontal.. need to account for the Xoffset position on the BIG-STRIP
            zoomedCoord = (mPdfviewStats.mCurrXOffset - scaledCoord) + (mPdfviewStats.mCurrOptViewWidth * (mPdfviewStats.mCurrPage - 1)) * mPdfviewStats.mCurrZoomLevel;
        }
        return zoomedCoord;
    }
    private float getYCoordBasedOnZoom(float origCoord) {
        float zoomedCoord = origCoord;
        if ((mPdfviewStats.mCurrZoomLevel != 1)) {
            float scaledCoord = -1 * (origCoord * mPdfviewStats.mCurrZoomLevel);
            // since scrolling is horizontal.. YOffset should be the same for everypage
            zoomedCoord = (mPdfviewStats.mCurrYOffset - scaledCoord);
        }
        return zoomedCoord;
    }
    public HighlightBox getCurrentBBoxBasedOnZoom() {
        float scaleFactorX = mPdfviewStats.mCurrOptViewWidth/ getCurrHighlighterPosition().getRefViewOptWidth();
        float scaleFactorY = mPdfviewStats.mCurrOptViewHeight/ getCurrHighlighterPosition().getRefViewOptHeight();

        float halfOffsetDiffX = (mPdfviewStats.mCurrViewWidth - mPdfviewStats.mCurrOptViewWidth) / 2;
        float halfOffsetDiffY = (mPdfviewStats.mCurrViewHeight - mPdfviewStats.mCurrOptViewHeight) / 2;

        float halfRefOffsetDiffX = (getCurrHighlighterPosition().getRefViewWidth() - getCurrHighlighterPosition().getRefViewOptWidth()) / 2;
        float halfRefOffsetDiffY = (getCurrHighlighterPosition().getRefViewHeight() - getCurrHighlighterPosition().getRefViewOptHeight()) / 2;

        // on Nexus9, OptSize=1298x1680, Size=1520x1680. (1520-1298)/2 = 111.0f which is the padding on both sides
        float startX = (getCurrHighlighterPosition().getStartX() - halfRefOffsetDiffX ) * scaleFactorX; // + halfOffsetDiffX;
        float startY = (getCurrHighlighterPosition().getStartY() - halfRefOffsetDiffY ) * scaleFactorY; // + halfOffsetDiffY;
        float endX   = (getCurrHighlighterPosition().getEndX()   - halfRefOffsetDiffX ) * scaleFactorX; // + halfOffsetDiffX;
        float endY   = (getCurrHighlighterPosition().getEndY()   - halfRefOffsetDiffY ) * scaleFactorY; // + halfOffsetDiffY;

        float translatedStartX = getXCoordBasedOnZoom(startX) + (mPdfviewStats.mCurrZoomLevel == 1 ? halfOffsetDiffX : 0);; //
        float translatedStartY = getYCoordBasedOnZoom(startY) + (mPdfviewStats.mCurrZoomLevel == 1 ? halfOffsetDiffY : 0);; //
        float translatedEndX = getXCoordBasedOnZoom(endX)     + (mPdfviewStats.mCurrZoomLevel == 1 ? halfOffsetDiffX : 0);; //
        float translatedEndY = getYCoordBasedOnZoom(endY)     + (mPdfviewStats.mCurrZoomLevel == 1 ? halfOffsetDiffY : 0);; //

        return getCurrHighlighterPosition().getCopyWithNewCoords(translatedStartX, translatedStartY, translatedEndX, translatedEndY);
    }

}
