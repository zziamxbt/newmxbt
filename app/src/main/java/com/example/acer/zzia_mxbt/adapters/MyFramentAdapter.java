package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.rank_author;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;



/**
 * Created by 刘俊杰 on 2016/5/16.
 */
public class MyFramentAdapter extends BaseAdapter {
    List<rank_author> list;
    Context context;
    LayoutInflater inflater;

    public MyFramentAdapter(List<rank_author> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
       // Log.e("tag",list.toString());
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
    class ViewHolder {
        SimpleDraweeView headimg;
        TextView num;
        TextView name;
        TextView content;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.author_item,null);
            viewHolder=new ViewHolder();
            viewHolder.headimg= (SimpleDraweeView) convertView.findViewById(R.id.head);
            viewHolder.num= (TextView) convertView.findViewById(R.id.num);
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            viewHolder.content= (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
               .setPlaceholderImage(context.getResources().getDrawable(R.drawable.head))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();

        Uri uri=Uri.parse(list.get(position).getUhead());

        DraweeController controller= Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();

        viewHolder.headimg.setHierarchy(hierarchy);
        viewHolder.headimg.setController(controller);

       if(position<3){
           viewHolder.num.setText(""+(position+1));
       }else{
           viewHolder.num.setText("");
       }

        viewHolder.name.setText(list.get(position).getUnickname());
        viewHolder.content.setText("曾参与续写佳作"+list.get(position).getUcontent()+"等....");
        return convertView;
    }
}
