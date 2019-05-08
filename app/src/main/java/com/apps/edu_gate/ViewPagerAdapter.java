package com.apps.edu_gate;

import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstfragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }



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
        if(lstfragment != null){
            for(Fragment fragment : lstfragment){
                if(fragment != null && fragment.isVisible()){
                    Log.e("hi", String.valueOf(count));
                    return getPageTitle(count);
                }
                count++;
            }
        }
        return null;
    }

}