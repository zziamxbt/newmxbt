package com.example.acer.zzia_mxbt.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.activity.MainActivity;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
<<<<<<< HEAD


import com.example.acer.zzia_mxbt.adapters.IndexListAdapter1;

=======
<<<<<<< HEAD

import com.example.acer.zzia_mxbt.adapters.IndexListAdapter1;
import com.example.acer.zzia_mxbt.bean.IndexBean;

import com.example.acer.zzia_mxbt.R;
=======
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import com.example.acer.zzia_mxbt.bean.IndexBean;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.User;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
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
<<<<<<< HEAD
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
=======
    import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44

/**
 * Created by Wang on 2016/5/7.
 */
public class ViewFragment extends Fragment {
    //存放首页文章信息的list

    List<IndexBean> list;
    List<IndexBean> savelist;
    PullToRefreshListView listView;
<<<<<<< HEAD
=======


<<<<<<< HEAD
    IndexListAdapter1 ila1;
=======







>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44


>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
    IndexListAdapter ila;
    SimpleDraweeView headimg ;
    Boolean isend=false;
    static  int begin = 0;
    static  int end = 9;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.index_pager_provider, container, false);
        savelist = new ArrayList<IndexBean>();

        list = new ArrayList<IndexBean>();
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
//                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                int Aid = list.get(position - 2).getArticleId();
             //   Log.e("Aid", "onItemClick: "+Aid );
                Intent intent = new Intent(getActivity(), Article_ReadActivity.class);
<<<<<<< HEAD
                intent.putExtra("Article_Id", Aid);
=======

                intent.putExtra("Article_Id", Aid);

>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
                startActivity(intent);
            }
        });

    }

    private void initList() {

<<<<<<< HEAD
        RequestParams params = new RequestParams("http://10.201.1.115:8080/ZZIA_MXBT/index_servlet");
=======

<<<<<<< HEAD
        RequestParams params = new RequestParams("http://10.201.1.166:8080/ZZIA_MXBT/index_servlet");

//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
=======


>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
       RequestParams paramsSend=new RequestParams("http://10.201.1.115:8080/ZZIA_MXBT/sendMessage");


//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
        x.http().get(paramsSend, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<Set<String>>() {
                }.getType();
                Set<String> set=new HashSet<String>();
                set = gson.fromJson(result, type);
                if(set.size()==0){
                    set.add("");
                }
                JPushInterface.setTags(getActivity(), set, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });
                Log.e("set", "set: "+set );
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
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();
                list = gson.fromJson(result, type);

                new loadDataAsyncTask((MainActivity) getActivity()).execute(list);
                listView.setMode(PullToRefreshBase.Mode.BOTH);


                initRefreshListView();


<<<<<<< HEAD
=======



>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
                View v = View.inflate(getActivity(), R.layout.index_header_text, null);
                listView.getRefreshableView().addHeaderView(v, null, false);
                listView.getRefreshableView().setHeaderDividersEnabled(false);

                for (int i = begin; i <= end; i++) {
                    savelist.add(list.get(i));
                }

<<<<<<< HEAD
=======




<<<<<<< HEAD
                ila = new IndexListAdapter(getActivity(),  savelist);
                listView.setAdapter(ila);


=======

>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
                ila = new IndexListAdapter(getActivity(),  savelist);
                listView.setAdapter(ila);

>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
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
                        new loadDataAsyncTask((MainActivity) getActivity()).execute(list);
                        if (!isend) {
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

    class loadDataAsyncTask extends AsyncTask<List<IndexBean>, Integer, String> {
        private static final int HIDDEN_CODE = 1;
        private static final int APPEAR_CODE = 2;
        private MainActivity activity;

        public loadDataAsyncTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(List<IndexBean>... params) {
            //用一个线程来模拟刷新
            List<IndexBean> list;
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
                Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();
                isend = true;
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
                ila.notifyDataSetChanged();
                //刷新完成
                listView.onRefreshComplete();
            }
        }


        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Log.e("load", "onProgressUpdate: " + values[0]);
            if (values[0] != 0) {
                activity.handler.sendEmptyMessage(APPEAR_CODE);
            } else {
                activity.handler.sendEmptyMessage(HIDDEN_CODE);
            }

        }
    }


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

    public void initBeginAndEnd() {
        begin = 0;
        end = 9;
    }

    public void initView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.index_cotent1);

    }
}
