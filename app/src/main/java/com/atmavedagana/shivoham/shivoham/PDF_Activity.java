package com.atmavedagana.shivoham.shivoham;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class PDF_Activity extends ActionBarCastActivity
                        implements PlayerControlBarView.PlayerControlsBarListener {

    public enum TOUCHMODE { MARKER_START, MARKER_END, TOP_GUIDE, BOT_GUIDE };

    private static final String CONTROLS_FRAGMENT_TAG = "player_controls_bar";

    private TOUCHMODE mTouchMode;
    private String mAudioLocalFilePath = null, mTextLocalFilePath = null;
    private String mContentTitle = "";

    PlayerControlBarView playerControlBarView = null;

    private FrameLayout mfl_mainFrame = null;
    private TextView mtv_touchMask =null;
    private TextView mtv_coords =null;
    private TextView mtv_playTime =null;
    private SimpleExoPlayer player;
    private Runnable mRunnable = null;
    private Handler mHandler = null;
    private LinearLayout linearLayout = null;
    private HighlightBox mCurrHighlighterPosition = null;
    private HighlightManager mHighlightManager = null;
    private LinearLayout mll_topGuide = null;
    private LinearLayout mll_botGuide = null;

    private long mCurrentPlayPostion;
    boolean mFirstTimeSetupDoneFlag = false;

    private String mOutputFilePath;
    OutputStreamWriter myOutWriter;

    Toast localToast;
    int coordsCounter = 0;

    private PDFView mPdfView=null;
    private int mTotalPages;

    PDFViewStats mPdfViewStats;
    private float mCurrTouchDownX;
    private float mCurrTouchDownY;
    private float mCurrTouchUpX;
    private float mCurrTouchUpY;


    public void setupTouchMaskListenerWithGuides() {
        mtv_touchMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) { }
                else if  (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    switch (mTouchMode) {
                        case MARKER_START:
                            mCurrTouchDownX = motionEvent.getX();
                            mCurrentPlayPostion = player.getContentPosition() / 10;
                            player.setPlayWhenReady(true);
                            mTouchMode = TOUCHMODE.MARKER_END;
                            break;
                        case MARKER_END:
                            mCurrTouchUpX = motionEvent.getX();
                            player.setPlayWhenReady(false);
                            mTouchMode = TOUCHMODE.MARKER_START;
                            createAndAddMarkerUsingSelection();
                            highlightText();
                            break;
                        case TOP_GUIDE:
                            mCurrTouchDownY = motionEvent.getY();
                            updateTopGuidePosition();
                            break;
                        case BOT_GUIDE:
                            mCurrTouchUpY = motionEvent.getY();
                            updateBotGuidePosition();
                            break;
                    }
                }
                return true;
            }
        });
    }

    private HighlightBox createMarkerUsingSelection() {
        return new HighlightBox(mCurrTouchDownX, mCurrTouchDownY, mCurrTouchUpX, mCurrTouchUpY,
                mCurrentPlayPostion, mPdfViewStats.mCurrPage, mPdfViewStats.mCurrViewWidth,
                mPdfViewStats.mCurrViewHeight,mPdfViewStats.mCurrOptViewWidth,mPdfViewStats.mCurrOptViewHeight);
    }

    private void createAndAddMarkerUsingSelection() {
        HighlightBox hBox = createMarkerUsingSelection();
        mHighlightManager.add(hBox);
        mHighlightManager.setCurrHighlighterPosition(hBox);
    }

    public void updateTopGuidePosition() {
        mll_topGuide.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params = mll_topGuide.getLayoutParams();
        params.height = 4;
        params.width = mfl_mainFrame.getWidth();
        mll_topGuide.setLayoutParams(params);


        mll_topGuide.setX(0);
        mll_topGuide.setY(mCurrTouchDownY);
        mll_topGuide.invalidate();
        mll_topGuide.requestLayout();
    }

    public void updateBotGuidePosition() {
        mll_botGuide.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params = mll_botGuide.getLayoutParams();
        params.height = 4;
        params.width = mfl_mainFrame.getWidth();
        mll_botGuide.setLayoutParams(params);

        mll_botGuide.setX(0);
        mll_botGuide.setY(mCurrTouchUpY);
        mll_botGuide.invalidate();
        mll_botGuide.requestLayout();
    }

    public void prepOutputFile() {
        //mOutputFilePath = this.getFilesDir().getAbsolutePath() + "/markerList.txt";
        mOutputFilePath = "/storage/emulated/0/Download/markerList.txt";

        try {
            File file = new File(mOutputFilePath);
            if (!file.exists())
                file.createNewFile();
            //FileOutputStream outFile = openFileOutput(mOutputFilePath, MODE_APPEND);
            FileOutputStream outFile = new FileOutputStream(file, true);
            myOutWriter = new OutputStreamWriter(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initializeToolbar(R.menu.pdfmenu);

        //UNPACK OUR DATA FROM INTENT
        Intent i = this.getIntent();
        String[] path = i.getExtras().getStringArray(Intent.ACTION_OPEN_DOCUMENT);
        if (path.length == 3) {
            mTextLocalFilePath = path[0];
            mAudioLocalFilePath = path[1];
            mContentTitle = path[2];
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        mtv_touchMask = (TextView) findViewById(R.id.tv_touchMask);
        //PDFVIEW SHALL DISPLAY OUR PDFS
        mPdfView = (PDFView) findViewById(R.id.pdfView);
        //SACRIFICE MEMORY FOR QUALITY
        //pdfView.useBestQuality(true)

        mfl_mainFrame = (FrameLayout) findViewById(R.id.fl_mainFrame);

        mll_botGuide = (LinearLayout) findViewById(R.id.ll_botGuide);
        mll_botGuide.setVisibility(View.INVISIBLE);
        mll_topGuide = (LinearLayout) findViewById(R.id.ll_topGuide);
        mll_topGuide.setVisibility(View.INVISIBLE);

        linearLayout = (LinearLayout) findViewById(R.id.ll_highlighter);
        linearLayout.setVisibility(View.INVISIBLE);
        localToast = new Toast(this);

        mHighlightManager = new HighlightManager();
        mPdfViewStats = new PDFViewStats();
        mHighlightManager.mPdfviewStats = mPdfViewStats;

        //prepare the output file
        prepOutputFile();

        if (mTextLocalFilePath !=null && mAudioLocalFilePath != null) {
            mHighlightManager. setupCoordsList(mTextLocalFilePath);
            openPDFFile();
            playMusic();
        } else
            localToast.makeText(this, "Illegal path location.", Toast.LENGTH_SHORT).show();
    }

    public void initializePlayerControlsBar(long totalAudioDuration) {
        playerControlBarView = (PlayerControlBarView) findViewById(R.id.controls_layout);
        playerControlBarView.setPlayerControlsBarListener(this);

        playerControlBarView.setUseMode(GlobalSettingsSingleton.getInstance().getmUseModeState());
        playerControlBarView.setTotalTime(totalAudioDuration);
    }

    public void HideTopBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
    }

    public void hideHighlighterLayout() {
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public boolean highlightText() {
        if (mHighlightManager.getCurrHighlighterPosition() != null) {
            if (mPdfViewStats.mCurrPage != mHighlightManager.getCurrentPageNum()) {
                mPdfView.jumpTo(mHighlightManager.getCurrentPageNum());
            }

            HighlightBox newCoord = mHighlightManager.getCurrentBBoxBasedOnZoom();
            float startX = newCoord.getStartX();
            float startY = newCoord.getStartY();
            float endX = newCoord.getEndX();
            float endY = newCoord.getEndY();

            updateHighlighterLayout(startX, startY, endX, endY);
        }
        return true;
    }

    public void updateHighlighterLayout(float startX, float startY, float endX, float endY) {

        linearLayout.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.height = (int)(endY-startY);
        params.width = (int)(endX-startX);
        linearLayout.setLayoutParams(params);

        linearLayout.setX(startX);
        linearLayout.setY(startY);
        linearLayout.invalidate();
        linearLayout.requestLayout();
    }

    class PDFViewStats {
        public int mCurrPage = 1;
        public float mCurrViewWidth;
        public float mCurrViewHeight;
        public float mCurrOptViewWidth;
        public float mCurrOptViewHeight;
        public float mCurrXOffset;
        public float mCurrYOffset;
        public float mCurrZoomLevel;

        public void updateVitals(PDFView pdfView) {
            mCurrZoomLevel = pdfView.getZoom();
            mCurrXOffset = pdfView.getCurrentXOffset();
            mCurrYOffset = pdfView.getCurrentYOffset();
            mCurrPage = pdfView.getCurrentPage() + 1;
            mCurrViewWidth = pdfView.getWidth();
            mCurrViewHeight = pdfView.getHeight();
            mCurrOptViewWidth = pdfView.getOptimalPageWidth();
            mCurrOptViewHeight = pdfView.getOptimalPageHeight();
        }
    }

    public void openPDFFile() {
        //GET THE PDF FILE
        File file=new File(mTextLocalFilePath);
        if(file.canRead())
        {
            mPdfView.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    //Toast.makeText(PDF_Activity.this, "1/"+String.valueOf(nbPages), Toast.LENGTH_LONG).show();
                    mTotalPages = nbPages;
                }
            }).onDraw(new OnDrawListener() {
                @Override
                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                    mPdfViewStats.updateVitals(mPdfView);

                    highlightText();
                }
            }).load();

            setupTouchMaskListenerWithGuides();
        }
    }



    public void onClickButtonTouch(View v) {
        int visibility = mtv_touchMask.getVisibility();
        if (visibility == View.VISIBLE)
            mtv_touchMask.setVisibility(View.GONE);
        else
            mtv_touchMask.setVisibility(View.VISIBLE);

        setButtonDepressedView(v.getId());
    }

    public void onClickButtonPrev(View V) {
        //Move to Prev Marker
        try {
            long position = mHighlightManager.moveToPrevMarker();
            player.seekTo(position);
            highlightText();
        } catch (HighlightManager.NoMarkerFoundException e) {
            Toast.makeText(PDF_Activity.this, "No marker found", Toast.LENGTH_LONG).show();
        }

        setButtonDepressedView(V.getId());
    }

    private boolean buttonCIsNowPlay = true; //Play-true, Pause-false
    public void onClickButtonPlayPause(View V) {
        //Play/Pause
        ImageButton btC = (ImageButton) findViewById(R.id.bn_C);

        if (buttonCIsNowPlay)
            btC.setBackgroundResource(R.drawable.pause);
        else
            btC.setBackgroundResource(R.drawable.play);

        buttonCIsNowPlay = !buttonCIsNowPlay;

        player.setPlayWhenReady(!player.getPlayWhenReady());
        setButtonDepressedView(V.getId());
    }

    public void onClickButtonForward(View V) {
        //Move to Next Marker
        try {
            long position = mHighlightManager.moveToNextMarker();
            player.seekTo(position);
            highlightText();
        } catch (HighlightManager.NoMarkerFoundException e) {
            Toast.makeText(PDF_Activity.this, "No marker found", Toast.LENGTH_LONG).show();
        }
        setButtonDepressedView(V.getId());
    }

    public void checkAndRestartHighlightHandler() {
        // change this to the correct call on mHandler that checks if any callbacks exist
        if (mHandler == null)
            trackProgress();
    }

    public void onClickButtonSave(View V) {
        mHighlightManager.saveCoordsListToFile(myOutWriter);
        setButtonDepressedView(V.getId());
    }

    public void onClickButtonSetTopGuide(View v) {
        mTouchMode = TOUCHMODE.TOP_GUIDE;
        player.setPlayWhenReady(false);
    }

    public void onClickButtonSetBotGuide(View v) {
        mTouchMode = TOUCHMODE.BOT_GUIDE;
        player.setPlayWhenReady(false);
    }

    public void onClickButtonRecord(View v) {
        mTouchMode = TOUCHMODE.MARKER_START;
        player.setPlayWhenReady(false);
    }

    public void setButtonDepressedView(int buttonID) {
//        LinearLayout bPanel = (LinearLayout) findViewById(R.id.ll_buttonsPanel);
//
//        for(int index=0; index < bPanel.getChildCount(); ++index) {
//            View nextChild = bPanel.getChildAt(index);
//            if (nextChild.getId() == buttonID)
//                (nextChild).setBackgroundColor(0xFF000000);
//            else
//                (nextChild).setBackgroundColor(0xFF00AAAA);
//        }
    }

    public void throwAlert(String strMsg) {
        new AlertDialog.Builder(this)
                .setMessage(strMsg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void playMusic() {
        // Dont use MediaPlayer.. this is too restrictive and doesnt expose low-level APIs
        // /MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.medhasuktam);
        //mPlayer.start();

        BandwidthMeter bandwidthMeter;
        ExtractorsFactory extractorsFactory;
        TrackSelection.Factory trackSelectionFactory;
        TrackSelector trackSelector;
        DefaultBandwidthMeter defaultBandwidthMeter;
        com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        MediaSource mediaSource;


        bandwidthMeter = new DefaultBandwidthMeter();
        extractorsFactory = new DefaultExtractorsFactory();
        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

/*        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);*/

        defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter);

        mediaSource = new ExtractorMediaSource(Uri.parse(mAudioLocalFilePath), dataSourceFactory, extractorsFactory, null, null);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player.prepare(mediaSource);

        player.setPlayWhenReady(false);
        player.addListener(new SimpleExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {}

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_READY && !mFirstTimeSetupDoneFlag) {
                    long realDurationMillis = player.getDuration();
                    initializePlayerControlsBar(realDurationMillis / 10);
                    mFirstTimeSetupDoneFlag = true;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {}

            @Override
            public void onPlayerError(ExoPlaybackException error) {}

            @Override
            public void onPositionDiscontinuity() {}

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
        });
        trackProgress();
    }

    public void trackProgress() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    long duration = player.getDuration() / 1000;
                    long position = player.getCurrentPosition() / 10;
                    if (duration > 0) {// (state == ExoPlayer.STATE_READY || state == ExoPlayer.STATE_ENDED))
                        // todo@svishves: Send position as Message to a Handler
                        identifyAndHighlightMarker(position);
                        mHandler.postDelayed(this, 500);
                    }
                    if (player.getPlaybackState() == SimpleExoPlayer.STATE_ENDED) {
                        notifyAudioEnded();
                        mHandler.removeCallbacks(this);
                    }
                }
            }
        };
        mHandler.postDelayed(mRunnable, 500);
    }

    public void notifyAudioEnded() {
        playerControlBarView.setPlayPauseButtonIcon(true);
    }

    public void identifyAndHighlightMarker(long position) {
        if (mHighlightManager.isMarkerPresentForPos(position)) {
            highlightText();
        }

        long duration = player.getDuration() / 10;
        playerControlBarView.setElapsedTime(position);
        playerControlBarView.setTotalTime(duration);
    }

    @Override
    public void onPause() {
        // TODO: 9/28/2017 @Shiv: remember position state to enable playback from same spot
        long position = player.getCurrentPosition();
        player.setPlayWhenReady(false);
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
//        MenuInflater inflater = getMenuInflater();
//        /* Use the inflater's inflate method to inflate our menu layout to this menu */
//        inflater.inflate(R.menu.pdfmenu, menu);
//        /* Return true so that the menu is displayed in the Toolbar */
//        return true;
//    }


    @Override
    public void onClickPlayButton() {
        onClickButtonPlayPause(playerControlBarView);
    }

    @Override
    public void onClickPauseButton() {
        Toast.makeText(this, "Audio paused.", Toast.LENGTH_SHORT).show();
        onClickButtonPlayPause(playerControlBarView);
    }

    @Override
    public void onClickPrevButton() {
        onClickButtonPrev(playerControlBarView);
    }

    @Override
    public void onClickNextButton() {
        onClickButtonForward(playerControlBarView);
    }

    @Override
    public void onClickChangeToListenMode() {
        Toast.makeText(this, "Listen mode", Toast.LENGTH_SHORT).show();
        GlobalSettingsSingleton.getInstance().setmUseModeState(GlobalSettingsSingleton.MODE_STATE.LISTEN_MODE);
    }

    @Override
    public void onClickChangeToChantMode() {
        Toast.makeText(this, "Chant-along mode", Toast.LENGTH_SHORT).show();
        GlobalSettingsSingleton.getInstance().setmUseModeState(GlobalSettingsSingleton.MODE_STATE.CHANT_MODE);
    }

    @Override
    public void onClickChangeToReadMode() {
        Toast.makeText(this, "Read mode", Toast.LENGTH_SHORT).show();
        GlobalSettingsSingleton.getInstance().setmUseModeState(GlobalSettingsSingleton.MODE_STATE.READ_MODE);
    }
}
