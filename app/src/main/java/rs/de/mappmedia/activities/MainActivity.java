package rs.de.mappmedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaFragmentPagerAdapter;
import rs.de.mappmedia.ftp.FTPDownloader;
import rs.de.mappmedia.listeners.SearchViewQueryTextListener;

public class MainActivity extends AppCompatActivity implements FTPDownloader.OnDownloadDoneListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        if (viewPager != null) {
            MediaFragmentPagerAdapter mediaFragmentPagerAdapter = new MediaFragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mediaFragmentPagerAdapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

        new FTPDownloader(this).execute("[host]", "[user]", "[password]", "[remote_file]", "[local_file]");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.item_main_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchViewQueryTextListener(this, searchView));
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
        /**
         * TODO Search for downloaded file and load the database
         */
    }
}
