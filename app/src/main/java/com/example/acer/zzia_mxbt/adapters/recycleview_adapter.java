package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.VoteActivity;
import com.example.acer.zzia_mxbt.bean.vote_content;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by 刘俊杰 on 2016/5/18.
 */
public class recycleview_adapter extends RecyclerView.Adapter<recycleview_adapter.recycleView> implements View.OnClickListener{
    List<vote_content> list;
    Context context;
    static int width;
    static int height;
    static  int data=0;
    static boolean flag=false;
   int Uid=0;
    public recycleview_adapter(List<vote_content> list, Context context) {
        this.list = list;
        this.context=context;
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        //-----------------------
        void onItemClick(View view, int position);
    }

    @Override
    public recycleView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, null);
        view.setOnClickListener(this);
        return new recycleView(view);

    }

    @Override
    public void onBindViewHolder(final recycleView holder, final int position) {
        holder.itemView.setTag(position);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
               // .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setPlaceholderImage(context.getResources().getDrawable(R.drawable.head))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();



        final Uri uri=Uri.parse(list.get(position).getVote_head());

        DraweeController controller= Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();


        holder.headimg.setHierarchy(hierarchy);
        holder.headimg.setController(controller);
        final int p =position;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
//                        Log.e("AAA","宽"+width);
//                        Log.e("AAA","高"+height);
                        int[] a = (int[]) msg.obj;
                        float rate;
                            rate = (float) a[0]/(float) a[1];

                        Uri uri1 = Uri.parse(list.get(p).getVote_coverimg());
                        holder.coverimg.setAspectRatio(rate);
                        holder.coverimg.setImageURI(uri1);

                        break;
                }
            }
        };
        new Thread(){

            public void run(){
                try {
                    URL url = new URL(list.get(p).getVote_coverimg());
                    InputStream input = url.openStream();
                    BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
                    onlyBoundsOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
                    input.close();
                    width = onlyBoundsOptions.outWidth;
                    height = onlyBoundsOptions.outHeight;
//                    Log.e("test","宽"+width);
//                    Log.e("test","高"+height);
                    Message message=new Message();
                    int[] a = new int[2];
                    a[0]=width;
                    a[1]=height;
                    message.what=1;
                    message.obj=a;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("error", "onBindViewHolder:aaa "+e.getMessage() );
                }
            }
        }.start();
//-----------------------------

        holder.vote_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!list.get(p).isFlag()){
                    //false表明是第一次点击
                    Toast.makeText(context,list.get(p).getVote_name()+"事件点击触发",Toast.LENGTH_LONG).show();
                    holder.vote_zan.setImageResource(R.drawable.gooded);
                    data=Integer.parseInt(list.get(p).getVote_num())+1;
                    holder.vote_num.setText(data+"票");
                    Log.e("DDDDDDDDD",""+data);
                    //若选中，则执行票数加一操作
                    VoteActivity as=new VoteActivity();
                    Message message=new Message();
                    Message message2=new Message();
                    message.what=1;
                    message2.what=2;
                    message.obj=list.get(p).getUid();
                    //需要告诉数据库把isvote改为true，加一
                    message2.obj="false";
                    Log.e("AAAA",list.get(p).getVote_name());
                    Log.e("AAAA",""+list.get(p).getUid());
                    as.handler2.sendMessage(message);
                    as.handler2.sendMessage(message2);
                    list.get(p).setFlag(true);
                }else if(list.get(p).isFlag()){
                    //表明是第二次点击
                    holder.vote_zan.setImageResource(R.drawable.vote_good);
                    //若选中，则执行票数减一操作
                    data=Integer.parseInt(list.get(p).getVote_num());
                    holder.vote_num.setText(data+"票");
                        Log.e("DDDDDDDDD",""+data);
                    VoteActivity as=new VoteActivity();
                    Message message=new Message();
                    Message message3=new Message();
                    message.what=1;
                    message3.what=3;
                    message.obj=list.get(p).getUid();
                    //告诉数据库用户取消了点赞，改标志位，并减一
                    message3.obj="true";
                    as.handler2.sendMessage(message);
                    as.handler2.sendMessage(message3);
                    list.get(p).setFlag(false);
                }


            }
        });

        Uid=list.get(position).getUid();
        holder.vote_name.setText(list.get(position).getVote_name());
        holder.vote_publishtime.setText(list.get(position).getVote_publishtime());
        holder.vote_title.setText(list.get(position).getVote_title());
        holder.vote_num.setText(list.get(position).getVote_num()+"票");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
}

    public static class recycleView extends  RecyclerView.ViewHolder{
        SimpleDraweeView headimg;
        TextView vote_name;
        TextView vote_publishtime;
        SimpleDraweeView coverimg;
        TextView vote_title;
        ImageView vote_zan;
        TextView vote_num;


        public recycleView(View itemView){
            super(itemView);
            headimg= (SimpleDraweeView) itemView.findViewById(R.id.vote_head);
            vote_name= (TextView) itemView.findViewById(R.id.vote_name);
            vote_publishtime= (TextView) itemView.findViewById(R.id.vote_publishtime);
           coverimg= (SimpleDraweeView) itemView.findViewById(R.id.vote_coverimg);
            vote_title= (TextView) itemView.findViewById(R.id.vote_title);
            vote_zan= (ImageView) itemView.findViewById(R.id.vote_good);
            vote_num= (TextView) itemView.findViewById(R.id.vote_num);

        }

    }

}
