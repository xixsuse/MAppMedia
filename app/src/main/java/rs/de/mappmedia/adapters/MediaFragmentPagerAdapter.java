package rs.de.mappmedia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rs.de.mappmedia.fragments.MediaFragment;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.adapters
 * Class:      MediaFragmentPagerAdapter
 */

public class MediaFragmentPagerAdapter extends FragmentPagerAdapter {


    public MediaFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
}
