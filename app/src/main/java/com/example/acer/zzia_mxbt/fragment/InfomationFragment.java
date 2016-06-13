package com.example.acer.zzia_mxbt.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.user_info;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by 刘俊杰 on 2016/6/11.
 */
public class InfomationFragment extends Fragment {
    SharedPreferences mSharedPreferencesUser;//偏好设置,保存后台传来的用户信息
    String Uid, Uhead, Uname, Unickname, Utoken;
    public static final String SAVEUSER = "save_user";//偏好设置，保存用户所有信息
    List<user_info> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);

        mSharedPreferencesUser = getActivity().getSharedPreferences(SAVEUSER, Context.MODE_PRIVATE);
        Uid = mSharedPreferencesUser.getString("Uid", "");
        Uhead = mSharedPreferencesUser.getString("Uhead", "");
        Uname = mSharedPreferencesUser.getString("Uname", "");
        Unickname = mSharedPreferencesUser.getString("Unickname", "");
        Utoken = mSharedPreferencesUser.getString("Utoken", "");
        userinfo();
        connect(Utoken);
        initdata();
        return view;
    }

    private void initdata() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

    private void connect(String token) {
        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("token错误", "请检查APP-KEY");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("连接成功", "--onSuccess" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("连接失败", "--onError" + errorCode);
            }
        });
    }

    private void userinfo() {
        list = new ArrayList<>();
        list.add(new user_info(Uname, Unickname, Uhead));

    }

}
