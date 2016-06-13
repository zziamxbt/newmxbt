package com.example.acer.zzia_mxbt.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.RankActivity;
import com.example.acer.zzia_mxbt.adapters.MyFramentAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.rank_author;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by 刘俊杰 on 2016/5/16.
 */
public class contentFrament extends Fragment {
    public static final String error="error";
    ListView listView;
    List<rank_author> list;
    MyFramentAdapter adapter;
    String path;
    View view;
   static String data;

    public Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.author_fragment, null);
        initview();

        initdata();
         handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:

                        data= (String) msg.obj;
                        initdata();
                        break;
                    case 2:

                        data= (String) msg.obj;
                        initdata();
                        break;
                    case 3:

                        data= (String) msg.obj;
                        initdata();
                        break;
                }
            }
        };
        return view;
    }

    private void initdata() {

//---------------------------------------------------------------
        MyApplication application=new MyApplication();
        path=application.getAuthor_url();
        RequestParams params = new RequestParams(path);
        params.addQueryStringParameter("drop",data);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<rank_author>>(){}.getType();
               list = gson.fromJson(result,type);
               Log.e("hehe",list.toString());
                adapter = new MyFramentAdapter(list,getActivity());
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
//                Log.e(error,"错误原因：" + ex.getMessage() );
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
        listView = (ListView) view.findViewById(R.id.listview);

    }

}
