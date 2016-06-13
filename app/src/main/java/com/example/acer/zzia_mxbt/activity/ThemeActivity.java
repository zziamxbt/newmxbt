package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.ThemeAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.SubjectBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class ThemeActivity extends AppCompatActivity {
    //1,找数据
    List<SubjectBean> mList;
    //2，找每一行视图
    //3,确定适配器
    ThemeAdapter mThemeAdapter = null;
    private ListView mListView = null;
    private String mShowSubjectPath;//访问显示专题封面的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_theme);
        initViews();
        initDatas();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.theme_listView);
    }

    private void initDatas() {
        //从数据库读取专题相关信息
        MyApplication myApplication = (MyApplication) getApplication();
        mShowSubjectPath = myApplication.getShowSubjectUrl();

        mList = new ArrayList<>();
        RequestParams params = new RequestParams(mShowSubjectPath);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问后台成功
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubjectBean>>() {
                }.getType();
                mList = gson.fromJson(result, type);
                mThemeAdapter = new ThemeAdapter(ThemeActivity.this, mList);
                mListView.setAdapter(mThemeAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        int sid = mList.get(position).getSid();//专题id
                        String imagePath = mList.get(position).getPath();//图片地址
                        String content = mList.get(position).getScontent();//专题简介
                        String textPath = mList.get(position).getStext();//专题内容

                        //将路径转化为URI
                        Uri imageUri = Uri.parse(imagePath);
                        Uri textUri = Uri.parse(textPath);
                       // Log.e("qiyu,传递sid", sid + "");
                        //Log.e("qiyu,传递image", imageUri + "");
                        //Log.e("qiyu,传递txt", textUri + "");
                        //Log.e("qiyu,传递content", content);

                        //传递参数
                        Intent intent = new Intent(ThemeActivity.this, ThemeContentActivity.class);
                        intent.putExtra("sid", sid);
                        intent.putExtra("imageUri", imageUri.toString());
                        intent.putExtra("textUri", textUri.toString());
                        intent.putExtra("content", content);
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