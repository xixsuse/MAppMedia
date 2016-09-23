package rs.de.mappmedia.listeners;


import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;
import rs.de.mappmedia.fragments.MediaFragment;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      SearchViewCollapseListener
 */

public class SearchViewExpandListener extends MediaFragmentPagerAdapter.MediaFragmentManipulator
    implements MenuItemCompat.OnActionExpandListener {

    /**
     * The constructor of SearchViewExpandListener
     * @param fragmentManager - the manager that holds all media fragments that were created
     */
    public SearchViewExpandListener(FragmentManager fragmentManager) {
        super(fragmentManager);
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

    /**
     * Is called when the user opens the search view.
     *
     * @param item - the item that was clicked in order to open the search view
     * @return whether or not the action was handled
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    /**
     * Is called when the user collapses the search view.
     *
     * @param item - the item that was clicked in order to collapse the search view
     * @return whether or not the action was handled
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        MediaFragment mediaFragment = get(currentSelectedFragmentIndex);
        if(mediaFragment != null) {
            return mediaFragment.onSearchViewCollapse();
        }
        return false;
    }
}
