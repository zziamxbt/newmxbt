package com.example.acer.zzia_mxbt.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.activity.MainActivity;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter1;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.example.acer.zzia_mxbt.R;
import com.facebook.drawee.view.SimpleDraweeView;
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
public class ViewFragment1 extends Fragment {
    //存放首页文章信息的list

    List<IndexBean> list;
    List<IndexBean> savelist;
    PullToRefreshListView listView;
    IndexListAdapter1 ila1;
    SimpleDraweeView headimg ;
    Boolean isend=false;
    static  int begin = 0;
    static  int end = 10;
    View view;
    MainActivity mainActivity;

    //最新最热选择
    TextView leftText;
    TextView rightText;
    String flag="newest";
    int visitFlag=0;
    Boolean left_isclicked=true;
    Boolean right_isclicked=false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.index_pager_provider, container, false);
        savelist = new ArrayList<IndexBean>();

        list = new ArrayList<IndexBean>();
        visitFlag = 0;
        left_isclicked=true;
        right_isclicked=false;
        initBeginAndEnd();
        initView();
        initList();
        setView();
        return view;
    }

    private void setView() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Aid = list.get(position-3).getArticleId();
                Log.e("ddd", "onItemClick: "+position );
                Log.e("Aid", "onItemClick: "+Aid );
                Intent intent = new Intent(getActivity(), Article_ReadActivity.class);
                intent.putExtra("Article_Id",Aid);
                startActivity(intent);
            }
        });

    }

    private void initList() {



        RequestParams params = new RequestParams("http://10.201.1.183:8080/ZZIA_MXBT/indexofzhenshi_servlet");

//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/indexofzhenshi_servlet");
        params.addQueryStringParameter("select",flag);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();
                list = gson.fromJson(result, type);
                if(list.size()<10) {
                    begin=0;
                    end=list.size()-1;
                }
                new loadDataAsyncTask((MainActivity) getActivity()).execute(list);
                listView.setMode(PullToRefreshBase.Mode.BOTH);


//               为listview 添加头信息，v是描述文本，v2是排序选择按钮
                if (visitFlag == 0) {
                    final View v = View.inflate(getActivity(),R.layout.indexofzhenshi_header_text,null);
                    final View v2 = View.inflate(getActivity(),R.layout.indexofzhenshi_selector,null);
                    leftText = (TextView) v2.findViewById(R.id.left_textView);
                    rightText = (TextView)v2.findViewById(R.id.right_textView);

                    leftText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View aview) {
                            if(left_isclicked==true){
                                return ;
                            }else if(left_isclicked==false){
                                leftText.setBackgroundResource(R.drawable.roundedconers_selected);
                                rightText.setBackgroundResource(R.drawable.roundedconers);
                                initBeginAndEnd();
                                flag = "newest";
                                left_isclicked=true;
                                right_isclicked=false;
                                leftText.setFocusableInTouchMode(false);

                                visitFlag++;
                                if (list != null)
                                    list.removeAll(list);

                                savelist.removeAll(savelist);
                                initList();
                            }
                        }
                    });
                    rightText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View aview) {
                            if(right_isclicked==true){
                                        return;
                            }else if(right_isclicked==false) {
                                rightText.setBackgroundResource(R.drawable.roundedconers_selected);
                                leftText.setBackgroundResource(R.drawable.roundedconers);
                                initBeginAndEnd();
                                flag = "hotest";
                                right_isclicked=true;
                                left_isclicked=false;
                                rightText.setFocusableInTouchMode(false);

                                visitFlag++;
                                if (list != null)
                                    list.removeAll(list);

                                savelist.removeAll(savelist);
                                initList();
                            }
                        }
                    });
                    listView.getRefreshableView().addHeaderView(v2,null,false);
                    listView.getRefreshableView().addHeaderView(v,null,false);

                    listView.getRefreshableView().setHeaderDividersEnabled(false);

                }

                initRefreshListView();
                for(int i=begin;i<=end;i++){
                        savelist.add(list.get(i));
                }

                ila1 = new IndexListAdapter1(getActivity(),  savelist);
                listView.setAdapter(ila1);



                listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                        new loadDataAsyncTask((MainActivity) getActivity()).execute(list);
                        if (!isend) {
                            for (int i = begin; i <= end; i++) {
                                savelist.add(list.get(i));
                            }
                        }
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        new loadDataAsyncTask((MainActivity)getActivity()).execute(list);
                        if(!isend) {
                            for (int i = begin; i <= end; i++) {
                                savelist.add(list.get(i));
                            }
                        }
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("a", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {




            }
        });
    }

     class loadDataAsyncTask extends AsyncTask<List<IndexBean>, Integer , String> {
         private static final int HIDDEN_CODE = 1;
         private static final int APPEAR_CODE = 2;
         private MainActivity activity;
         public loadDataAsyncTask(MainActivity activity) {
             this.activity = activity;
         }

        @Override
        protected String doInBackground(List<IndexBean>... params) {
            //用一个线程来模拟刷新
            List<IndexBean> list ;
            if (params!=null) {
                list = params[0];
                publishProgress(1);
                addData(list, begin, end);
                if (list.size() >= end + 10) {
                    begin = end + 1;
                    end = end + 10;
                } else if (list.size() >= end + 1) {
                    begin = end + 1;
                    end = list.size() - 1;
                } else {
//                    Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();
                    isend = true;
                }
            }
                publishProgress(0);
                return "success";

        }

        //对返回值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                //通知数据集改变，刷新页面
                ila1.notifyDataSetChanged();
                //刷新完成
                listView.onRefreshComplete();
            }
        }

         @Override
         protected void onProgressUpdate(Integer... values) {
             super.onProgressUpdate(values);

             Log.e("load", "onProgressUpdate: "+values[0] );
            if (values[0]!=0){
                mainActivity.handler.sendEmptyMessage(APPEAR_CODE);
            }else{
                mainActivity.handler.sendEmptyMessage(HIDDEN_CODE);
            }

         }
     }



    public   void addData(List<IndexBean> beanList, int begin, int end) {
        if(beanList!=null) {
            for (int i = begin; i <= end; i++) {
                String contentHttp=null;
                if(beanList.size()>i) {
                    contentHttp = beanList.get(i).getContent();
                }
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
                    if(beanList.size()>i) {
                        if (content.toString().length() >= 60) {
                            beanList.get(i).setContent(content.toString().substring(0, 60) + "...");
                        } else {
                            beanList.get(i).setContent(content.toString());
                        }
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

    public void initRefreshListView() {
        ILoadingLayout startLables = listView.getLoadingLayoutProxy(true, false);
        startLables.setPullLabel("下拉刷新");
        startLables.setRefreshingLabel("正在拉");
        startLables.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }
    public void initBeginAndEnd(){
        begin=0;
        end=9;
    }
    public void initView(){
        listView = (PullToRefreshListView) view.findViewById(R.id.index_cotent1);


    }
}
