package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.example.acer.zzia_mxbt.bean.User;
import com.example.acer.zzia_mxbt.utils.GetToken;
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
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    //修改昵称结果码
    private static final int NICKNAME_CODE = 1000;
    //修改城市结果码
    private static final int COUNTRY_CODE = 1001;
    //修改签名结果码
    private static final int SIGN_CODE = 1002;
    //修改头像结果码
    private static final int RESULT_LOAD_IMAGE = 1003;
    //裁剪头像结果码
    private static final int IMAGE_CUT_CODE = 1004;
    private static final int EDIT_BG_CODE = 1005;
    private static final int BG_CUT_CODE = 1006;
    private static final int USER_REQUEST_COD = 1;
    private static final int EDIT_CODE = 3001;
    //各个控件
    SimpleDraweeView edit_bg;
    ImageView edit_back;
    TextView edit_save;
    RelativeLayout edit_head;
    SimpleDraweeView edit_head_img;
    RelativeLayout edit_nickname;
    TextView edit_nickname_name;
    RelativeLayout edit_sex;
    ImageView edit_sex_icon;
    TextView edit_sex_sex;
    RelativeLayout edit_country;
    TextView edit_country_country;
    RelativeLayout edit_sign;
    TextView edit_sign_sign;
    //性别选择弹窗
    private PopupWindow popupWindow;
    //性别选择控件
    RadioButton boyRadio ;
    RadioButton girlRadio;
    TextView  makeSure;
    RadioGroup sexGroup ;
    //用户对象
    User user;
    //存储头像图片的位图
    Bitmap headSaveMap;
    //存储头像到七牛云上用的byte数组
    byte[] headSaveByte;

    //存储背景的图片位图
    Bitmap bgSaveMap;
    //存储背景的byte数组
    byte[] bgSaveByte;
    //裁剪图片的宽高
    private static int output_X = 600;
    private static int output_Y = 600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getPopupWindow();
        findView();
        UserGetter();
        InitData(user);
        setView();
    }

    private void setView() {
        //修改昵称
        edit_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(EditActivity.this, "修改昵称", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this , EditNickname.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("user", user);
                bundle.putBoolean("isLogin",true);
                intent.putExtras(bundle);
                startActivityForResult(intent, NICKNAME_CODE);
            }
        });


        edit_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this , EditCountry.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("user", user);
                bundle.putBoolean("isLogin",true);
                intent.putExtras(bundle);
                startActivityForResult(intent, COUNTRY_CODE);
            }
        });


        edit_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this , EditSign.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("user", user);
                bundle.putBoolean("isLogin",true);
                intent.putExtras(bundle);
                startActivityForResult(intent, SIGN_CODE);
            }
        });

        edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                setResult(EDIT_CODE, intent);
                finish();

            }
        });



        edit_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void InitData(final User user) {

        //初始化背景
        String path1 = user.getUbk();
        Uri uri1 = Uri.parse(path1);
        GenericDraweeHierarchyBuilder builder1 = new GenericDraweeHierarchyBuilder(EditActivity.this.getResources());
        GenericDraweeHierarchy hierarchy1 = builder1
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(EditActivity.this.getResources().getDrawable(R.drawable.index_img1))

                .build();
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri1)
                .build();
        edit_bg.setController(controller1);
        edit_bg.setHierarchy(hierarchy1);

        //初始化头像
        String path2 = user.getUhead();
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(EditActivity.this.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(EditActivity.this.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();
        edit_head_img.setController(controller2);
        edit_head_img.setHierarchy(hierarchy2);


        //初始化昵称
        edit_nickname_name.setText(user.getUnickname());

        //初始化性别
        edit_sex_sex.setText(user.getUsex());
        if(user.getUsex().equals("男")){
            edit_sex_icon.setImageResource(R.drawable.sex_boy);
        }else{
            edit_sex_icon.setImageResource(R.drawable.sex_girl);
        }


        //初始化城市
        edit_country_country.setText(user.getUcountry());

        //初始化个性签名
        edit_sign_sign.setText(user.getUsign());


        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                int id = sexGroup.getCheckedRadioButtonId();
                if(id==R.id.edit_sex_boy_selector){
                    user.setUsex("男");
                    edit_sex_sex.setText(user.getUsex());
                    edit_sex_icon.setImageResource(R.drawable.sex_boy);
                }else if(id==R.id.edit_sex_girl_selector){
                    user.setUsex("女");
                    edit_sex_sex.setText(user.getUsex());
                    edit_sex_icon.setImageResource(R.drawable.sex_girl);
                }
                popupWindow.dismiss();

            }
        });


        edit_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        edit_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, EDIT_BG_CODE);
            }
        });
        
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForServer();
            }
        });
    }

    private void askForServer() {
        Gson gson = new Gson();
        String result  = gson.toJson(user);
        RequestParams params = new RequestParams(MyApplication.getUrl_Edit());
        params.addQueryStringParameter("user",result);

//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

    private void UserGetter() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private void findView() {
        edit_bg = (SimpleDraweeView) findViewById(R.id.edit_bg);
        edit_back = (ImageView) findViewById(R.id.edit_back);
        edit_save = (TextView) findViewById(R.id.edit_save);
        edit_head = (RelativeLayout) findViewById(R.id.edit_head);
        edit_head_img = (SimpleDraweeView) findViewById(R.id.edit_head_img);
        edit_nickname = (RelativeLayout) findViewById(R.id.edit_nickname);
        edit_nickname_name = (TextView) findViewById(R.id.edit_nickname_name);
        edit_sex = (RelativeLayout) findViewById(R.id.edit_sex);
        edit_sex_icon = (ImageView) findViewById(R.id.edit_sex_icon);
        edit_sex_sex = (TextView) findViewById(R.id.edit_sex_sex);
        edit_country = (RelativeLayout) findViewById(R.id.edit_country );
        edit_country_country = (TextView) findViewById(R.id.edit_country_country);
        edit_sign = (RelativeLayout) findViewById(R.id.edit_sign);
        edit_sign_sign = (TextView) findViewById(R.id.edit_sign_sign);
        boyRadio = (RadioButton) popupWindow.getContentView().findViewById(R.id.edit_sex_boy_selector);
        girlRadio = (RadioButton) popupWindow.getContentView().findViewById(R.id.edit_sex_girl_selector);
        sexGroup = (RadioGroup) popupWindow.getContentView().findViewById(R.id.radioGroup);
//        makeSure = (TextView) View.inflate(EditActivity.this ,R.layout.sex_editter,null).findViewById(R.id.makesure);
        makeSure= (TextView) popupWindow.getContentView().findViewById(R.id.makesure);
    }

//各个intent的result 返回结果处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COUNTRY_CODE && resultCode == COUNTRY_CODE)
        {
            user = (User) data.getSerializableExtra("user");
            edit_country_country.setText(user.getUcountry());
        } if(requestCode == NICKNAME_CODE && resultCode == NICKNAME_CODE){
        user = (User) data.getSerializableExtra("user");
        edit_nickname_name.setText(user.getUnickname());
    } if(requestCode == SIGN_CODE && resultCode == SIGN_CODE){
        user = (User) data.getSerializableExtra("user");
        edit_sign_sign.setText(user.getUsign());
    } if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
        cropRawPhoto(data.getData());
    }if(requestCode == IMAGE_CUT_CODE&& null != data){
        Log.e("wangtianyu", "onActivityResult: "+"wangtianyu" );
        saveImg(data);//设置图片框

    }if (requestCode == EDIT_BG_CODE && resultCode == RESULT_OK && null != data){
        cropBG(data.getData());
    }if (requestCode==BG_CUT_CODE&& null != data){
        saveBg(data);
    }

    }

    private void saveBg(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bgSaveMap = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bgSaveMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bgSaveByte = baos.toByteArray();
            GetToken mGetToken;
            final String token;
            String urlPath="http://o758frrmu.bkt.clouddn.com";
            String name="zzia-mxbt";//zzia_mxbt
            final String bgImageName= System.currentTimeMillis()+".jpg";
            final String bgImagePath ="http://o7f8zz7p8.bkt.clouddn.com/"+bgImageName;
            if(bgSaveByte!=null){
                mGetToken = new GetToken();
                token = mGetToken.getToken(name);
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(bgSaveByte,bgImageName,token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {

                                if (info.statusCode == 200) {
                                    Log.e("222", "" + info.isOK());
                                    Toast.makeText(getApplication(), "完成上传", Toast.LENGTH_LONG).show();
                                    user.setUbk(bgImagePath);
                                    String path1 = user.getUbk();
                                    Uri uri1 = Uri.parse(path1);
                                    DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                                            .setUri(uri1)
                                            .build();
                                    edit_bg.setController(controller1);
                                } else {

                                    Toast.makeText(getApplication(), info.statusCode + "上传失败", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, null);

            }



        }
    }

    //裁剪背景图的方法
    private void cropBG(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
//把裁剪的数据填入里面
// 设置裁剪
        intent.putExtra("crop", "true");
// aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data",true);
        startActivityForResult(intent, BG_CUT_CODE);
    }

    //裁剪图片的方法
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//把裁剪的数据填入里面
// 设置裁剪
        intent.putExtra("crop", "true");
// aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent, IMAGE_CUT_CODE);
    }


//提取截取的图片保存到位图
private void saveImg(Intent intent) {

    Bundle extras = intent.getExtras();
    if (extras != null) {
        headSaveMap = extras.getParcelable("data");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        headSaveMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        headSaveByte = baos.toByteArray();
        GetToken mGetToken;
        final String token;
        String urlPath="http://o758frrmu.bkt.clouddn.com";
        String name="zzia-mxbt";//zzia_mxbt
        final String headImageName= System.currentTimeMillis()+".jpg";
        final String headImagePath ="http://o7f8zz7p8.bkt.clouddn.com/"+headImageName;
        if(headSaveByte!=null){
            mGetToken = new GetToken();
            token = mGetToken.getToken(name);
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(headSaveByte,headImageName,token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {

                            if (info.statusCode == 200) {
                                Log.e("222", "" + info.isOK());
                                Toast.makeText(getApplication(), "完成上传", Toast.LENGTH_LONG).show();
                                user.setUhead(headImagePath);
                                String path2 = user.getUhead();
                                Uri uri2 = Uri.parse(path2);
                                DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                                        .setUri(uri2)
                                        .build();
                                edit_head_img.setController(controller2);
                            } else {

                                Toast.makeText(getApplication(), info.statusCode + "上传失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, null);

         }



    }
}



//性别选择弹窗
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.sex_editter, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, RelativeLayout.LayoutParams.MATCH_PARENT,  400, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.CardView);
        // 点击其他地方消失
//        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//                return false;
//            }
//        });
    }
    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }
}
