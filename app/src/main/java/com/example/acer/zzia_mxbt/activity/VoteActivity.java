package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.recycleview_adapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.vote_content;
import com.example.acer.zzia_mxbt.utils.SpacesItemDecoration;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {
//jjjkkkkkk
    RecyclerView recycler;
    recycleview_adapter adapter;
    List<vote_content> list;
    String path;
    int Uid;
    String flag;
    static int Cid;
    public Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Uid= (int) msg.obj;
                 //   Log.e("ccc", "handleMessage: "+"aaaaaaaaaa啊啊"+Uid );
                    initdata();
                    break;
                case 2:
                    //代表执行加一操作标志位

                    flag= (String) msg.obj;
                    Log.e("BB",flag);
                    initdata();
                    break;
                case 3:
                    //代表执行减一操作标志位

                    flag= (String) msg.obj;
                    Log.e("CC",flag);
                    initdata();
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(VoteActivity.this);
        setContentView(R.layout.activity_vote);
        initview();
        initIntent();
        initdata();

    }

    private void initIntent() {
        Intent intent=getIntent();
        Cid= intent.getIntExtra("Chapter_Id",0);
        Log.e("Cid", "initdata: "+Cid );
    }


    private void initdata() {
        list = new ArrayList<>();
        MyApplication application=new MyApplication();
        path=application.getVote_url();

        RequestParams params = new RequestParams(path);
        params.addQueryStringParameter("Uid",Uid+"");
        params.addQueryStringParameter("Cid",""+Cid);
        params.addQueryStringParameter("flag", flag);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<vote_content>>(){}.getType();
                list = gson.fromJson(result,type);
                Log.e("teag",""+list.get(0).getAWid());
                recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new recycleview_adapter(list, VoteActivity.this);
                recycler.setAdapter(adapter);

                adapter.notifyDataSetChanged();
               adapter.setOnItemClickListener(new recycleview_adapter.OnRecyclerViewItemClickListener() {
                   @Override
                   public void onItemClick(View view, int position) {
                       Intent intent=new Intent(VoteActivity.this,Write_ReadActivity.class);
                      // Log.e("AWid",""+list.get(position).getAWid()+"aaaaa"+position);
                       intent.putExtra("AWid",list.get(position).getAWid());
                       startActivity(intent);
                       //Toast.makeText(VoteActivity.this,"AA"+position,Toast.LENGTH_LONG).show();
                   }
               });
                SpacesItemDecoration decoration = new SpacesItemDecoration(20);
                recycler.addItemDecoration(decoration);



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                onCreate(null);
            }
        });


    }



    private void initview() {
        recycler = (RecyclerView) findViewById(R.id.recycleview);
    }
}
