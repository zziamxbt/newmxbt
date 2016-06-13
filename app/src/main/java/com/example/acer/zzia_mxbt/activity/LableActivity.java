package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.LableAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.LableBean;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LableActivity extends AppCompatActivity {
    List<LableBean> mList;//数据来源
    LableAdapter mLableAdapter=null;
    private GridView mGridView=null;//label布局控件
    private String mShowLablePath;//访问标签封面的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable);
        initViews();
        initDatas();
    }

    private void initViews() {
        mGridView= (GridView) findViewById(R.id.lable_gridview);
    }

    private void initDatas() {
        MyApplication myApplication= (MyApplication) getApplication();
        mShowLablePath=myApplication.getShowLableUrl();

        mList=new ArrayList<>();
        RequestParams params=new RequestParams(mShowLablePath);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问后台成功
                Gson gson = new Gson();
                Type type = new TypeToken<List<LableBean>>() {
                }.getType();
                mList=gson.fromJson(result,type);
                //Log.e("qiyu传递", "onSuccess: " + mList.size()+"");
                mLableAdapter=new LableAdapter(LableActivity.this,mList);
                mGridView.setAdapter(mLableAdapter);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int Lid=mList.get(position).getLid();
                        String Lcontent=mList.get(position).getLcontent();
                       // Log.e("qiyu,传递Lid", Lid + "");
                       // Log.e("qiyu,传递Lcontent", Lcontent );

                        Intent intent=new Intent(LableActivity.this,LableContentActivity.class);
                        intent.putExtra("Lid", Lid);
                        intent.putExtra("Lcontent", Lcontent);
                        startActivity(intent);

                    }
                });
        }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.e("qiyu", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
