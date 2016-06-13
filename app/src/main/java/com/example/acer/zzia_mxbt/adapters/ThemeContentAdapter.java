package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.SubjectArticleBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qiyu on 2016/5/31.
 */
public class ThemeContentAdapter extends BaseAdapter {

    List<SubjectArticleBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public ThemeContentAdapter(Context mContext, List<SubjectArticleBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        SimpleDraweeView headImg;
        TextView nickName;
        TextView time;
        ImageView kindImg;
        TextView kindContent;
        SimpleDraweeView background;
        TextView title;
        TextView content;
        TextView collectNum;// 收藏数量
        TextView recomendNum;// 推荐数量
        TextView commentNum;// 评论数量
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.theme_content_item, null);
            viewHolder = new ViewHolder();

            viewHolder.headImg = (SimpleDraweeView) convertView.findViewById(R.id.theme_article_item_head);
            viewHolder.nickName = (TextView) convertView.findViewById(R.id.theme_article_item_nickname);
            viewHolder.time = (TextView) convertView.findViewById(R.id.theme_article_item_time);
            viewHolder.kindImg = (ImageView) convertView.findViewById(R.id.theme_article_item_kindimg);
            viewHolder.kindContent = (TextView) convertView.findViewById(R.id.theme_article_item_kind);
            viewHolder.background = (SimpleDraweeView) convertView.findViewById(R.id.theme_article_item_background);
            viewHolder.title = (TextView) convertView.findViewById(R.id.theme_article_item_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.theme_article_item_content);
            viewHolder.collectNum= (TextView) convertView.findViewById(R.id.theme_article_item_collectnum);
            viewHolder.recomendNum= (TextView) convertView.findViewById(R.id.theme_article_item_recommendnum);
            viewHolder.commentNum= (TextView) convertView.findViewById(R.id.theme_article_item_commentnum);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //1,头像处理(使用第三方框架，将头像转化为原型)
        String headImgPath=mList.get(position).getHeadImg();
        Uri uri=Uri.parse(headImgPath);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(mContext.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        viewHolder.headImg.setHierarchy(hierarchy);
        viewHolder.headImg.setController(controller);


        //2,nickName处理
        viewHolder.nickName .setText(mList.get(position).getNickName());

        //3,time,处理
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date before=df.parse(mList.get(position).getTime()+"");
            Date now=df.parse(df.format(new Date()));
            long differ=now.getTime()-before.getTime();
            long days=differ/(1000 * 60 * 60 * 24);
            viewHolder.time.setText(days+"天前更新");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //4,kindImg
        String kind=mList.get(position).getKind();
        if(kind.startsWith("真事")){
            viewHolder.kindImg.setImageResource(R.drawable.ring_green);
        }else if (kind.startsWith("创作")){
            viewHolder.kindImg.setImageResource(R.drawable.ring_yellow);
        }else if (kind.startsWith("灵异")){
            viewHolder.kindImg.setImageResource(R.drawable.ring_blue);
        }else if(kind.startsWith("生活")){
            viewHolder.kindImg.setImageResource(R.drawable.ring_red);
        }

        //5,kind
        viewHolder.kindContent.setText(mList.get(position).getKind());

        //6,background
        String backPath=mList.get(position).getBackground();
        Uri uri2=Uri.parse(backPath);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(mContext.getResources().getDrawable(R.drawable.index_img1))
                .build();

        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();
        viewHolder.background.setHierarchy(hierarchy2);
        viewHolder.background.setController(controller2);

        //7,title
        viewHolder.title.setText(mList.get(position).getTitle());

        //8,content
        viewHolder.content.setText(mList.get(position).getContent());

        //9,collectNum
        viewHolder.collectNum.setText(mList.get(position).getCollectNum()+"");


        //10,recommendNum
       viewHolder.recomendNum.setText(mList.get(position).getRecomendNum()+"");

        //11,commentNum
        viewHolder.commentNum.setText(mList.get(position).getCommentNum()+"");

        return convertView;
    }
}
