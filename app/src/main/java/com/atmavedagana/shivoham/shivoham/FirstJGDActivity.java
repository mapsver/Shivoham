package com.atmavedagana.shivoham.shivoham;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.atmavedagana.shivoham.shivoham.utils.FileUtils;
import com.atmavedagana.shivoham.shivoham.utils.LogHelper;

public class FirstJGDActivity extends ActionBarCastActivity
                                implements MediaBrowserFragment.MediaFragmentListener {

    private static final String TAG = LogHelper.makeLogTag(FirstJGDActivity.class);
    private static final String SAVED_MEDIA_ID="com.example.android.uamp.MEDIA_ID";
    private static final String FRAGMENT_TAG = "uamp_list_container";

    private AVGMediaBrowser mMediaBrowser = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstjgd);

        initializeToolbar(R.menu.main);
        initializeFromParams(savedInstanceState, getIntent());

        // todo@shiv: If saved state.. then restore the old setting
        GlobalSettingsSingleton.getInstance().setDefaults();

        mMediaBrowser = new AVGMediaBrowser(FileUtils.getMediaStorageRootFolder(this));
    }

    protected void initializeFromParams(Bundle savedInstanceState, Intent intent) {
        String mediaId = null;
        // check if we were started from a "Play XYZ" voice search. If so, we save the extras
        // (which contain the query details) in a parameter, so we can reuse it later, when the
        // MediaSession is connected.
        if (savedInstanceState != null) {
           // If there is a saved media ID, use it
            mediaId = savedInstanceState.getString(SAVED_MEDIA_ID);
        }
        navigateToBrowser(mediaId);
    }

    private void navigateToBrowser(String mediaId) {
        LogHelper.d(TAG, "navigateToBrowser, mediaId=" + mediaId);
        MediaBrowserFragment fragment = getBrowseFragment();

        if (fragment == null ) //|| !TextUtils.equals(fragment.getMediaId(), mediaId))
        {
            fragment = new MediaBrowserFragment();
//            fragment.setMediaId(mediaId);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
            transaction.replace(R.id.container, fragment, FRAGMENT_TAG);
            // If this is not the top level media (root), we add it to the fragment back stack,
            // so that actionbar toggle and Back will work appropriately:
            if (mediaId != null) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }
    }

    @Override
    public void onMediaItemSelected(AVGMediaItem item) {

        LogHelper.d(TAG, "onMediaItemSelected");

        String[] strArr = new String[3];
        strArr[0] = item.getmPDFFileSaveToPath();
        strArr[1] = item.getmAudioFileSaveToPath();
        strArr[2] = item.getmDisplayTitle();
        Intent openPDFIntent = new Intent(this, PDF_Activity.class);
        openPDFIntent.putExtra(Intent.ACTION_OPEN_DOCUMENT, strArr);

        startActivity(openPDFIntent);

//        if (item.isPlayable()) {
//            getSupportMediaController().getTransportControls()
//                    .playFromMediaId(item.getMediaId(), null);
//        } else if (item.isBrowsable()) {
//            navigateToBrowser(item.getMediaId());
//        } else {
//            LogHelper.w(TAG, "Ignoring MediaItem that is neither browsable nor playable: ",
//                    "mediaId=", item.getMediaId());
//        }
    }

    @Override
    public void setToolbarTitle(CharSequence title) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, PreferenceSettings.class);
            startActivity(prefsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public AVGMediaBrowser getMediaBrowser() {
        return mMediaBrowser;
    }

    private MediaBrowserFragment getBrowseFragment() {
        return (MediaBrowserFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
