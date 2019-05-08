package com.apps.edu_gate;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchWithFragments extends BaseActivity {

    private static final String TAG = "SearchPageActivity";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter vadapter;
    private Fragment frag1;
    private Fragment frag2;
    private Fragment frag3;
    private Fragment frag4;
    private FragmentManager fm;

    SearchView searchView;
    String searching;

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
                if (searching != null && !searching.isEmpty()) {
                    String x = (String)vadapter.getVisibleFragment();
                    Log.e(TAG, x );
//                    Toast.makeText(getBaseContext(), searching + x, Toast.LENGTH_LONG).show();
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
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_with_fragments);
        setTitle("Search Tutors");
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        frag1 = new FragmentName();
        frag2 = new FragmentLocation();
        frag3 = new FragmentSubject();
        frag4 = new FragmentGrade();
        mListeners = new ArrayList<>();
        fm = getSupportFragmentManager();
        vadapter = new ViewPagerAdapter(fm);
        vadapter.AddFragment(frag1, "Name");
        vadapter.AddFragment(frag2, "Location");
        vadapter.AddFragment(frag3, "Subject");
        vadapter.AddFragment(frag4, "Grade");

        viewPager.setAdapter(vadapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
