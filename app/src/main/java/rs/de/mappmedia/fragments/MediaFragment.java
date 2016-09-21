package rs.de.mappmedia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaListViewAdapter;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.fragments
 * Class:      MediaFragment
 */

public class MediaFragment extends Fragment {


    public static MediaFragment newInstance() {
        MediaFragment fragment = new MediaFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_media, container, false);

        ListView mediaList = (ListView) mainView.findViewById(R.id.listview_fragment_media_list);
        mediaList.setAdapter(new MediaListViewAdapter(getContext()));

        return mainView;
    }


}
