package com.example.acer.zzia_mxbt.activity;

import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.utils.GetToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AndWriteContent_Activity extends AppCompatActivity {
    public static final String TAG = "mytest";
    TextView andwrite_title,andwrite_next;
    EditText andwrite_contenttitle,andwrite_content;
    ImageView andwrite_back;
    static int flag=0;
    int Uid=2;//等待接收数据
    int Cid=14;//等待接收数据
    String selected;
    String createdate;
    String title;
    String content;
    String mpath;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_write_content_);
        initview();
        initdata();
//        inithttp();
    }

    private void inithttp() {
        MyApplication application=new MyApplication();
        path=application.getAndwrite_url();
        RequestParams params = new RequestParams(path);
        params.addQueryStringParameter("Uid",Uid+"");
        params.addQueryStringParameter("Cid",Cid+"");
        params.addQueryStringParameter("path",mpath);
        params.addQueryStringParameter("title",title);
        params.addQueryStringParameter("createtime",createdate);
        params.addQueryStringParameter("isfinish",selected);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("AAAA","插入成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("log",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initdata() {
        andwrite_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                PopupWindow popupWindow;
                View view;
                view = layoutInflater.inflate(R.layout.andwrite_item, null);
                TextView  preview, publish;
                final Button contiue,finish;
                preview = (TextView) view.findViewById(R.id.andwrite_preview);
                publish = (TextView) view.findViewById(R.id.andwrite_publish);
                contiue = (Button) view.findViewById(R.id.andwrite_contiue);
                finish = (Button) view.findViewById(R.id.andwrite_finish);
                popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0*1101000000));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(andwrite_next, Gravity.BOTTOM,0,0);
                popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
                //子控件的单击事件
                //连载中单击事件
                contiue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        contiue.setBackgroundResource(R.drawable.anniu);
                        finish.setBackgroundResource(R.drawable.anniu2);
                        flag=1;
                    }
                });
                //已完结单击事件
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        contiue.setBackgroundResource(R.drawable.author_anniu);
                        finish.setBackgroundResource(R.drawable.novel_anniu2);
                        flag=2;
                    }
                });
                //预览单击事件,预览会把文章信息先取出，进行显示，暂不存入数据库
                preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String title= andwrite_contenttitle.getText().toString();
                        String content=andwrite_content.getText().toString();
                        Date date=new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                       String createtime=sdf.format(date);
                        //然后跳转到内容界面
                    }
                });



                //发布单击事件，将文章信息存入到数据库
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         title= andwrite_contenttitle.getText().toString();
                        content=andwrite_content.getText().toString();
                        Log.e("tag",title+":"+content);
                        Date date=new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                         createdate=sdf.format(date);
                        Log.e("tag",createdate+"");

                        if(flag==1) {
                             selected = "连载中";
                        }else if(flag==2){
                             selected = "已完结";
                        }
                        //需要把content传上七牛云返回路径，把title和selected插入数据库
                        Log.e("tag",selected+"");
                        String andwritename=System.currentTimeMillis()+".txt";
                        mpath="http://o7f8zz7p8.bkt.clouddn.com/"+andwritename;
                        byte [] data=content.getBytes();
                        GetToken mGetToken;
                        final String token;
                        String urlPath="http://o758frrmu.bkt.clouddn.com";
                        String name="zzia-mxbt";//zzia_mxbt

                        if(data!=null){
                            mGetToken = new GetToken();
                            token = mGetToken.getToken(name);
                            UploadManager uploadManager = new UploadManager();
                            uploadManager.put(data,andwritename,token,
                                    new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject res) {

                                            if (info.statusCode == 200) {
                                                Log.e(TAG, "" + info.isOK());
                                                Toast.makeText(getApplication(), "完成上传", Toast.LENGTH_LONG).show();
                                            } else {

                                                Toast.makeText(getApplication(), info.statusCode + "上传失败", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, null);
                        }

                        inithttp();

                    }
                });

            }
        });




    }



    private void initview() {
        andwrite_title= (TextView) findViewById(R.id.andwrite_title);
        andwrite_next= (TextView) findViewById(R.id.andwrite_next);
        andwrite_contenttitle= (EditText) findViewById(R.id.andwrite_contenttitle);
        andwrite_content= (EditText) findViewById(R.id.andwrite_content);
        andwrite_back= (ImageView) findViewById(R.id.andwrite_back);
    }
}
