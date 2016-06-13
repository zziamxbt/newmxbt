package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.downlineBean;
import com.example.acer.zzia_mxbt.utils.RoundImageview;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by 刘俊杰 on 2016/6/8.
 */
public class DownlineAdapter extends BaseAdapter{
    List<downlineBean> list;
    Context context;
    LayoutInflater inflater;

    public DownlineAdapter(List<downlineBean> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class  ViewHolder{
        RoundImageview Uhead;
        ImageView Acoverimg;
        TextView Unickname;
        TextView Akind;
        TextView Atitle;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.downline_item,null);
            viewHolder=new ViewHolder();
            viewHolder.Uhead= (RoundImageview) convertView.findViewById(R.id.downline_head);
            viewHolder.Acoverimg= (ImageView) convertView.findViewById(R.id.downline_coverimg);
            viewHolder.Unickname= (TextView) convertView.findViewById(R.id.downline_name);
            viewHolder.Akind= (TextView) convertView.findViewById(R.id.downline_kind);
            viewHolder.Atitle= (TextView) convertView.findViewById(R.id.downline_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Uhead.setImageDrawable(Drawable.createFromStream(list.get(position).getUhead(), "uimg"));
        viewHolder.Acoverimg.setImageDrawable(Drawable.createFromStream(list.get(position).getAcoverimg(), "aimg"));
        viewHolder.Unickname.setText(list.get(position).getUnickname());
        viewHolder.Akind.setText(list.get(position).getAkind());
        viewHolder.Atitle.setText(list.get(position).getAtitle());
        return convertView;
    }
}
