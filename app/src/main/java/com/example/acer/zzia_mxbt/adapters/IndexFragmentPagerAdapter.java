package com.example.acer.zzia_mxbt.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by acer on 2016/5/7.
 */
//分类滑动的适配器
public class IndexFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mlist;
    public IndexFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        //得到视图列表
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
