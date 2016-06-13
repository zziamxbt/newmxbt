package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.rank_novel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by 刘俊杰 on 2016/5/17.
 */
public class novel_fragment_Adapter extends BaseAdapter {
    List<rank_novel> list;
    Context context;
    LayoutInflater inflater;

    public novel_fragment_Adapter(List<rank_novel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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

    class ViewHolder {
        TextView novel_num;
        TextView novel_name;
        TextView updatetime;
        TextView isfinish;
        TextView title;
        SimpleDraweeView headimg;
        SimpleDraweeView coverimg;
        // ImageView coverimg;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.novel_item, null);
            viewHolder.novel_num = (TextView) convertView.findViewById(R.id.novel_num);
            viewHolder.novel_name = (TextView) convertView.findViewById(R.id.novel_name);
            viewHolder.updatetime = (TextView) convertView.findViewById(R.id.updatetime);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.isfinish = (TextView) convertView.findViewById(R.id.isfinish);
            viewHolder.headimg = (SimpleDraweeView) convertView.findViewById(R.id.novel_head);
            viewHolder.coverimg = (SimpleDraweeView) convertView.findViewById(R.id.coverimg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setPlaceholderImage(context.getResources().getDrawable(R.drawable.head))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        GenericDraweeHierarchyBuilder builder1 = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy1 = builder1
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .build();

        Uri uri = Uri.parse(list.get(position).getHead());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();

        Uri uri1 = Uri.parse(list.get(position).getCoverimg());
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri1)
                .build();

        viewHolder.headimg.setHierarchy(hierarchy);
        viewHolder.headimg.setController(controller);

       if(position<3){
            viewHolder.novel_num.setText(""+(position+1));
       }else{
           viewHolder.novel_num.setText("");
       }

        viewHolder.novel_name.setText(list.get(position).getNickname());
        viewHolder.updatetime.setText(list.get(position).getUpdatetime());
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.isfinish.setText(list.get(position).getIsfinish());
        viewHolder.coverimg.setHierarchy(hierarchy1);
        viewHolder.coverimg.setController(controller1);

        return convertView;
    }
}
