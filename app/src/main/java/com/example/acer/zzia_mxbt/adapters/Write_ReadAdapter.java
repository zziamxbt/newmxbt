package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.Write_ReadBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by C5-0 on 2016/5/30.
 */
public class Write_ReadAdapter extends BaseAdapter {
    private Context mContext;
    private List<Write_ReadBean> mWrite_readBeanList;
    private LayoutInflater mInflater;

    public Write_ReadAdapter(Context mContext, List<Write_ReadBean> mWrite_readBeanList) {
        this.mContext = mContext;
        this.mWrite_readBeanList = mWrite_readBeanList;
        mInflater=LayoutInflater.from(mContext);
    }

    class ViewHolder {
        private TextView Write_ChapterName;
        private TextView Write_ChapterContent;
        private SimpleDraweeView Write_ChapterAuthor;
        private TextView Write_ChapterAuthorName;
        private TextView Write_ArticleTime;
    }

    @Override
    public int getCount() {
        return mWrite_readBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWrite_readBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();;

            convertView=mInflater.inflate(R.layout.listview_write_read,null);

            viewHolder.Write_ChapterName= (TextView) convertView.findViewById(R.id.Write_ChapterName);
            viewHolder.Write_ChapterContent= (TextView) convertView.findViewById(R.id.Write_ChapterContent);
            viewHolder.Write_ChapterAuthor= (SimpleDraweeView) convertView.findViewById(R.id.Write_ChapterAuthor);
            viewHolder.Write_ChapterAuthorName= (TextView) convertView.findViewById(R.id.Write_ChapterAuthorName);
            viewHolder.Write_ArticleTime= (TextView) convertView.findViewById(R.id.Write_ArticleTime);
            convertView.setTag(viewHolder);


        viewHolder.Write_ChapterName.setText(mWrite_readBeanList.get(position).getWrite_ChapterName());
        viewHolder.Write_ChapterContent.setText(mWrite_readBeanList.get(position).getWrite_ChapterContent());
        viewHolder.Write_ChapterAuthor.setImageURI(Uri.parse(mWrite_readBeanList.get(position).getWrite_ChapterAuthor()));
        viewHolder.Write_ChapterAuthorName.setText(mWrite_readBeanList.get(position).getWrite_ChapterAuthorName());
        viewHolder.Write_ArticleTime.setText(mWrite_readBeanList.get(position).getWrite_ArticleTime());

        viewHolder.Write_ChapterAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了本章续写者的头像", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
