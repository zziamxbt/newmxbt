package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
<<<<<<< HEAD
=======
import android.content.DialogInterface;
<<<<<<< HEAD
import android.net.Uri;
=======
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import android.graphics.Picture;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
=======
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
<<<<<<< HEAD
=======
import android.widget.EditText;
import android.widget.ImageView;
<<<<<<< HEAD
=======
import android.widget.RemoteViews;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import com.example.acer.zzia_mxbt.bean.JavaBean_article;
=======
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.ArticleBean;
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import com.example.acer.zzia_mxbt.bean.JavaBean_article;
import com.example.acer.zzia_mxbt.bean.WalletBean;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import com.example.acer.zzia_mxbt.utils.SetPicture;
import com.facebook.drawee.view.SimpleDraweeView;
<<<<<<< HEAD
=======
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44

<<<<<<< HEAD
import java.lang.reflect.Type;
=======
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
<<<<<<< HEAD
=======
import java.lang.reflect.Type;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import java.util.List;



/**
 * Created by Gemptc on 2016/5/14.
 */
public class MyAdapter_article extends BaseAdapter {
<<<<<<< HEAD
=======
    //存储金币集合
    List<WalletBean> mList;
    //获取路径
    private String mPath="http://10.201.1.115:8080/ZZIA_MXBT/wallet_servlet";
    private boolean goldFlag=true;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
    //文章內容默認大小
    public static final int size = 13;
    private int myPosition;
    private Context mContext;
    private List<JavaBean_article> mArticleList;
    private LayoutInflater mInflater;
<<<<<<< HEAD
=======
    private EditText mEditText;
    private TextView mTextView;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
    ViewHolder viewHolder = new ViewHolder();

    static class ViewHolder {
        private TextView ArticleContent;
        private TextView ArticleTime;
        private TextView ChapterTitle;
        private TextView ChapterAuthorName;
        private SimpleDraweeView mauthor_portraits;
<<<<<<< HEAD
=======
        private ImageView mShangCi;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
    }

    public MyAdapter_article(Context mContext, List<JavaBean_article> mArticleList, int myPosition) {
        this.mContext = mContext;
        this.mArticleList = mArticleList;
        this.myPosition = myPosition;
        mInflater = LayoutInflater.from(mContext);
<<<<<<< HEAD
=======

        mTextView=new TextView(mContext);
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
    }


    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(
                    R.layout.listview_article, null);
<<<<<<< HEAD

=======
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
            viewHolder.ArticleContent = (TextView) convertView.findViewById(
                    R.id.ArticleContent);
            viewHolder.ArticleTime = (TextView) convertView.findViewById(
                    R.id.ArticleTime);
            viewHolder.ChapterTitle = (TextView) convertView.findViewById(
                    R.id.ChapterTitle);
            viewHolder.ChapterAuthorName = (TextView) convertView.findViewById(
                    R.id.ChapterAuthorName);
            viewHolder.mauthor_portraits = (SimpleDraweeView) convertView.findViewById(R.id.ChapterAuthor);
<<<<<<< HEAD
=======
            viewHolder.mShangCi= (ImageView) convertView.findViewById(R.id.ShangCi);

>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44

        viewHolder.ArticleContent.setText(mArticleList.get(position).getArticleContent());
        viewHolder.ArticleTime.setText(mArticleList.get(position).getArticleTime());
        viewHolder.ChapterTitle.setText(mArticleList.get(position).getChapterTitle());
        viewHolder.ChapterAuthorName.setText(mArticleList.get(position).getChapterAuthorName());

        viewHolder.mauthor_portraits.setController(SetPicture.controller(Uri.parse(mArticleList.get(position).getmAuthor_portraits())));
        viewHolder.mauthor_portraits.setHierarchy(SetPicture.hierarchy(mArticleList.get(position).getmAuthor_portraits(),mContext));


        viewHolder.ArticleContent.setTextSize(size + myPosition);
        viewHolder.ChapterTitle.setTextSize(size + myPosition);

        viewHolder.mauthor_portraits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Toast.makeText(mContext, "你点击的本章节续写者的头像,第"+(position+1)+"章节续写者", Toast.LENGTH_LONG).show();
=======

                Toast.makeText(mContext, "你点击本章节的用户id:"+mArticleList.get(position).getUserId()+",此章节id："+mArticleList.get(position).getAuthorId(),Toast.LENGTH_LONG).show();
            }
        });
        getTest(mArticleList.get(position).getUserId(),mArticleList.get(position).getAuthorId(),0,true);//获取该用户的金币数

        viewHolder.mShangCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText=new EditText(mContext);
                final AlertDialog.Builder builder= new AlertDialog.Builder(mContext);//MainActivity.this为当前环境

                builder.setIcon(R.drawable.jinbi);//提示图标

                builder.setTitle("打赏");//提示框标题
               builder.setMessage("你的金币:"+mTextView.getText());
               // Log.e("data", "data：" + mTextView.getText());
            //   builder.setMessage("你的金币:"+10+"个"+"\n");//提示内容
               mEditText.setHint("你要打赏 "+mArticleList.get(position).getChapterAuthorName()+" 金币");
               mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

               builder.setView(mEditText);
//**
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //给作者打赏金币
                        getTest(mArticleList.get(position).getUserId(),mArticleList.get(position).getAuthorId(),Integer.valueOf(mEditText.getText().toString()),false);
                    }
                });//确定按钮
                builder.setNegativeButton("取消",null);
                builder.create().show();
                Toast.makeText(mContext, "你点击本章节的用户id:"+mArticleList.get(position).getUserId()+",此章节id："+mArticleList.get(position).getAuthorId(),Toast.LENGTH_LONG).show();
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
            }
        });

        return convertView;
    }

<<<<<<< HEAD

    //取出文章内容
=======
     //访问后台数据
    public void getTest(int Uid,int Authorid,int goldNum,boolean goldFlag){
        RequestParams params = new RequestParams(mPath);
        if(goldFlag){
            params.addQueryStringParameter("User_id", Uid + "");
            params.addQueryStringParameter("gold","false");
        }else {
            params.addQueryStringParameter("User_id", Uid + "");
            params.addQueryStringParameter("Authorid", Authorid+ "");
            params.addQueryStringParameter("gold","false");
            params.addQueryStringParameter("goldNum",goldNum+"");
        }
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<WalletBean>>() {
                }.getType();
                mList= gson.fromJson(result, type);
                mTextView.setText(mList.get(0).getWbalance()+"");
                Log.e("data", "data：" + mTextView.getText());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("data", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44


}
