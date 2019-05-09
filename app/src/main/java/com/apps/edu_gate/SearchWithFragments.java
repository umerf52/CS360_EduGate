package com.apps.edu_gate;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchWithFragments extends BaseActivity {

    private static final String TAG = "SearchPageActivity";

    private ViewPager viewPager;
    private ViewPagerAdapter vadapter;

    SearchView searchView;
    String searching;
    String x;

    private List<DataUpdateListener> mListeners;

    public interface DataUpdateListener {
        void onDataUpdate(String query, String ident);
    }

    public synchronized void registerDataUpdateListener(DataUpdateListener listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataUpdateListener(DataUpdateListener listener) {
        mListeners.remove(listener);
    }

    public synchronized void dataUpdated(String query, String ident) {
        for (DataUpdateListener listener : mListeners) {
            listener.onDataUpdate(query, ident);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = query;
                searching = searching.toLowerCase();
                if (!searching.isEmpty()) {
                    int position = viewPager.getCurrentItem();
                    x = (String)vadapter.getPageTitle(position);
                    Log.e(TAG, x );
                    dataUpdated(searching, x);
                    if( ! searchView.isIconified()) {
                        searchView.setIconified(false);
                    }return false;
                } else {
                    Toast.makeText(getBaseContext(), "Please add item to search!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searching = newText;
                int position = viewPager.getCurrentItem();
                x = (String)vadapter.getPageTitle(position);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_fragments);
        setTitle("Search Tutors");

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);
        Fragment frag1 = new FragmentName();
        Fragment frag2 = new FragmentSubject();
        Fragment frag3 = new FragmentLocation();
        Fragment frag4 = new FragmentGrade();
        mListeners = new ArrayList<>();
        FragmentManager fm = getSupportFragmentManager();
        vadapter = new ViewPagerAdapter(fm);
        vadapter.AddFragment(frag1, "Name");
        vadapter.AddFragment(frag2, "Subject");
        vadapter.AddFragment(frag3, "Location");
        vadapter.AddFragment(frag4, "Grade");

        viewPager.setAdapter(vadapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
