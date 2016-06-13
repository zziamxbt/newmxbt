package com.example.acer.zzia_mxbt.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by qiyu on 2016/6/3.
 */
public class SearchThemeAdapter extends PagerAdapter {
    private List<View> mViewList;


    public SearchThemeAdapter(List<View> viewList) {
        mViewList = viewList;

    }


    /**
     * 返回页卡的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mViewList.size();
    }

    /**
     * 判断View是否来自对象
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 实例化一个页卡
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("INFO", "instantiate item:" + position);
        container.addView(mViewList.get(position), 0);
        return mViewList.get(position);
    }

    /**
     * 销毁一个页卡
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

}
