package rs.de.mappmedia.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaListViewAdapter;
import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.listeners.OnUpdateListener;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.fragments
 * Class:      MediaFragment
 */

public class MediaFragment extends Fragment implements OnUpdateListener {

    public static final String MEDIA_TYPE_KEY = "key_media_type";

    private MediaListViewAdapter mediaListViewAdapter;

    public static MediaFragment newInstance(int mediaType) {
        MediaFragment fragment = new MediaFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(MEDIA_TYPE_KEY, mediaType);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_media, container, false);
        ListView mediaList = (ListView) mainView.findViewById(R.id.listview_fragment_media_list);

        mediaListViewAdapter = new MediaListViewAdapter(getContext());
        mediaListViewAdapter.setUpdateListener(this);
        mediaList.setAdapter(mediaListViewAdapter);

        loadAllMediaItems();
        return mainView;
    }

    public boolean onSearchQuerySubmitEvent(String query) {
        ArrayList<Media> mediaSearchResult = DatabaseAccess.localMediaSearch(query,
                this.getArguments().getInt(MEDIA_TYPE_KEY));
        mediaListViewAdapter.setResultMediaItems(mediaSearchResult);
        return false;
    }

    public boolean onSearchViewCollapse() {
        loadAllMediaItems();
        return true;
    }

    private void loadAllMediaItems() {
        ArrayList<Media> allMediaResult = DatabaseAccess.localAllMedia(
                this.getArguments().getInt(MEDIA_TYPE_KEY));
        mediaListViewAdapter.setResultMediaItems(allMediaResult);
    }

    @Override
    public void onUpdate() {
        loadAllMediaItems();
    }
}
