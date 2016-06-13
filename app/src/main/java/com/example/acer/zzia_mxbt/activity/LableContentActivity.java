package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.ThemeContentAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.SubjectArticleBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LableContentActivity extends AppCompatActivity {
    List<SubjectArticleBean> mList;
    ThemeContentAdapter mThemeContentAdapter=null;
    private PullToRefreshListView mListView=null;
    private String mShowLableArticlePath;
    private ImageView mReturnImg;
    private TextView mLableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_lable_content);

        initViews();

        initDatas();

        initListeners();

        listViewListeners();

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        initRefreshListView();
    }

    private void initViews() {
        mListView= (PullToRefreshListView) findViewById(R.id.lable_content_listview);
        mReturnImg= (ImageView) findViewById(R.id.lable_content_returnback);
        mLableTextView= (TextView) findViewById(R.id.lable_content_text);
    }

    private void initDatas() {
        MyApplication myApplication= (MyApplication) getApplication();
        mShowLableArticlePath=myApplication.getShowLableArticleUrl();

        //获取Lable页面传递的标签id，content
        Intent intent=getIntent();
        int Lid=intent.getIntExtra("Lid",0);
        String Lcontent=intent.getStringExtra("Lcontent");
       // Log.e("qiyu接收Lid", Lid + "");
       // Log.e("qiyu接收Lcontent", Lcontent);
        mLableTextView.setText(Lcontent);


        mList=new ArrayList<>();

        RequestParams params=new RequestParams(mShowLableArticlePath);
        params.addParameter("Lid",Lid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type = new TypeToken<List<SubjectArticleBean>>() {
                }.getType();
                mList = gson.fromJson(result, type);
              //  Log.e("qiyu接收后台", mList.size()+"");
                //将后台取出的文章章节地址解析读取后再传递到适配器。
                loadListTxtAsyncTask myTask = new loadListTxtAsyncTask();
                myTask.execute(mList);

                //listview绑定适配器
                mThemeContentAdapter = new ThemeContentAdapter(LableContentActivity.this, mList);
                mListView.setAdapter(mThemeContentAdapter);

                mThemeContentAdapter.notifyDataSetChanged();

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

    //耗时操作，异步加载取出文章集合的第一章

    /**
     * 参数一：表示需要处理的数据类型
     * 参数二：。。。
     * 参数三：doInBackground（）方法的返回类型
     */
    static class loadListTxtAsyncTask extends AsyncTask<List<SubjectArticleBean>, Void, String> {
        List<SubjectArticleBean> mList = null;

        @Override
        protected String doInBackground(List<SubjectArticleBean>... params) {
            mList = params[0];
            for (int i = 0; i < mList.size(); i++) {
                String txtHttp = mList.get(i).getContent();
                StringBuilder content = null;
                URL url = null;
                try {
                    url = new URL(txtHttp);
                    InputStream is = url.openStream();
                    BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    content = new StringBuilder();
                    String valueString;
                    while ((valueString = buff.readLine()) != null) {
                        content.append(valueString);
                    }
                    //只将数据部分显示
                    if (content.toString().length() >= 30) {
                        mList.get(i).setContent(content.toString().substring(0, 30) + "...");
                    } else {
                        mList.get(i).setContent(content.toString());
                    }
                    buff.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "success";//返回的结果作为onPostExecute(String str)的参数处理
        }

        @Override
        protected void onPostExecute(String str) {
            if (str.equals("success")) {
               /* //通知数据集改变，刷新页面
                activity.mThemeContentAdapter.notifyDataSetChanged();
                //刷新完成
                activity.mListView.onRefreshComplete();*/
            }
        }
    }

    private void initListeners() {

    }



    //listview刷新初始化
    private void initRefreshListView() {
        ILoadingLayout startLables = mListView.getLoadingLayoutProxy(true, false);
        startLables.setPullLabel("下拉刷新");
        startLables.setRefreshingLabel("正在拉");
        startLables.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    private void listViewListeners() {
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载耗时，异步操作
                new loadListViewAsyncTask(LableContentActivity.this).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                new loadListViewAsyncTask(LableContentActivity.this).execute();
            }
        });

        //ListView的item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有1个头部（刷新），所以减1
                int aid = mList.get(position - 1).getArticleId();
                Intent intent = new Intent(LableContentActivity.this, Article_ReadActivity.class);
                intent.putExtra("Article_Id", aid);
                Log.e("qiyu,LableContent,", "传递文章id: "+ aid);
                startActivity(intent);
                /*Intent intent=new Intent(LableContentActivity.this,QiyuActivity.class);
                startActivity(intent);*/
            }
        });
    }

    //内部类：实现数据加载的耗时操作
    static class loadListViewAsyncTask extends AsyncTask<Void, Void, String> {
        private LableContentActivity activity;

        public loadListViewAsyncTask(LableContentActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        }

        //对返回值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                //通知数据集改变，刷新页面
                activity.mThemeContentAdapter.notifyDataSetChanged();
                //刷新完成
                activity.mListView.onRefreshComplete();
            }
        }
    }
}
