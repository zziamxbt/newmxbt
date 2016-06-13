package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.SearchLableAdapter;
import com.example.acer.zzia_mxbt.adapters.SearchThemeAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.LableBean;
import com.example.acer.zzia_mxbt.bean.SubjectBean;
import com.example.acer.zzia_mxbt.view.HorizontalListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    /**
     * 主题版块
     */
    private List<View> mThemeViewList;//主题数据来源
    private ViewPager mThemeViewPager;//主题viewPager
    private LinearLayout mThemeDotLayout;//主题圆点布局
    private Button mThemeDotSelectedBt;//圆点是否被选中
    private TextView mThemeMore;//主题的更多
    private View view1, view2, view3, view4;//viewPager对应的view
    private SimpleDraweeView mThemeViewImg1, mThemeViewImg2, mThemeViewImg3, mThemeViewImg4;//view对应的image
    private String mSearchPath;//显示主题的路径
    private List<SubjectBean> mSubjectList;//访问后台，返回的数据集合

    /**
     * 标签版块
     */
    private List<LableBean> mLableList;//保存后台数据
    private HorizontalListView mLableHorizontalListView = null;//横向listview
    private SearchLableAdapter mSearchLableAdapter = null;
    private TextView mLableMore;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_search);
        initViews();//初始化布局
        initDatas();//初始化数据
        initListeners();//监听事件
        SearchThemeAdapter pagerAdapter = new SearchThemeAdapter(mThemeViewList);
        mThemeViewPager.setAdapter(pagerAdapter);
        //主题上圆点的设置
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dot_normal);
        for (int i = 0; i < mThemeViewList.size(); i++) {
            Button bt = new Button(this);
            bt.setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
            bt.setBackgroundResource(R.drawable.dot_normal);
            mThemeDotLayout.addView(bt);
            pagerAdapter.notifyDataSetChanged();
        }
    }


    private void initViews() {
        /**
         * 专题
         */
        mThemeViewPager = (ViewPager) findViewById(R.id.search_theme_viewPage);
        mThemeDotLayout = (LinearLayout) findViewById(R.id.search_theme_dotLayout);
        mThemeMore = (TextView) findViewById(R.id.search_theme_more);

        view1 = View.inflate(this, R.layout.search_theme_view1, null);
        view2 = View.inflate(this, R.layout.search_theme_view2, null);
        view3 = View.inflate(this, R.layout.search_theme_view3, null);
        view4 = View.inflate(this, R.layout.search_theme_view4, null);

        mThemeViewImg1 = (SimpleDraweeView) view1.findViewById(R.id.search_theme_img1);
        mThemeViewImg2 = (SimpleDraweeView) view2.findViewById(R.id.search_theme_img2);
        mThemeViewImg3 = (SimpleDraweeView) view3.findViewById(R.id.search_theme_img3);
        mThemeViewImg4 = (SimpleDraweeView) view4.findViewById(R.id.search_theme_img4);


        /**
         * 标签
         */

        mLableHorizontalListView = (HorizontalListView) findViewById(R.id.search_lable_horizontallListView);
        mLableMore = (TextView) findViewById(R.id.search_lable_more);
    }

    private void initDatas() {
        //从数据读取部分专题信息（专题封面，专题id）
        MyApplication myApplication = (MyApplication) getApplication();
        mSearchPath = myApplication.getSearchThemeUrl();

        /**
         * 获取专题数据
         */
        mSubjectList = new ArrayList<>();//保存后台Bean数据的集合
        RequestParams params = new RequestParams(mSearchPath);
        params.addParameter("search", "searchTheme");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubjectBean>>() {
                }.getType();
                mSubjectList = gson.fromJson(result, type);

                //获取图片
                SimpleDraweeView[] viewImage = {mThemeViewImg1, mThemeViewImg2, mThemeViewImg3, mThemeViewImg4};
                View[] viewlist = {view1, view2, view3, view4};
                for (int i = 0; i < mSubjectList.size(); i++) {
                    final int j = i;
                    Uri uri = Uri.parse(mSubjectList.get(i).getPath());//将字符串形式转化为uri
                    GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(SearchActivity.this.getResources());
                    GenericDraweeHierarchy hierarchy = builder
                            .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                            .setPlaceholderImage(SearchActivity.this.getResources().getDrawable(R.drawable.jiazai))
                            .build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .build();
                    viewImage[i].setController(controller);
                    viewImage[i].setHierarchy(hierarchy);

                    //设置每一个view的单击事件
                    viewlist[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int sid = mSubjectList.get(j).getSid();//专题id
                            String imagePath = mSubjectList.get(j).getPath();//图片地址
                            String content = mSubjectList.get(j).getScontent();//专题简介
                            String textPath = mSubjectList.get(j).getStext();//专题内容

                            //将路径转化为URI
                            Uri imageUri = Uri.parse(imagePath);
                            Uri textUri = Uri.parse(textPath);

                            //传递参数
                            Intent intent = new Intent(SearchActivity.this, ThemeContentActivity.class);
                            intent.putExtra("sid", sid);
                            intent.putExtra("imageUri", imageUri.toString());
                            intent.putExtra("textUri", textUri.toString());
                            intent.putExtra("content", content);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("qiyu,theme error", "onError: " + ex.getMessage().toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        //将添加图片数据的集合交由viewPager的适配器处理
        mThemeViewList = new ArrayList<>();
        mThemeViewList.add(view1);
        mThemeViewList.add(view2);
        mThemeViewList.add(view3);
        mThemeViewList.add(view4);


        /**
         * 获取标签数据
         */
        mLableList = new ArrayList<>();//保存后台Bean数据的集合
        RequestParams params2 = new RequestParams(mSearchPath);
        params2.addParameter("search", "searchLable");
        x.http().get(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<LableBean>>() {
                }.getType();
                mLableList = gson.fromJson(result, type);
                //Log.e("qiyu,lable,", "onSuccess: " + mLableList.size());
                mSearchLableAdapter = new SearchLableAdapter(SearchActivity.this, mLableList);
                mLableHorizontalListView.setAdapter(mSearchLableAdapter);

                mLableHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int Lid = mLableList.get(position).getLid();
                        String Lcontent=mLableList.get(position).getLcontent();
                        Intent intent = new Intent(SearchActivity.this, LableContentActivity.class);
                        intent.putExtra("Lid", Lid);
                        intent.putExtra("Lcontent",Lcontent);
                        //Log.e("qiyu,lable,", "传递需要获取的文章id: " + Lid+"");
                        //Log.e("qiyu,lable,", "传递需要获取的文章内容: "+ Lcontent);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("qiyu,lable,", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void initListeners() {
        //viewPager的监听事件
        mThemeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(SearchActivity.this, "选择了第" + (position + 1) + "个页面", Toast.LENGTH_SHORT).show();
                if (mThemeDotSelectedBt != null) {
                    //圆点的设置
                    mThemeDotSelectedBt.setBackgroundResource(R.drawable.dot_normal);
                }
                Button currentBt = (Button) mThemeDotLayout.getChildAt(position);
                currentBt.setBackgroundResource(R.drawable.dot_selected);
                mThemeDotSelectedBt = currentBt;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mThemeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ThemeActivity.class);
                startActivity(intent);
            }
        });


        mLableMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, LableActivity.class);
                startActivity(intent);
            }
        });
    }
}
