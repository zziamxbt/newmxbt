package com.example.acer.zzia_mxbt.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.activity.CenterActivity;
import com.example.acer.zzia_mxbt.adapters.MyRecyclerViewAdapter;
import com.example.acer.zzia_mxbt.adapters.MyRecyclerViewAdapter2;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.example.acer.zzia_mxbt.utils.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by acer on 2016/5/7.
 */
public class CenterFragment2 extends Fragment {
    //存放文章信息的list
    List<IndexBean> list ;
    List<IndexBean> savelist = new ArrayList<>();
    //Oncreate中用以返回的view
    View view;
    public static final int DISTANCE = 100;
    //定义recyclerView
    RecyclerView recyclerView2 ;
    Boolean isend=false;
    static  int begin = 0;
    static  int end = 9;
    //记录屏幕上已经出现的行数
    int result;
    //记录用户按下屏幕和抬起手指时候触摸点的y轴坐标
    float startY,stopY;
    int lastVisibleItem;
    //recyclerView 适配器
    MyRecyclerViewAdapter2 MyRecyclerViewAdapter2;
    LinearLayoutManager linearLayoutManager;
    private static CenterFragment2 instance = null;
    //SwipeRefreshLayout
    SwipeRefreshLayout mSwipeRefreshWidget;
    public static CenterFragment2 newInstance() {
        if (instance == null) {
            instance = new CenterFragment2();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.centerlayout2, container, false);
//        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.center_recyclerView);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        List<Integer> datas = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            datas.add(i);
//        }
//        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
        initBeginAndEnd();
        //初始化各个控件和视图
        initView();
        //设置各个控件和视图
        setView();
        //向服务器请求数据
        askForData();
        savelist.removeAll(savelist);
        return view;
    }

    private void initBeginAndEnd() {
        begin = 0;
        end = 9;
    }

    private void askForData() {
        RequestParams params = new RequestParams(MyApplication.getMyrecommand_url());

//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
        params.addQueryStringParameter("uid",CenterActivity.getUser().getUid()+"");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("aaa", "onSuccess: " );
                Gson gson = new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();
                list = gson.fromJson(result, type);
                if(list!=null) {
                    new loadDataAsyncTask((CenterActivity) getActivity()).execute(list);
                    if(list.size()>end) {
                        for (int i = begin; i <= end; i++) {
                            savelist.add(list.get(i));
                        }
                    }else{
                        for (int i = begin ; i<= (end=list.size()-1);i++){
                            savelist.add(list.get(i));
                        }
                    }
                }
                MyRecyclerViewAdapter2 = new MyRecyclerViewAdapter2(getActivity(),savelist);
                recyclerView2.setAdapter(MyRecyclerViewAdapter2);
                MyRecyclerViewAdapter2.setOnItemClickListener(new MyRecyclerViewAdapter2.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(),Article_ReadActivity.class);
                        intent.putExtra("Article_Id",list.get(position).getArticleId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("aaa", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });


    }

    private void setView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(0);
        recyclerView2.addItemDecoration(decoration);
//        mSwipeRefreshWidget.setColorScheme(R.color.white,R.color.blue, R.color.gray,R.color.black);
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 停止刷新
                        mSwipeRefreshWidget.setRefreshing(false);
                    }
                }, 3000); // 5秒后发送消息，停止刷新

            }
        });

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));


        recyclerView2.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == MyRecyclerViewAdapter2.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    new loadDataAsyncTask((CenterActivity) getActivity()).execute(list);
                    for(int i = begin ; i<= end; i++){
                        savelist.add(list.get(i));
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem  = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initView() {
        recyclerView2 = (RecyclerView) view.findViewById(R.id.center_recyclerView2);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget2);
    }


    //AsyncTask 内部类
    class loadDataAsyncTask extends AsyncTask<List<IndexBean>, Integer,String> {

        private CenterActivity activity;
        List<IndexBean> list;
        public loadDataAsyncTask(CenterActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(List<IndexBean>... params) {
            //用一个线程来模拟刷新

            list = params[0];
//            publishProgress(1);
            if(list.size()>=end){
                addData(list, begin, end);
            }else{
                begin=0;
                end=list.size()-1;
                addData(list , begin ,end);
            }


            if (list.size() >= end + 10) {
                begin = end + 1;
                end = end + 10;
            } else if (list.size() >= end + 1) {
                begin = end + 1;
                end = list.size() - 1;
            } else {
                Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();
                isend = true;
            }
            return "success";
        }


        //对返回值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                //通知数据集改变，刷新页面
                MyRecyclerViewAdapter2.notifyDataSetChanged();
                //刷新完成;

                mSwipeRefreshWidget.setRefreshing(false);
            }

        }



    }


    //数据添加方法
    public void addData(List<IndexBean> beanList, int begin, int end) {
        for (int i = begin; i <= end; i++) {
            String contentHttp = beanList.get(i).getContent();
            StringBuilder content = null;

            URL url = null;
            try {
                url = new URL(contentHttp);
                InputStream is = url.openStream();
                BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                content = new StringBuilder();
                String valueString;

                while ((valueString = buff.readLine()) != null) {
                    content.append(valueString);
//                        Log.e("lalala", "getView: "+mlist.get(p).getNickName()+stringBuilder);
                }
                if (content.toString().length() >= 60) {
                    beanList.get(i).setContent(content.toString().substring(0, 60) + "...");
                } else {
                    beanList.get(i).setContent(content.toString());
                }
                buff.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

