package com.example.acer.zzia_mxbt.activity;


import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.fragment.InfomationFragment;
import com.example.acer.zzia_mxbt.fragment.MessageFragment;
import io.rong.imkit.RongIM;

public class MessageActivity extends AppCompatActivity {
    MessageFragment messageFragment;
    InfomationFragment infomationFragment;
    TextView conversation_info,conversation_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initview();
        initdefault();
    }

    private void initdefault() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        messageFragment=new MessageFragment();
        transaction.add(R.id.conversation_layout, messageFragment);
        transaction.commit();
    }

    private void initview() {
        conversation_info= (TextView) findViewById(R.id.conversation_info);
        conversation_message= (TextView) findViewById(R.id.conversation_message);
        conversation_message.setBackgroundResource(R.drawable.conversation_button);
    }

    public void backevent(View view) {
        finish();
    }
//消息单击事件
    public void messageevent(View view) {
        conversation_info.setBackgroundResource(R.color.white);
        conversation_info.setTextColor(Color.BLACK);
        conversation_message.setBackgroundResource(R.drawable.conversation_button);
        conversation_message.setTextColor(Color.WHITE);
        //跳转到消息界面
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        messageFragment=new MessageFragment();
        transaction.replace(R.id.conversation_layout, messageFragment);
        transaction.commit();

    }
//私信单击事件
    public void infoevent(View view) {
        //按钮变色
        conversation_info.setBackgroundResource(R.drawable.conversation_button);
        conversation_info.setTextColor(Color.WHITE);
        conversation_message.setTextColor(Color.BLACK);
        conversation_message.setBackgroundResource(R.color.white);
        //先跳转到私信fargment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        infomationFragment=new InfomationFragment();
        transaction.replace(R.id.conversation_layout, infomationFragment);
        transaction.commit();

    }
}
