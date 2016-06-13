package com.example.acer.zzia_mxbt.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.MyAdapter;
import com.example.acer.zzia_mxbt.fragment.contentFrament;
import com.example.acer.zzia_mxbt.fragment.novelFrament;
import com.facebook.drawee.backends.pipeline.Fresco;


import java.util.ArrayList;
import java.util.List;



public class RankActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    Button author, novel;
    ImageView dropdown, back;
    List<Fragment> list;
    MyAdapter myAdapter;
    contentFrament authorFragment;
    novelFrament novelFrament;
    FragmentManager mFragmentManager;
    boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(RankActivity.this);
        setContentView(R.layout.activity_rank);
        initview();
        initdata();
        initlistner();
    }


    private void initlistner() {
        author.setOnClickListener(this);
        novel.setOnClickListener(this);
        dropdown.setOnClickListener(this);
        back.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    author.setBackgroundResource(R.drawable.anniu);
                    novel.setBackgroundResource(R.drawable.anniu2);
                    flag=false;
                }
                if (position == 1) {
                    author.setBackgroundResource(R.drawable.author_anniu);
                    novel.setBackgroundResource(R.drawable.novel_anniu2);
                    flag=true;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initdata() {

        list = new ArrayList<>();
        authorFragment = new contentFrament();
        novelFrament = new novelFrament();

        list.add(authorFragment);
        list.add(novelFrament);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        mFragmentManager = getSupportFragmentManager();
        myAdapter = new MyAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(myAdapter);

    }

    private void initview() {

        author = (Button) findViewById(R.id.author);
        novel = (Button) findViewById(R.id.xiaoshuo);
        dropdown = (ImageView) findViewById(R.id.dropdown);
        back = (ImageView) findViewById(R.id.back);
        // frameLayout= (FrameLayout) findViewById(R.id.id_content);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.author:

                author.setBackgroundResource(R.drawable.anniu);
                novel.setBackgroundResource(R.drawable.anniu2);

                viewPager.setCurrentItem(0);
                flag = false;
                break;
            case R.id.xiaoshuo:


                author.setBackgroundResource(R.drawable.author_anniu);
                novel.setBackgroundResource(R.drawable.novel_anniu2);

                viewPager.setCurrentItem(1);
                flag = true;
                break;
            case R.id.back:
                finish();
                break;
            case R.id.dropdown:
                LayoutInflater layoutInflater = getLayoutInflater();
                PopupWindow popupWindow;
                View view;
                view = layoutInflater.inflate(R.layout.drop_down, null);
                TextView drop1, drop2, drop3;
                drop1 = (TextView) view.findViewById(R.id.drop1);
                drop2 = (TextView) view.findViewById(R.id.drop2);
                drop3 = (TextView) view.findViewById(R.id.drop3);
                if (flag == false) {
                    drop1.setText("关注度");
                    drop2.setText("好评度");
                    drop3.setText("续写之最");
                } else {
                    drop1.setText("上周最多");
                    drop2.setText("历史最多");
                    drop3.setText("续写者之最");
                }

                popupWindow = new PopupWindow(view, 250, 400, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0 * 1101000000));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(dropdown, -230, 20);
                popupWindow.setAnimationStyle(R.style.anim_menu_animation);
                //-------------------------------------下拉项的单击事件
                drop1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag==false){
                            //代表作家中的关注度选项
//                            Toast.makeText(MainActivity.this,"关注度被电击",Toast.LENGTH_LONG).show();
//                            Flagtest.setData("focus");
                            viewPager.setCurrentItem(0);
                            Message msg = new Message();
                            msg.what =1;
                            msg.obj="focus";
                            authorFragment.handler.sendMessage(msg);

                        }else{
                            //代表小说中的本周最多项
                            // Toast.makeText(MainActivity.this,"本周最多被电击",Toast.LENGTH_LONG).show();

                            viewPager.setCurrentItem(1);
                            Message msg2 = new Message();
                            msg2.what =1;
                            msg2.obj="lastweekbest";
                            if(!novelFrament.handler.hasMessages(1))
                                novelFrament.handler.sendMessage(msg2);
                        }



                    }
                });
                drop2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag==false){


                            viewPager.setCurrentItem(0);
                            Message msg = new Message();
                            msg.obj="good";
                            msg.what =2;
                            if(!authorFragment.handler.hasMessages(2)){
                                authorFragment.handler.sendMessage(msg);
                            }


                        }else{


                            viewPager.setCurrentItem(1);
                            Message msg2 = new Message();
                            msg2.obj="historybest";
                            msg2.what =2;
                            if(!novelFrament.handler.hasMessages(2)){
                                novelFrament.handler.sendMessage(msg2);
                            }

                        }



                    }
                });
                drop3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag==false){


                            viewPager.setCurrentItem(0);
                            Message msg = new Message();
                            msg.what =3;
                            msg.obj="writebest";
                            if(!authorFragment.handler.hasMessages(3)){
                                authorFragment.handler.sendMessage(msg);
                            }


                        }else{


                            viewPager.setCurrentItem(1);
                            Message msg2 = new Message();
                            msg2.what =3;
                            msg2.obj="writerbest";
                            if(!novelFrament.handler.hasMessages(3)){
                                novelFrament.handler.sendMessage(msg2);
                            }

                        }
                    }
                });
        }

    }


}

