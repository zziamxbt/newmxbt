package com.example.acer.zzia_mxbt.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.user_info;
import com.example.acer.zzia_mxbt.bean.vote_content;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserinfoActivity extends AppCompatActivity {
    String path;
    List<user_info> list;
   static List<user_info> listdata;


    static List<user_info> listdata2 = new ArrayList<>();

    SharedPreferences mSharedPreferencesUser;//偏好设置,保存后台传来的用户信息
    public static final String SAVEUSER = "save_user";//偏好设置，保存用户所有信息
    String Uid, Uhead, Uname, Unickname, Utoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        list=new ArrayList<>();
        listdata=new ArrayList<>();
        MyApplication application=new MyApplication();
         path=application.getUserinfo_url();
        RequestParams params = new RequestParams(path);
        x.http().get(params, new Callback.CommonCallback<String>(

        ) {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<user_info>>() {
                }.getType();
                list = gson.fromJson(result, type);
                for(int i=0;i<list.size();i++){
                    user_info info=new user_info();
                    info.setUname(list.get(i).getUname());
                    info.setUnickname(list.get(i).getUnickname());
                    info.setUhead(list.get(i).getUhead());
                    listdata.add(info);
                }
                mSharedPreferencesUser =getSharedPreferences(SAVEUSER, MODE_PRIVATE);
                Uhead = mSharedPreferencesUser.getString("Uhead", "");
                Uname = mSharedPreferencesUser.getString("Uname", "");
                Unickname = mSharedPreferencesUser.getString("Unickname", "");
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String s) {
                        //循环获取当前所有用户信息
                        for (int i=0;listdata.size()>=i;i++) {
                            if(listdata.size()==i){
                                return new UserInfo(Uname,Unickname,Uri.parse(Uhead));
                            }else
                            if (listdata.get(i).getUname().equals(s)) {
                                return new UserInfo(listdata.get(i).getUname(),listdata.get(i).getUnickname(), Uri.parse(listdata.get(i).getUhead()));
                            }
                        }
                        return null;
                    }
                }, true);//如果需要缓存用户信息为true，否则为false

                copy(listdata);



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    public void copy(List<user_info> listdata){
        listdata2.addAll(listdata);
    }


    public static List<user_info> getListdata2() {
        Log.e("ljj", "getListdata2: "+listdata2 );
        return listdata2;
    }

}
