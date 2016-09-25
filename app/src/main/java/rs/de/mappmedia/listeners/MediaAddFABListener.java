package rs.de.mappmedia.listeners;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.database.models.Movie;
import rs.de.mappmedia.dialog.VersatileMediaDialog;
import rs.de.mappmedia.dialog.inputs.MovieValueFiller;
import rs.de.mappmedia.fragments.MediaFragment;

import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      MediaAddFABListener
 */

public class MediaAddFABListener extends MediaFragmentPagerAdapter.MediaFragmentManipulator implements View.OnClickListener {


    /**
     * The constructor of MediaAddFabListener.
     * @param fragmentManager
     */
    public MediaAddFABListener(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * Is called when the user clicked the action button to add a new media object.
     *
     * @param v - the view from which the action occurred
     */
    @Override
    public void onClick(View v) {
        MediaFragment mediaFragment = get(currentSelectedFragmentIndex);
        int mediaType = mediaFragment.getArguments().getInt(MediaFragment.MEDIA_TYPE_KEY);
        switch(mediaType) {
            case Media.TYPE_MOVIE:
                VersatileMediaDialog dialog = new VersatileMediaDialog(v.getContext());
                dialog.setTitle(R.string.versatile_dialog_add_movie_title);
                dialog.setPositiveButton(R.string.versatile_dialog_add_movie_pos_button);
                dialog.setNegativeButton(R.string.versatile_dialog_neg_button);
                dialog.show(new MovieValueFiller(Movie.newInstance(), mediaFragment));
                break;
        }

    }

    /**
     * Is called when the user selected a different media fragment.
     *
     * @param currentSelectedFragmentIndex - the index of the new selected media fragment
     */
    @Override
    public void onFragmentSelected(int currentSelectedFragmentIndex) {
        this.currentSelectedFragmentIndex = currentSelectedFragmentIndex;
    }

}
