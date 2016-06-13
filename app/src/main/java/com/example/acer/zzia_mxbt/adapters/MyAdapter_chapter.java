package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.JavaBean_chapter;

import java.util.List;

/**
 * Created by Gemptc on 2016/5/16.
 */
public class MyAdapter_chapter extends BaseAdapter {
    //接受文章内容listview的位置
    private int mposition;
    private Context mContext;
    private List<JavaBean_chapter> mChapterList;
    ;
    private LayoutInflater mInflater;


    class ViewHolder {
        private ImageView Seclect_Chapter_Item;
        private TextView ChapterNumber;
        private TextView ArticleEasyIntroduction;
    }

    public MyAdapter_chapter(Context mContext, List<JavaBean_chapter> mChapterList, int mposition) {
        this.mContext = mContext;
        this.mChapterList = mChapterList;
        this.mposition = mposition;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mChapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.listview_chapter, null);
            viewHolder = new ViewHolder();
            viewHolder.ChapterNumber = (TextView) convertView.findViewById(
                    R.id.ChapterNumber);
            viewHolder.ArticleEasyIntroduction = (TextView) convertView.findViewById(
                    R.id.ArticleEasyIntroduction);
            viewHolder.Seclect_Chapter_Item = (ImageView) convertView.findViewById(
                    R.id.Seclect_Chapter_Item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ChapterNumber.setText(mChapterList.get(position).getChapterNumber());
        viewHolder.ArticleEasyIntroduction.setText(mChapterList.get(position).getArticleEasyIntroduction());
        for (int i = 0; i < mChapterList.size(); i++) {
            viewHolder.Seclect_Chapter_Item.setImageBitmap(null);
        }
        if (position == mposition) {
            viewHolder.Seclect_Chapter_Item.setImageResource(R.drawable.select_correct);
        }
        return convertView;
    }
}
