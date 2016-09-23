package rs.de.mappmedia.listeners;

import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;

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
