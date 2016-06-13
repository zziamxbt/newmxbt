package com.example.acer.zzia_mxbt.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.DownlineAdapter;
import com.example.acer.zzia_mxbt.bean.downlineBean;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DownlineActivity extends AppCompatActivity {
ListView listView;
    List<downlineBean> list;
    DownlineAdapter adapter;
    SQLiteDatabase db;
    int Aid;
    int Uid;
    ByteArrayInputStream Uhead;
    ByteArrayInputStream Acoverimg;
    String Unickname;
    String Akind;
    String Atitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downline);
        initview();
       initdatabase();
        initlistner();
    }




    private void initview() {
        listView= (ListView) findViewById(R.id.downline_listview);
    }

    private void initdatabase() {
        Intent intent=getIntent();
       Uid= intent.getIntExtra("Uid",0);
        list=new ArrayList<>();
        downlineBean bean=null;

        db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        Cursor cursor= db.rawQuery("select Aid,Uhead,Unickname,Akind,Acoverimg,Atitle from article where Uid="+Uid,null);
        while(cursor.moveToNext()){
            bean=new downlineBean();
            bean.setAid(cursor.getInt(cursor.getColumnIndex("Aid")));
            Uhead = new ByteArrayInputStream(  cursor.getBlob(cursor.getColumnIndex("Uhead")));
            bean.setUhead(Uhead);
            Acoverimg = new ByteArrayInputStream(  cursor.getBlob(cursor.getColumnIndex("Acoverimg")));
            bean.setAcoverimg(Acoverimg);
            Unickname=cursor.getString(cursor.getColumnIndex("Unickname"));
            bean.setUnickname(Unickname);
            Akind=cursor.getString(cursor.getColumnIndex("Akind"));
            bean.setAkind(Akind);
            Atitle=cursor.getString(cursor.getColumnIndex("Atitle"));
            bean.setAtitle(Atitle);

            list.add(bean);
        }

        adapter=new DownlineAdapter(list,DownlineActivity.this);
        listView.setAdapter(adapter);
        db.close();
    }
    private void initlistner() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Aid=list.get(position).getAid();
                    Intent intent=new Intent(DownlineActivity.this,DownlineContentActivity.class);
                intent.putExtra("Aid",Aid);
                startActivity(intent);
            }
        });
    }
}
