package com.example.shaad.snpppapp.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaad on 05-03-2018.
 */

public class FragmentPagerSliderAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DashboardNavigationAdap";
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public FragmentPagerSliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

}
