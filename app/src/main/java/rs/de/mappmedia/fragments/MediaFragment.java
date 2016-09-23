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

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.fragments
 * Class:      MediaFragment
 */

public class MediaFragment extends Fragment implements MediaListViewAdapter.OnUpdateListener {

    private ListView mediaList;
    private MediaListViewAdapter mediaListViewAdapter;

    public static MediaFragment newInstance() {
        MediaFragment fragment = new MediaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_media, container, false);
        mediaList = (ListView) mainView.findViewById(R.id.listview_fragment_media_list);
        mediaListViewAdapter = new MediaListViewAdapter(getContext());
        mediaListViewAdapter.setUpdateListener(this);
        mediaList.setAdapter(mediaListViewAdapter);
        loadAllMediaItems();
        return mainView;
    }

    public boolean onSearchQuerySubmitEvent(String query) {
        DatabaseAccess dbAccess = DatabaseAccess.getInstance(getContext());
        dbAccess.open(true, null);
        ArrayList<Media> resultMediaItems = dbAccess.searchMedia(query, Media.SEARCH_TYPE_MOVIE);
        dbAccess.close();
        mediaListViewAdapter.setResultMediaItems(resultMediaItems);
        return false;
    }

    public boolean onSearchViewCollapse() {
        loadAllMediaItems();
        return true;
    }

    private void loadAllMediaItems() {
        DatabaseAccess dbAccess = DatabaseAccess.getInstance(getContext());
        dbAccess.open(true, null);
        ArrayList<Media> resultMediaItems = dbAccess.getAllMedia(Media.SEARCH_TYPE_MOVIE);
        dbAccess.close();
        mediaListViewAdapter.setResultMediaItems(resultMediaItems);
    }


    @Override
    public void onUpdate() {
        loadAllMediaItems();
    }
}
