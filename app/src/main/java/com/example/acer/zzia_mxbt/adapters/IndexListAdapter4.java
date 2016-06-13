package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by acer on 2016/5/7.
 */
public class IndexListAdapter4 extends BaseAdapter {


    Context mcontext;
    static  List<IndexBean> mlist;
    LayoutInflater layoutInflater;
    public IndexListAdapter4(Context context , List<IndexBean> list){
        this.mcontext = context;
        this.mlist = list;
        layoutInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        SimpleDraweeView headimg;
        TextView name;
        TextView time;
        TextView maincontent;
        ImageView kindimg;
        TextView title;
        TextView kindcontent;
        SimpleDraweeView mainimg;



    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.index_content,null);
            viewHolder = new ViewHolder();
            viewHolder.headimg= (SimpleDraweeView) convertView.findViewById(R.id.index_head);
            viewHolder.name = (TextView) convertView.findViewById(R.id.index_username);
            viewHolder.time = (TextView) convertView.findViewById(R.id.index_time);
            viewHolder.kindimg = (ImageView) convertView.findViewById(R.id.index_list_kind_img);
            viewHolder.kindcontent = (TextView) convertView.findViewById(R.id.index_list_kind);
            viewHolder.mainimg = (SimpleDraweeView) convertView.findViewById(R.id.index_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_title);

            viewHolder.maincontent = (TextView) convertView.findViewById(R.id.list_maincontent);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //头像处理
        String path = mlist.get(position).getHeadImg();
        Uri uri = Uri.parse(path);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mcontext.getResources());
               GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(mcontext.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();

//
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        viewHolder.headimg.setHierarchy(hierarchy);
        viewHolder.headimg.setController(controller);



        viewHolder.headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "头像被点击了", Toast.LENGTH_SHORT).show();
            }
        });




        viewHolder.name.setText(mlist.get(position).getNickName());
        viewHolder.time.setText(mlist.get(position).getDateTime());
        String kind = mlist.get(position).getKind();
        if(kind.startsWith("真事")){
            viewHolder.kindimg.setImageResource(R.drawable.ring_green);
        }else if (kind.startsWith("创作")){
            viewHolder.kindimg.setImageResource(R.drawable.ring_yellow);
        }else if (kind.startsWith("灵异")){
            viewHolder.kindimg.setImageResource(R.drawable.ring_blue);
        }else if(kind.startsWith("生活")){
            viewHolder.kindimg.setImageResource(R.drawable.ring_red);
        }

        viewHolder.kindcontent.setText(mlist.get(position).getKind());




        //内容图片处理
        String path2 = mlist.get(position).getBackGround();
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(mcontext.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(mcontext.getResources().getDrawable(R.drawable.index_img1))
                .build();



        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();

        viewHolder.mainimg.setController(controller2);
        viewHolder.mainimg.setHierarchy(hierarchy2);




        viewHolder.title.setText(mlist.get(position).getTitle());

        //读取第一章内容

        viewHolder.maincontent.setText(mlist.get(position).getContent());

        return convertView;
    }
}
