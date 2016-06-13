package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;

public class SuccessActivity extends AppCompatActivity {
    private TextView mTextView;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initDatas();
        initViews();
    }

    private void initDatas() {
        Intent intent=getIntent();
        mName=intent.getStringExtra("name");
    }

    private void initViews() {
        mTextView= (TextView) findViewById(R.id.success_textview);
        mTextView.setText(mName);

    }
}
