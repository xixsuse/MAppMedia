package rs.de.mappmedia.listeners;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;

import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;
import rs.de.mappmedia.fragments.MediaFragment;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      SearchViewQueryTextListener
 */

public class SearchViewQueryTextListener extends MediaFragmentPagerAdapter.MediaFragmentManipulator
    implements SearchView.OnQueryTextListener {

    /**
     * The constructor of SearchViewQueryTextListener
     * @param fragmentManager - the manager that holds all media fragments that were created
     */
    public SearchViewQueryTextListener(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * Is called when the user typed in a search query into the search view and submitted
     * it by clicking onto the search icon.
     *
     * @param query - the query to search for
     * @return whether or not the query was handled
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        MediaFragment mediaFragment = get(currentSelectedFragmentIndex);
        if(mediaFragment != null) {
            return mediaFragment.onSearchQuerySubmitEvent(query);
        }
        return false;
    }

    /**
     * Is called when the user changed the search query in the search view.
     *
     * @param newText - the new query to search for
     * @return whether or not the new query was handled
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
