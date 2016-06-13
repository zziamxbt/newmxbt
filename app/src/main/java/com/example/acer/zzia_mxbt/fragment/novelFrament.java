package com.example.acer.zzia_mxbt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.adapters.novel_fragment_Adapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.rank_novel;
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
public class novelFrament extends Fragment {
    ListView listView;
    List<rank_novel> list;
    novel_fragment_Adapter novel_adapter;
    View view;
    String path;
static String data;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    // Toast.makeText(getActivity(), Flagtest.getData(), Toast.LENGTH_SHORT).show();
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.novel_fragment, null);
        initview();
        initdata();
        initlistner();
        return view;
    }

    private void initlistner() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), Article_ReadActivity.class);
                intent.putExtra("Article_Id",list.get(position).getAid());
                startActivity(intent);
            }
        });
    }

    private void initdata() {

        MyApplication application=new MyApplication();
        path=application.getNovel_url();
        RequestParams params = new RequestParams(path);
        params.addQueryStringParameter("drop1",data);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<rank_novel>>(){}.getType();
                list = gson.fromJson(result,type);
                Log.e("haha",list.toString());
                novel_adapter=new novel_fragment_Adapter(list,getActivity());
                listView.setAdapter(novel_adapter);
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
        listView = (ListView) view.findViewById(R.id.listview);
    }
}
