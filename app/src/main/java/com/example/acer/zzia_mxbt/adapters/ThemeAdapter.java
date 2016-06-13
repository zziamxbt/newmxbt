package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.SubjectBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qiyu on 2016/5/30.
 */
public class ThemeAdapter extends BaseAdapter {
    List<SubjectBean> mList;
    Context mContext;
    LayoutInflater mInflater;//初始化布局文件
    public ThemeAdapter(Context context, List<SubjectBean> list) {
        mContext=context;
        mList=list;
        mInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        //返回数据数量
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        //返回每一行的数据结果
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //返回每一行的id
        return position;
    }

    class ViewHolder{
        //缓存
        SimpleDraweeView imageview;//专题封面
        TextView textView;//专题内容
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            //说明第一次绘制整屏列表
            convertView=mInflater.inflate(R.layout.theme_item,null);
            viewHolder=new ViewHolder();
            //初始化当前布局
            viewHolder.imageview= (SimpleDraweeView) convertView.findViewById(R.id.theme_item_image);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.theme_item_text);
            //将当前布局缓存到布局中
            convertView.setTag(viewHolder);

        }else{
           //说明开始上下滑动，所有的布局采用第一次绘制的缓存布局
            viewHolder= (ViewHolder) convertView.getTag();
        }

        String imagepath=mList.get(position).getPath();//图片路径地址
        Uri uri=Uri.parse(imagepath);//将字符串形式转化为uri
        GenericDraweeHierarchyBuilder builder=new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy=builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setPlaceholderImage(mContext.getResources().getDrawable(R.drawable.jiazai))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        viewHolder.imageview.setController(controller);
        viewHolder.imageview.setHierarchy(hierarchy);

        String text=mList.get(position).getScontent();
        viewHolder.textView.setText(text);
        /**
         * 文本阴影效果
         * android:shadowDx——设置阴影横向坐标开始位置(相对于文本内容)
         android:shadowDy——设置阴影纵向坐标开始位置(相对于文本内容)
         android:shadowRadius——设置阴影的半径
         android:shadowColor——指定文本阴影的颜色
         */
        viewHolder.textView.setShadowLayer(3.0f, 15, 20, 0xff00ff00);
        return convertView;
    }
}
