package rs.de.mappmedia.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rs.de.mappmedia.fragments.MediaFragment;
import rs.de.mappmedia.listeners.MediaAddFABListener;
import rs.de.mappmedia.listeners.SearchViewExpandListener;
import rs.de.mappmedia.listeners.SearchViewQueryTextListener;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.adapters
 * Class:      MediaFragmentPagerAdapter
 */

public class MediaFragmentPagerAdapter extends FragmentPagerAdapter implements TabLayout.OnTabSelectedListener  {


    private SearchViewQueryTextListener searchViewTextQueryListener;
    private SearchViewExpandListener searchViewCollapseListener;
    private MediaAddFABListener mediaAddFABListener;


    public MediaFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        searchViewTextQueryListener = new SearchViewQueryTextListener(fragmentManager);
        searchViewCollapseListener = new SearchViewExpandListener(fragmentManager);
        mediaAddFABListener = new MediaAddFABListener(fragmentManager);
    }

    public SearchViewQueryTextListener getSearchViewTextQueryListener() {
        return searchViewTextQueryListener;
    }

    public SearchViewExpandListener getSearchViewExpandListener() {
        return searchViewCollapseListener;
    }

    public MediaAddFABListener getMediaAddFABListener() {
        return mediaAddFABListener;
    }

    @Override
    public Fragment getItem(int position) {
        return MediaFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Filme";
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int currentSelectedFragmentIndex = tab.getPosition();
        searchViewTextQueryListener.onFragmentSelected(currentSelectedFragmentIndex);
        searchViewCollapseListener.onFragmentSelected(currentSelectedFragmentIndex);
        mediaAddFABListener.onFragmentSelected(currentSelectedFragmentIndex);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public static abstract class MediaFragmentManipulator {

        protected FragmentManager fragmentManager;
        protected int currentSelectedFragmentIndex;

        public MediaFragmentManipulator(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        protected MediaFragment get(int currentSelectedFragmentIndex) {
            return (MediaFragment)fragmentManager.getFragments().get(currentSelectedFragmentIndex);
        }

        public abstract void onFragmentSelected(int currentSelectedFragmentIndex);

    }

}
