package rs.de.mappmedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;
import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.database.models.Movie;
import rs.de.mappmedia.ftp.FTPDownloadAsyncTask;
import rs.de.mappmedia.util.Util;

public class MainActivity extends AppCompatActivity implements FTPDownloadAsyncTask.OnStatusListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MediaFragmentPagerAdapter mediaFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        if (viewPager != null) {
            mediaFragmentPagerAdapter = new MediaFragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mediaFragmentPagerAdapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setOnTabSelectedListener(mediaFragmentPagerAdapter);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_main_add);
        if(floatingActionButton != null) {
            floatingActionButton.setOnClickListener(mediaFragmentPagerAdapter.getMediaAddFABListener());
        }

        DatabaseAccess.getInstance(this); //to load the instance of DatabaseAccess

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.item_main_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(mediaFragmentPagerAdapter.getSearchViewTextQueryListener());
        MenuItemCompat.setOnActionExpandListener(searchItem, mediaFragmentPagerAdapter.getSearchViewExpandListener());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_main_settings:
                Intent settingsIntent = new Intent(this, SettingsPreferenceActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDownloadDone() {
        Log.d(TAG, "onDownloadDone");
    }

    @Override
    public void onDownloadFileFailed() {
        Log.d(TAG, "onDownloadFileFailed");
    }

    @Override
    public void onConnectionFailed() {
        Log.d(TAG, "onConnectionFailed");
    }

    @Override
    public void onFailDisconnect() {
        Log.d(TAG, "onFailDisconnect");
    }

}
