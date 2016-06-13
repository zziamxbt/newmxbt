package com.example.acer.zzia_mxbt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.ArticleCommentAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.ArticleCommentBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AndWriteCommentActivity extends AppCompatActivity {
    TextView article_comment_title, article_comment_publish;
    EditText article_comment_edit;
    ImageView article_comment_back;
    ListView listView;
    List<ArticleCommentBean> list;
    ArticleCommentAdapter adapter;
    String path;
    int AWid = 1;//数据待接收
    int Uid = 1;//数据待接受，表示当前用户
    int replyed;
    String createtime;
    String content;
    String flag="false";
    int flagid=0;//用于判断是否取消回复
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(AndWriteCommentActivity.this);
        setContentView(R.layout.activity_article_comment);
        initview();
        initlistner();
        inithttp();

    }





    private void inithttp() {
        list = new ArrayList<>();
        MyApplication application = new MyApplication();
        path = application.getAndwrite_comment_url();
        RequestParams params = new RequestParams(path);
        params.addQueryStringParameter("AWid", AWid + "");
        params.addQueryStringParameter("Uid", Uid + "");
        params.addQueryStringParameter("replyed",replyed+"");
        params.addQueryStringParameter("flag",flag);
        params.addQueryStringParameter("createtime", createtime);
        params.addQueryStringParameter("content", content);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ArticleCommentBean>>() {
                }.getType();
                list = gson.fromJson(result, type);
                Log.e("GGG",list.get(0).getContent());
                adapter=new ArticleCommentAdapter(list,AndWriteCommentActivity.this);
                listView.setAdapter(adapter);
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

    private void initview() {
        article_comment_title = (TextView) findViewById(R.id.article_comment_title);
        article_comment_publish = (TextView) findViewById(R.id.article_comment_publish);
        article_comment_edit = (EditText) findViewById(R.id.article_comment_edit);
        article_comment_back = (ImageView) findViewById(R.id.article_comment_back);
        listView= (ListView) findViewById(R.id.article_comment_listview);

    }
    private void initlistner() {
        //发送按钮
        article_comment_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content = article_comment_edit.getText().toString();
                article_comment_edit.setText(null);
                Date date = new Date();
                Log.e("HHH", date+"");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                createtime = sdf.format(date);
                Log.e("HHH", createtime);
                Log.e("HHH", content);
                inithttp();
                flag="false";
                content="";
                createtime="";
            }
        });
        //后退按钮
        article_comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(ArticleCommentActivity.this,"当前用户对"+list.get(position).getUid()+"进行了回复",Toast.LENGTH_SHORT).show();
                if(flagid==list.get(position).getUid()){
                    article_comment_edit.setHint("请输入评论");
                    flag="false";
                    flagid=0;
                }else {
                    article_comment_edit.setHint("回复:'"+list.get(position).getNickname()+"'");
                    flag="true";
                    replyed=list.get(position).getUid();
                    flagid=list.get(position).getUid();
                }

                 initlistner();
            }
        });

    }
}
