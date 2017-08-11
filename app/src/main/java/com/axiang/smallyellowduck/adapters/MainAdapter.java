package com.axiang.smallyellowduck.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by a2389 on 2017/6/22.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private List<String> pagerList;

    private List<Fragment> viewlist;

    public MainAdapter(FragmentManager fm,
                       List<String> pagerList, List<Fragment> viewList) {
        super(fm);
        this.pagerList = pagerList;
        this.viewlist = viewList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return viewlist.get(position);
    }

    @Override
    public int getCount() {
        return viewlist.size();
    }
}
