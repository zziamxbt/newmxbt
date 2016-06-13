package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.ArticleCommentBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 刘俊杰 on 2016/6/1.
 */
public class ArticleCommentAdapter extends BaseAdapter {
    List<ArticleCommentBean> list;
    Context context;
    LayoutInflater inflater;

    public ArticleCommentAdapter(List<ArticleCommentBean> list, Context context) {
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
    class ViewHolder {
        SimpleDraweeView headimg;
        TextView createtime;
        TextView name;
        TextView mcontent;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.article_comment_item,null);
            viewHolder=new ViewHolder();
            viewHolder.headimg= (SimpleDraweeView) convertView.findViewById(R.id.article_comment_head);
            viewHolder.createtime= (TextView) convertView.findViewById(R.id.article_comment_createtime);
            viewHolder.name= (TextView) convertView.findViewById(R.id.article_comment_name);
            viewHolder.mcontent= (TextView) convertView.findViewById(R.id.article_comment_content);
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
        Uri uri=Uri.parse(list.get(position).getHead());
        DraweeController controller= Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        viewHolder.headimg.setHierarchy(hierarchy);
        viewHolder.headimg.setController(controller);
        viewHolder.name.setText(list.get(position).getNickname());
        viewHolder.createtime.setText(list.get(position).getCreatetime());
        viewHolder.mcontent.setText(list.get(position).getContent());
        return convertView;
    }
}
