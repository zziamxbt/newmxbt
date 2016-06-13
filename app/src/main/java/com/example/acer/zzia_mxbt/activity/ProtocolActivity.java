package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.acer.zzia_mxbt.R;

public class ProtocolActivity extends AppCompatActivity {
    private Button mProtocolSure;//协议确定按钮
    private ImageView mProtocolBack;//返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        mProtocolSure = (Button) findViewById(R.id.protocol_sure);
        initViews();
        initListeners();
    }

    //初始化控件
    private void initViews() {
        mProtocolSure = (Button) findViewById(R.id.protocol_sure);
        mProtocolBack = (ImageView) findViewById(R.id.protocol_returnback);
    }

    //控件监听事件
    private void initListeners() {
        mProtocolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustWhichActivity();
            }
        });

        mProtocolSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustWhichActivity();
            }
        });
    }

    //判断是哪个activity跳转过来的
    private void adjustWhichActivity() {
        Intent intent = getIntent();
        if (intent.getStringExtra("flag").equals("login")) {
            //登录页面
            Intent loginIntent = new Intent(ProtocolActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else if (intent.getStringExtra("flag").equals("regist")) {
            //注册页面
            Intent registIntent = new Intent(ProtocolActivity.this, RegistActivity.class);
            startActivity(registIntent);
        }
    }
}
