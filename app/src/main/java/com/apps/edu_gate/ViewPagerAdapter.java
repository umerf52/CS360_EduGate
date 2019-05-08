package com.apps.edu_gate;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstfragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();



    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        return lstfragment.get(position);
    }

    @Override
    public int getCount(){
        return lstTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return lstTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String title){

        lstfragment.add(fragment);
        lstTitles.add(title);
    }

    public CharSequence getVisibleFragment(){
        int count = 0;

        List<Fragment> fragments = lstfragment;
        int x = lstfragment.size();
        String t = String.valueOf(x);
        Log.e("Aasa", t);
        if(fragments != null){
            for(Fragment fragment : fragments){
                Log.e("sa",String.valueOf(count));
                if(fragment != null && fragment.isVisible())
                    return getPageTitle(count);
                count++;
            }
        }
        return null;
    }

    public Fragment getVisibleFragment2(){
        List<Fragment> fragments = lstfragment;
        int x = lstfragment.size();
        String t = String.valueOf(x);
        Log.e("Aasa", t);
        if(fragments != null){
            int count = 0;
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


}