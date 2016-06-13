package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    private List<IndexBean> datas;
    private Context context;
    private List<Integer> lists;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MyRecyclerViewAdapter2(Context context, List<IndexBean> datas) {
        this.datas = datas;
        this.context = context;

    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        //-----------------------
        void onItemClick(View view, int position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.index_content, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setTag(position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();

        holder.itemView.setLayoutParams(params);
        //头像处理
        String path = datas.get(position).getHeadImg();
        Uri uri = Uri.parse(path);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(context.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();

//
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        holder.headimg.setHierarchy(hierarchy);
        holder.headimg.setController(controller);



        holder.headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "头像被点击了", Toast.LENGTH_SHORT).show();
            }
        });




        holder.name.setText(datas.get(position).getNickName());
        holder.time.setText(datas.get(position).getDateTime());
        String kind = datas.get(position).getKind();
        if(kind.startsWith("真事")){
            holder.kindimg.setImageResource(R.drawable.ring_green);
        }else if (kind.startsWith("创作")){
            holder.kindimg.setImageResource(R.drawable.ring_yellow);
        }else if (kind.startsWith("灵异")){
            holder.kindimg.setImageResource(R.drawable.ring_blue);
        }else if(kind.startsWith("生活")){
            holder.kindimg.setImageResource(R.drawable.ring_red);
        }

        holder.kindcontent.setText(datas.get(position).getKind());




        //内容图片处理
        String path2 = datas.get(position).getBackGround();
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(context.getResources().getDrawable(R.drawable.index_img1))
                .build();



        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();

        holder.mainimg.setController(controller2);
        holder.mainimg.setHierarchy(hierarchy2);




        holder.title.setText(datas.get(position).getTitle());

        //读取第一章内容

        holder.maincontent.setText(datas.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        if (datas!=null)
            return datas.size();
        else
            return 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
}

class MyViewHolder2 extends RecyclerView.ViewHolder {
    SimpleDraweeView headimg;
    TextView name;
    TextView time;
    TextView maincontent;
    ImageView kindimg;
    TextView title;
    TextView kindcontent;
    SimpleDraweeView mainimg;
    public MyViewHolder2(View itemView) {
        super(itemView);
        headimg= (SimpleDraweeView) itemView.findViewById(R.id.index_head);
        name = (TextView) itemView.findViewById(R.id.index_username);
        time = (TextView) itemView.findViewById(R.id.index_time);
        kindimg = (ImageView) itemView.findViewById(R.id.index_list_kind_img);
        kindcontent = (TextView) itemView.findViewById(R.id.index_list_kind);
        mainimg = (SimpleDraweeView) itemView.findViewById(R.id.index_img);
        title = (TextView) itemView.findViewById(R.id.list_title);
        maincontent = (TextView) itemView.findViewById(R.id.list_maincontent);
    }
}
