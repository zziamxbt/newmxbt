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
import com.example.acer.zzia_mxbt.bean.LableBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qiyu on 2016/6/6.
 */
public class LableAdapter extends BaseAdapter {
    List<LableBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public LableAdapter(Context context, List<LableBean> list) {
        mContext = context;
        mList = list;
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
        //缓存
        SimpleDraweeView imageView;//标签封面
        TextView textView;//标签内容
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            //说明第一次绘制整屏列表
            convertView = mInflater.inflate(R.layout.lable_item, null);
            viewHolder = new ViewHolder();
            //初始化当前布局
            viewHolder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.lable_item_imageview);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.lable_item_textview);
            //将当前布局缓存到布局中
            convertView.setTag(viewHolder);

        } else {
            //说明开始上下滑动，所有的布局采用第一次绘制的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*String image = mList.get(position).getPath();
        viewHolder.imageView.setImageURI(Uri.parse(image));*/
        String imagepath=mList.get(position).getPath();//图片路径地址
        Uri uri=Uri.parse(imagepath);//将字符串形式转化为uri
        GenericDraweeHierarchyBuilder builder=new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy=builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setPlaceholderImage(mContext.getResources().getDrawable(R.drawable.jiazai))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
       viewHolder.imageView.setHierarchy(hierarchy);
        viewHolder.imageView.setController(controller);

        String text = mList.get(position).getLcontent();
        viewHolder.textView.setText(text);

        return convertView;
    }
}
