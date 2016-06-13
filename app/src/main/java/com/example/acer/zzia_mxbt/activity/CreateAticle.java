package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.example.acer.zzia_mxbt.bean.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

public class CreateAticle extends AppCompatActivity {

    private static final int COVER_CODE = 4001;
    private static final int COVER_CREATE_CODE = 105;
    private static final int CONTENT_CREATE_CODE = 4002;
    private static final int CONTENT_CODE = 106;
    User user;
    SimpleDraweeView create_cover_head ;
    SimpleDraweeView cover_img;
    TextView create_cover_nickname;
    CardView cover_card;
    CardView content_card;
    TextView cover_title;
    ImageView kind_ring;
    TextView kind_text;
    TextView content_content;
    TextView create_send;
    //存储传回数据
    String path1;
    String title;
    String kind;
    String content;
    String path2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aticle);
        UserGetter();
        findView();
        initdata();
        setView();


    }

    private void setView() {
        cover_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateAticle.this, "点击了封面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateAticle.this , CoverCreateActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user",user);
//                intent.putExtras(bundle);
                startActivityForResult(intent, COVER_CODE);
            }
        });

        content_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title==null){
                    Toast.makeText(CreateAticle.this, "请先设置封面", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreateAticle.this, "点击了开头", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAticle.this , ContentCreateActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user",user);
//                intent.putExtras(bundle);
                    startActivityForResult(intent, CONTENT_CREATE_CODE);
                }
            }
        });

        create_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (path1==null||title==null||kind==null||content==null){
                    Toast.makeText(CreateAticle.this, "请将内容填写完整", Toast.LENGTH_SHORT).show();
                }else{
                    askForData();
                    finish();
                }
            }
        });
    }

    private void askForData() {
        RequestParams params = new RequestParams(MyApplication.getArticle_create());
        params.addQueryStringParameter("img",path1);
        params.addQueryStringParameter("title",title);
        params.addQueryStringParameter("kind",kind);
        params.addQueryStringParameter("content",path2);
        params.addQueryStringParameter("uid",user.getUid()+"");
        Toast.makeText(CreateAticle.this, ""+user.getUid(), Toast.LENGTH_SHORT).show();
//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {


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

    //初始化数据
    private void initdata() {
        //初始化头像
        String path2 = user.getUhead();
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(CreateAticle.this.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(CreateAticle.this.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();
        create_cover_head.setController(controller2);
        create_cover_head.setHierarchy(hierarchy2);


        //初始化昵称
        create_cover_nickname.setText(user.getUnickname());
    }

    private void findView() {
        create_cover_head = (SimpleDraweeView) findViewById(R.id.create_cover_head);
        create_cover_nickname = (TextView) findViewById(R.id.create_cover_nickname);
        cover_card = (CardView) findViewById(R.id.create_cover_cardview);
        content_card = (CardView) findViewById(R.id.create_content_cardview);
        cover_img = (SimpleDraweeView) findViewById(R.id.create_cover_img);
        cover_title = (TextView) findViewById(R.id.create_cover_title);
        kind_ring = (ImageView) findViewById(R.id.create_cover_kind_ring);
        kind_text = (TextView) findViewById(R.id.create_cover_kind_text);
        content_content = (TextView) findViewById(R.id.create_content_content);
        create_send= (TextView) findViewById(R.id.create_send);
    }

    private void UserGetter() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("test", "onCreate: "+path1+title+kind+content );
        //获取头像
        if (requestCode == COVER_CODE && resultCode == COVER_CREATE_CODE && data!=null){
            path1 =data.getStringExtra("coverPath");
            if(path1!=null) {
                Uri uri1 = Uri.parse(path1);
                GenericDraweeHierarchyBuilder builder1 = new GenericDraweeHierarchyBuilder(CreateAticle.this.getResources());
                GenericDraweeHierarchy hierarchy1 = builder1
                        .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .setPlaceholderImage(CreateAticle.this.getResources().getDrawable(R.drawable.index_img1))

                        .build();
                DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                        .setUri(uri1)
                        .build();

                cover_img.setHierarchy(hierarchy1);
                cover_img.setController(controller1);
            }
            title = data.getStringExtra("title");
            if (title!=null){
                cover_title.setText(title);
            }

            kind = data.getStringExtra("kind");
            if (kind!=null){
                switch (kind){
                    case "真事":
                        kind_ring.setImageResource(R.drawable.ring_green);
                        kind_text.setText("真事");
                        break;
                    case "创作":
                        kind_ring.setImageResource(R.drawable.ring_yellow);
                        kind_text.setText("创作");
                        break;

                    case "灵异":
                        kind_ring.setImageResource(R.drawable.ring_blue);
                        kind_text.setText("灵异");
                        break;

                    case "生活":
                        kind_ring.setImageResource(R.drawable.ring_red);
                        kind_text.setText("生活");
                        break;

                }
            }

        }

        if(requestCode == CONTENT_CREATE_CODE && resultCode == CONTENT_CODE && data!=null){
            content = data.getStringExtra("content");
            path2 =data.getStringExtra("path");
            if(content!=null){
                if (content.length()>=200) {
                    content_content.setText(content.substring(0, 200));
                }else{
                    content_content.setText(content);
                }
            }
        }

    }
}
