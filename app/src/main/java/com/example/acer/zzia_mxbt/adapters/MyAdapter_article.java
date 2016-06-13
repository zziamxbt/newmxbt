package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.JavaBean_article;
import com.example.acer.zzia_mxbt.utils.SetPicture;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by Gemptc on 2016/5/14.
 */
public class MyAdapter_article extends BaseAdapter {
    //文章內容默認大小
    public static final int size = 13;
    private int myPosition;
    private Context mContext;
    private List<JavaBean_article> mArticleList;
    private LayoutInflater mInflater;
    ViewHolder viewHolder = new ViewHolder();

    static class ViewHolder {
        private TextView ArticleContent;
        private TextView ArticleTime;
        private TextView ChapterTitle;
        private TextView ChapterAuthorName;
        private SimpleDraweeView mauthor_portraits;
    }

    public MyAdapter_article(Context mContext, List<JavaBean_article> mArticleList, int myPosition) {
        this.mContext = mContext;
        this.mArticleList = mArticleList;
        this.myPosition = myPosition;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(
                    R.layout.listview_article, null);

            viewHolder.ArticleContent = (TextView) convertView.findViewById(
                    R.id.ArticleContent);
            viewHolder.ArticleTime = (TextView) convertView.findViewById(
                    R.id.ArticleTime);
            viewHolder.ChapterTitle = (TextView) convertView.findViewById(
                    R.id.ChapterTitle);
            viewHolder.ChapterAuthorName = (TextView) convertView.findViewById(
                    R.id.ChapterAuthorName);
            viewHolder.mauthor_portraits = (SimpleDraweeView) convertView.findViewById(R.id.ChapterAuthor);

        viewHolder.ArticleContent.setText(mArticleList.get(position).getArticleContent());
        viewHolder.ArticleTime.setText(mArticleList.get(position).getArticleTime());
        viewHolder.ChapterTitle.setText(mArticleList.get(position).getChapterTitle());
        viewHolder.ChapterAuthorName.setText(mArticleList.get(position).getChapterAuthorName());

        viewHolder.mauthor_portraits.setController(SetPicture.controller(Uri.parse(mArticleList.get(position).getmAuthor_portraits())));
        viewHolder.mauthor_portraits.setHierarchy(SetPicture.hierarchy(mArticleList.get(position).getmAuthor_portraits(),mContext));


        viewHolder.ArticleContent.setTextSize(size + myPosition);
        viewHolder.ChapterTitle.setTextSize(size + myPosition);

        viewHolder.mauthor_portraits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击的本章节续写者的头像,第"+(position+1)+"章节续写者", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }


    //取出文章内容


}
