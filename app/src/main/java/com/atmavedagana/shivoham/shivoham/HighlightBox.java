package com.atmavedagana.shivoham.shivoham;

/**
 * Created by shiv on 10/24/2017.
 */

class HighlightBox {
    private int index;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private long startTime;
    private int pageNum;
    private float refViewWidth;
    private float refViewHeight;
    private float refViewOptWidth;
    private float refViewOptHeight ;
    // ***remember to modify the copy-constructor too, when adding fields

    HighlightBox() {}
    HighlightBox(float sx, float sy, float ex, float ey, long stTime, int pgNum) {
        setIndex(index);
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
        setStartTime(stTime);
        setPageNum(pgNum);
    }
    HighlightBox(float sx, float sy, float ex, float ey, long stTime, int pgNum,
                 float viewWt, float viewHt, float viewOptWt, float viewOptHt) {
        setIndex(index);
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
        setStartTime(stTime);
        setPageNum(pgNum);
        setRefViewWidth(viewWt);
        setRefViewHeight(viewHt);
        setRefViewOptWidth(viewOptWt);
        setRefViewOptHeight(viewOptHt);
    }
    public HighlightBox(HighlightBox orig) {
        setIndex(orig.getIndex());
        setStartX(orig.getStartX());
        setStartY(orig.getStartY());
        setEndX(orig.getEndX());
        setEndY(orig.getEndY());
        setPageNum(orig.getPageNum());
        setStartTime(orig.getStartTime());
        setRefViewWidth(orig.getRefViewWidth());
        setRefViewHeight(orig.getRefViewHeight());
        setRefViewOptWidth(orig.getRefViewOptWidth());
        setRefViewOptHeight(orig.getRefViewOptHeight());
    }
    public HighlightBox getCopyWithNewCoords(float sx, float sy, float ex, float ey) {
        HighlightBox newBox = new HighlightBox(this);
        newBox.setStartX(sx);
        newBox.setStartY(sy);
        newBox.setEndX(ex);
        newBox.setEndY(ey);

        return newBox;
    }

    public float getStartX() {
        return startX;
    }
    public void setStartX(float startX) {
        this.startX = startX;
    }
    public float getStartY() {
        return startY;
    }
    public void setStartY(float startY) {
        this.startY = startY;
    }
    public float getEndX() {
        return endX;
    }
    public void setEndX(float endX) {
        this.endX = endX;
    }
    public float getEndY() {
        return endY;
    }
    public void setEndY(float endY) {
        this.endY = endY;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public float getRefViewWidth() {
        return refViewWidth;
    }
    public void setRefViewWidth(float refViewWidth) {
        this.refViewWidth = refViewWidth;
    }
    public float getRefViewHeight() {
        return refViewHeight;
    }
    public void setRefViewHeight(float refViewHeight) {
        this.refViewHeight = refViewHeight;
    }
    public float getRefViewOptWidth() {
        return refViewOptWidth;
    }
    public void setRefViewOptWidth(float refViewOptWidth) {
        this.refViewOptWidth = refViewOptWidth;
    }
    public float getRefViewOptHeight() {
        return refViewOptHeight;
    }
    public void setRefViewOptHeight(float refViewOptHeight) {
        this.refViewOptHeight = refViewOptHeight;
    }
}