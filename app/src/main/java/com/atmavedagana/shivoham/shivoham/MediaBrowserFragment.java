/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atmavedagana.shivoham.shivoham.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A Fragment that lists all the various browsable queues available
 * from a {@link android.service.media.MediaBrowserService}.
 * <p/>
 * It uses a {@link MediaBrowserCompat} to connect to the {com.example.android.uamp.MusicService}.
 * Once connected, the fragment subscribes to get all the children.
 * All {@link MediaBrowserCompat.MediaItem}'s that can be browsed are shown in a ListView.
 */
public class MediaBrowserFragment extends Fragment {

    private static final String TAG = LogHelper.makeLogTag(MediaBrowserFragment.class);
    private static final String ARG_MEDIA_ID = "media_id";

    private BrowseAdapter mBrowserAdapter;
    private String mMediaId;
    private MediaFragmentListener mMediaFragmentListener;
    private View mErrorView;
    private TextView mErrorMessage;

//    // Receive callbacks from the MediaController. Here we update our state such as which queue
//    // is being shown, the current title and description and the PlaybackState.
//    private final MediaControllerCompat.Callback mMediaControllerCallback =
//            new MediaControllerCompat.Callback() {
//        @Override
//        public void onMetadataChanged(MediaMetadataCompat metadata) {
//            super.onMetadataChanged(metadata);
//            if (metadata == null) {
//                return;
//            }
//            LogHelper.d(TAG, "Received metadata change to media ",
//                    metadata.getDescription().getMediaId());
//            mBrowserAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
//            super.onPlaybackStateChanged(state);
//            LogHelper.d(TAG, "Received state change: ", state);
//
//            mBrowserAdapter.notifyDataSetChanged();
//        }
//    };
//
//    private final MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback =
//        new MediaBrowserCompat.SubscriptionCallback() {
//            @Override
//            public void onChildrenLoaded(@NonNull String parentId,
//                                         @NonNull List<MediaBrowserCompat.MediaItem> children) {
//                try {
//                    LogHelper.d(TAG, "fragment onChildrenLoaded, parentId=" + parentId +
//                        "  count=" + children.size());
//                    mBrowserAdapter.clear();
//                    for (MediaBrowserCompat.MediaItem item : children) {
//                        mBrowserAdapter.add(item);
//                    }
//                    mBrowserAdapter.notifyDataSetChanged();
//                } catch (Throwable t) {
//                    LogHelper.e(TAG, "Error on childrenloaded", t);
//                }
//            }
//
//            @Override
//            public void onError(@NonNull String id) {
//                LogHelper.e(TAG, "browse fragment subscription onError, id=" + id);
//                Toast.makeText(getActivity(), R.string.error_loading_media, Toast.LENGTH_LONG).show();
//
//            }
//        };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // If used on an activity that doesn't implement MediaFragmentListener, it
        // will throw an exception as expected:
        mMediaFragmentListener = (MediaFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogHelper.d(TAG, "fragment.onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mErrorView = rootView.findViewById(R.id.playback_error);
        mErrorMessage = (TextView) mErrorView.findViewById(R.id.error_message);

        mBrowserAdapter = new BrowseAdapter(getActivity());

        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(mBrowserAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AVGMediaItem item = mBrowserAdapter.getItem(position);
                mMediaFragmentListener.onMediaItemSelected(item);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

//        // fetch browsing information to fill the listview:
//        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();
//
//        LogHelper.d(TAG, "fragment.onStart, mediaId=", mMediaId,
//                "  onConnected=" + mediaBrowser.isConnected());
//
//        if (mediaBrowser.isConnected()) {
//            onConnected();
//        }

        ArrayList<AVGMediaItem> children = mMediaFragmentListener.getMediaBrowser().getMediaItems(AVGMediaBrowser.BROWSE_TYPE.BROWSE_DROPBOX_AND_LOCAL);

        for (AVGMediaItem item : children) {
            mBrowserAdapter.add(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();
//        if (mediaBrowser != null && mediaBrowser.isConnected() && mMediaId != null) {
//            mediaBrowser.unsubscribe(mMediaId);
//        }
//        MediaControllerCompat controller = ((FragmentActivity) getActivity())
//                .getSupportMediaController();
//        if (controller != null) {
//            controller.unregisterCallback(mMediaControllerCallback);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMediaFragmentListener = null;
    }
//
//    public String getMediaId() {
//        Bundle args = getArguments();
//        if (args != null) {
//            return args.getString(ARG_MEDIA_ID);
//        }
//        return null;
//    }
//
//    public void setMediaId(String mediaId) {
//        Bundle args = new Bundle(1);
//        args.putString(MediaBrowserFragment.ARG_MEDIA_ID, mediaId);
//        setArguments(args);
//    }

    // Called when the MediaBrowser is connected. This method is either called by the
    // fragment.onStart() or explicitly by the activity in the case where the connection
    // completes after the onStart()
//    public void onConnected() {
//        if (isDetached()) {
//            return;
//        }
//        mMediaId = getMediaId();
//        if (mMediaId == null) {
//            mMediaId = mMediaFragmentListener.getMediaBrowser().getRoot();
//        }
//        updateTitle();
//
//        // Unsubscribing before subscribing is required if this mediaId already has a subscriber
//        // on this MediaBrowser instance. Subscribing to an already subscribed mediaId will replace
//        // the callback, but won't trigger the initial callback.onChildrenLoaded.
//        //
//        // This is temporary: A bug is being fixed that will make subscribe
//        // consistently call onChildrenLoaded initially, no matter if it is replacing an existing
//        // subscriber or not. Currently this only happens if the mediaID has no previous
//        // subscriber or if the media content changes on the service side, so we need to
//        // unsubscribe first.
//        mMediaFragmentListener.getMediaBrowser().unsubscribe(mMediaId);
//
//        mMediaFragmentListener.getMediaBrowser().subscribe(mMediaId, mSubscriptionCallback);
//
//        // Add MediaController callback so we can redraw the list when metadata changes:
//        MediaControllerCompat controller = ((FragmentActivity) getActivity())
//                .getSupportMediaController();
//        if (controller != null) {
//            controller.registerCallback(mMediaControllerCallback);
//        }
//    }

//
//    private void updateTitle() {
//        if (MediaIDHelper.MEDIA_ID_ROOT.equals(mMediaId)) {
//            mMediaFragmentListener.setToolbarTitle(null);
//            return;
//        }
//
//        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();
//        mediaBrowser.getItem(mMediaId, new MediaBrowserCompat.ItemCallback() {
//            @Override
//            public void onItemLoaded(MediaBrowserCompat.MediaItem item) {
//                mMediaFragmentListener.setToolbarTitle(
//                        item.getDescription().getTitle());
//            }
//        });
//    }

    // An adapter for showing the list of browsed MediaItem's
    private static class BrowseAdapter extends ArrayAdapter<AVGMediaItem> {

        public BrowseAdapter(Activity context) {
            super(context, R.layout.media_list_item, new ArrayList<AVGMediaItem>());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AVGMediaItem item = getItem(position);
            return MediaItemViewHolder.setupListView((Activity) getContext(), convertView, parent,
                    item);
        }
    }

    public interface MediaFragmentListener {
        void onMediaItemSelected(AVGMediaItem item);
        void setToolbarTitle(CharSequence title);
        AVGMediaBrowser getMediaBrowser();
    }

}