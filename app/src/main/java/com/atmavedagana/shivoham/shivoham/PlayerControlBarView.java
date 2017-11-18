package com.atmavedagana.shivoham.shivoham;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmavedagana.shivoham.shivoham.utils.LogHelper;
import com.atmavedagana.shivoham.shivoham.utils.TimeHelper;

/**
 * Created by shiv on 11/12/2017.
 */

public class PlayerControlBarView extends LinearLayout {


    private static final String TAG = LogHelper.makeLogTag(PlayerControlBarView.class);
    private PlayerControlsBarListener playerControlsBarListener = null;

    private ImageView mPlayPauseButton, mPrevButton, mNextButton, mModeIcon;
    private TextView mElapsedTime, mTotalTime;
    private GlobalSettingsSingleton.MODE_STATE mModeState = GlobalSettingsSingleton.MODE_STATE.LISTEN_MODE;
    private boolean mIsCurrentlyPlayIcon = true; // if playing, then playIcon is false and vice-versa

    public PlayerControlBarView(Context context, AttributeSet attrSet) {
        super(context, attrSet);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_controls_bar, this, true);

        mPlayPauseButton = (ImageView) findViewById(R.id.play_pause);
        mPrevButton = (ImageView) findViewById(R.id.prev);
        mNextButton = (ImageView) findViewById(R.id.next);
        mModeIcon = (ImageView) findViewById(R.id.mode_icon);
        mElapsedTime = (TextView) findViewById(R.id.controls_time_elapsed);
        mTotalTime = (TextView) findViewById(R.id.controls_time_total);


        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mIsCurrentlyPlayIcon) {
                setPlayPauseButtonIcon(false);
                playerControlsBarListener.onClickPlayButton();
            } else {
                setPlayPauseButtonIcon(true);
                playerControlsBarListener.onClickPauseButton();
            }
            }
        });
        mPrevButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerControlsBarListener.onClickPrevButton();
            }
        });
        mNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerControlsBarListener.onClickNextButton();
            }
        });
        mModeIcon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModeState == GlobalSettingsSingleton.MODE_STATE.LISTEN_MODE) {
                    setUseMode(GlobalSettingsSingleton.MODE_STATE.CHANT_MODE);
                } else if (mModeState == GlobalSettingsSingleton.MODE_STATE.CHANT_MODE) {
                    setUseMode(GlobalSettingsSingleton.MODE_STATE.READ_MODE);
                } else if (mModeState == GlobalSettingsSingleton.MODE_STATE.READ_MODE) {
                    setUseMode(GlobalSettingsSingleton.MODE_STATE.LISTEN_MODE);
                }
            }
        });
    }

    public void setPlayPauseButtonIcon(boolean setToPlayIconNotPause) {
        if (setToPlayIconNotPause)
            mPlayPauseButton.setImageResource(R.drawable.playsolidbutton);
        else
            mPlayPauseButton.setImageResource(R.drawable.pausesolidbutton);

        mIsCurrentlyPlayIcon = setToPlayIconNotPause;
    }

    public void setTotalTime(long totalDuration) {
        String totalTimeStr = TimeHelper.convertMsToMMSS(totalDuration);
        mTotalTime.setText(totalTimeStr);
    }

    public void setElapsedTime(long totalDuration) {
        String totalTimeStr = TimeHelper.convertMsToMMSS(totalDuration);
        mElapsedTime.setText(totalTimeStr);
    }

    public void setUseMode(GlobalSettingsSingleton.MODE_STATE modeState) {
        mModeState = modeState;
        if (modeState == GlobalSettingsSingleton.MODE_STATE.CHANT_MODE) {
            mModeIcon.setImageResource(R.drawable.chanticon);
            playerControlsBarListener.onClickChangeToChantMode();
        } else if (modeState == GlobalSettingsSingleton.MODE_STATE.READ_MODE) {
            mModeIcon.setImageResource(R.drawable.readicon2);
            playerControlsBarListener.onClickChangeToReadMode();
        } else if (modeState == GlobalSettingsSingleton.MODE_STATE.LISTEN_MODE) {
            mModeIcon.setImageResource(R.drawable.listenicon);
            playerControlsBarListener.onClickChangeToListenMode();
        }
    }

    public void setPlayerControlsBarListener(PlayerControlsBarListener listener) { playerControlsBarListener = listener; }

    public interface PlayerControlsBarListener {
        void onClickPlayButton();
        void onClickPauseButton();
        void onClickPrevButton();
        void onClickNextButton();

        void onClickChangeToListenMode();
        void onClickChangeToChantMode();
        void onClickChangeToReadMode();
    }
}
