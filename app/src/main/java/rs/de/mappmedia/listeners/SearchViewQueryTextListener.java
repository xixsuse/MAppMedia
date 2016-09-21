package rs.de.mappmedia.listeners;

import android.support.v7.widget.SearchView;
import android.util.Log;

import rs.de.mappmedia.activities.MainActivity;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      SearchViewQueryTextListener
 */

public class SearchViewQueryTextListener implements SearchView.OnQueryTextListener {

    private MainActivity mainActivity;
    private SearchView searchView;

    public SearchViewQueryTextListener(MainActivity mainActivity, SearchView searchView) {
        this.mainActivity = mainActivity;
        this.searchView = searchView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("SVQTL", "Query Text Submit : " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("SVQTL", "Query Text Change : " + newText);
        return false;
    }

}
