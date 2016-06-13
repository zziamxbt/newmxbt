package com.example.acer.zzia_mxbt.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.utils.RoundImageview;

import java.io.ByteArrayInputStream;

public class DownlineContentActivity extends AppCompatActivity {
TextView content_title,content_kind,content_name,content_mcontent;
    ImageView content_corverimg,content_back;
    RoundImageview content_head;
    int Aid;
    SQLiteDatabase db;
    ByteArrayInputStream Uhead;
    ByteArrayInputStream Acoverimg;
    String Unickname;
    String Akind;
    String Atitle;
    String mcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downline_content);
        initview();
        initdata();
        initlistner();
    }
    private void initview() {
        content_title= (TextView) findViewById(R.id.content_title);
        content_kind= (TextView) findViewById(R.id.content_kind);
        content_name= (TextView) findViewById(R.id.content_name);
        content_mcontent= (TextView) findViewById(R.id.content_mcontent);
        content_corverimg= (ImageView) findViewById(R.id.content_coverimg);
        content_head= (RoundImageview) findViewById(R.id.content_head);
        content_back= (ImageView) findViewById(R.id.content_back);
    }
    private void initlistner() {
        content_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initdata() {
        Intent intent=getIntent();
       Aid= intent.getIntExtra("Aid",0);
        db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        Cursor cursor= db.rawQuery("select Uhead,Unickname,Akind,Acoverimg,Atitle,content from article where Aid="+Aid,null);
        while(cursor.moveToNext()){
            Uhead = new ByteArrayInputStream(  cursor.getBlob(cursor.getColumnIndex("Uhead")));
            content_head.setImageDrawable(Drawable.createFromStream(Uhead, "uimg"));
            Acoverimg = new ByteArrayInputStream(  cursor.getBlob(cursor.getColumnIndex("Acoverimg")));
            content_corverimg.setImageDrawable(Drawable.createFromStream(Acoverimg, "aimg"));
            Unickname=cursor.getString(cursor.getColumnIndex("Unickname"));
            content_name.setText(Unickname);
            Akind=cursor.getString(cursor.getColumnIndex("Akind"));
            content_kind.setText(Akind);
            Atitle=cursor.getString(cursor.getColumnIndex("Atitle"));
            content_title.setText(Atitle);
            mcontent=cursor.getString(cursor.getColumnIndex("content"));
            content_mcontent.setText(mcontent);
        }
        db.close();
    }


}
