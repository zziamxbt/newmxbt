package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.CenterViewPagerAdapter;
import com.example.acer.zzia_mxbt.bean.User;
import com.example.acer.zzia_mxbt.fragment.CenterFragment;
import com.example.acer.zzia_mxbt.fragment.CenterFragment1;
import com.example.acer.zzia_mxbt.fragment.CenterFragment2;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CenterActivity extends AppCompatActivity {
    private static final int BACK_IMAGE_CODE = 1;
    Toolbar mToolbar;
    CollapsingToolbarLayout ctbl ;
    SimpleDraweeView simpleDraweeView;
    SimpleDraweeView head_simpleDraweeView;
    TextView textView ;
    ViewPager center_viewPager ;
    CenterViewPagerAdapter centerViewPagerAdapter;

    public static User getUser() {
        return user;
    }

    private static  User user;
    Boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CenterActivity.this);
        setContentView(R.layout.activity_center);
        intentGetter();
        iniView();
        setView();
        intiPager();

    }

    private void intentGetter() {
        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("isLogin", false);
        user = (User) intent.getSerializableExtra("user");
    }

    private void intiPager() {
        center_viewPager = (ViewPager) findViewById(R.id.center_pager);
        centerViewPagerAdapter = new CenterViewPagerAdapter(getSupportFragmentManager());
        centerViewPagerAdapter.addFragment(CenterFragment.newInstance(), "我的故事");//添加Fragment
        centerViewPagerAdapter.addFragment(CenterFragment1.newInstance(), "我的收藏");
        centerViewPagerAdapter.addFragment(CenterFragment2.newInstance(), "我的推荐");
        center_viewPager.setAdapter(centerViewPagerAdapter);//设置适配器

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("我的故事"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("我的收藏"));
        mTabLayout.addTab(mTabLayout.newTab().setText("我的推荐"));
        mTabLayout.setupWithViewPager(center_viewPager);
    }

    private void setView() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        String path2 = user.getUbk();
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(CenterActivity.this.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(CenterActivity.this.getResources().getDrawable(R.drawable.index_img1))
                .build();
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();
        simpleDraweeView.setController(controller2);
        simpleDraweeView.setHierarchy(hierarchy2);



        String path1 = user.getUhead();
        Uri uri1 = Uri.parse(path1);
        GenericDraweeHierarchyBuilder builder1 = new GenericDraweeHierarchyBuilder(CenterActivity.this.getResources());
        GenericDraweeHierarchy hierarchy1 = builder1
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(CenterActivity.this.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))

                .build();
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri1)
                .build();
        head_simpleDraweeView.setController(controller1);
        head_simpleDraweeView.setHierarchy(hierarchy1);
        final Handler hanler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case BACK_IMAGE_CODE:
                        Palette.from((Bitmap)msg.obj).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(final Palette palette) {

                                int bgColor = palette.getLightVibrantColor(getResources().getColor(R.color.white));
                                int titleColor = palette.getMutedColor(getResources().getColor(R.color.white));
                                int ExpandedTitleColor = palette.getLightVibrantColor(getResources().getColor(R.color.white));
                                if(ExpandedTitleColor==getResources().getColor(R.color.white))
                                    ExpandedTitleColor = palette.getDarkMutedColor(getResources().getColor(R.color.white));
                                ctbl.setContentScrimColor(bgColor);
                                ctbl.setCollapsedTitleTextColor(titleColor);
                                ctbl.setExpandedTitleColor(ExpandedTitleColor);
                                textView.setTextColor(titleColor);

                            }
                        });

                }
            }
        };


            new Thread(){
                @Override
                public void run() {
                    super.run();
                    URL url = null;
                    try {
                        url = new URL(user.getUbk());
                        InputStream is = url.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        if(bitmap!=null){
//                            Log.e("bitmap", "run: "+"NOTNULL" );
//                        }
                        Message msg = new Message();
                        msg.obj = bitmap;
                        msg.what = BACK_IMAGE_CODE;
                        hanler.sendMessage(msg);
                        is.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            ctbl.setTitle(user.getUnickname());

    }

    private void iniView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ctbl= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.center_background);
       textView = (TextView) findViewById(R.id.center_edit);
        head_simpleDraweeView = (SimpleDraweeView) findViewById(R.id.center_head);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
