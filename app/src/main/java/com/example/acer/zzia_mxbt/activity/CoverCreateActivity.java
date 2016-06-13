package com.example.acer.zzia_mxbt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.User;
import com.example.acer.zzia_mxbt.utils.GetToken;
import com.facebook.common.time.CurrentThreadTimeClock;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CoverCreateActivity extends AppCompatActivity {
    private static final int COVER_CREATE_CODE = 105;
    //各种控件
    ImageView create_back;
    TextView cover_save;
    RelativeLayout relativeLayout;
    SimpleDraweeView cover_cover;
    EditText editText;
    RelativeLayout cover_selector;
    ImageView cover_ring;
    TextView  cover_text;
    //User对象
    User user;
    private AlertDialog dialog;
    private Uri coverImgUri;
    //存储头像图片的位图
    Bitmap coverSaveMap;
    //存储头像到七牛云上用的byte数组
    byte[] coverSaveByte;

    //七牛运存储路径
    String coverPath;
    String coverName;


    //性别选择弹窗
    private PopupWindow popupWindow;
    RelativeLayout kind1 ;
    RelativeLayout kind2 ;
    RelativeLayout kind3 ;
    RelativeLayout kind4 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_create);
        getPopupWindow();
        findView();
        setView();
    }

    private void setView() {
        create_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(CoverCreateActivity.this).setItems(new String[] { "相机", "相册" }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                File appDir = new File(Environment.getExternalStorageDirectory(),
                                        "/360");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = System.currentTimeMillis() + ".jpg";
                                File outputImage = new File(appDir, fileName);
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                coverImgUri = Uri.fromFile(outputImage);
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, coverImgUri);
                                startActivityForResult(intent, 101);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 102);
                            }
                        }
                    }).create();
                }
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });


        cover_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });


        kind1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover_ring.setImageResource(R.drawable.ring_green);
                cover_text.setText("真事");
                popupWindow.dismiss();
            }

        });

        kind2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover_ring.setImageResource(R.drawable.ring_yellow);
                cover_text.setText("创作");
                popupWindow.dismiss();
            }
        });

        kind3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover_ring.setImageResource(R.drawable.ring_blue);
                cover_text.setText("灵异");
                popupWindow.dismiss();
            }
        });


        kind4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover_ring.setImageResource(R.drawable.ring_red);
                cover_text.setText("生活");
                popupWindow.dismiss();
            }
        });


        cover_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText()==null||editText.getText().toString().equals("请输入标题")){
                    Toast.makeText(CoverCreateActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(coverPath==null){
                    Toast.makeText(CoverCreateActivity.this, "请选取封面", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("coverPath",coverPath);
                    intent.putExtra("title",editText.getText().toString());
                    intent.putExtra("kind" , cover_text.getText().toString());
                    setResult(COVER_CREATE_CODE,intent);
                    finish();
                }
            }
        });
    }

    private void findView() {
        create_back = (ImageView) findViewById(R.id.create_back);
        cover_save= (TextView) findViewById(R.id.cover_save);
        relativeLayout= (RelativeLayout) findViewById(R.id.relativeLayout);
        cover_cover = (SimpleDraweeView) findViewById(R.id.cover_cover);
        editText = (EditText) findViewById(R.id.editText);
        cover_selector= (RelativeLayout) findViewById(R.id.cover_kind_selector);
        cover_ring = (ImageView) findViewById(R.id.cover_kind_ring);
        cover_text = (TextView) findViewById(R.id.cover_kind_text);
        kind1 = (RelativeLayout) popupWindow.getContentView().findViewById(R.id.kind1);
        kind2 = (RelativeLayout) popupWindow.getContentView().findViewById(R.id.kind2);
        kind3 = (RelativeLayout) popupWindow.getContentView().findViewById(R.id.kind3);
        kind4 = (RelativeLayout) popupWindow.getContentView().findViewById(R.id.kind4);
    }


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
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == 101) {
//            Bitmap bmp = BitmapFactory.decodeFile();
            cropRawPhoto(coverImgUri);
        }
        if (resultCode == RESULT_OK && requestCode == 102 && intent!= null){
            cropRawPhoto(intent.getData());
        }if (requestCode==100&& null != intent){
            saveCover(intent);
        }
    }
    //提取截取的图片保存到位图
    private void saveCover(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            coverSaveMap = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            coverSaveMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            coverSaveByte = baos.toByteArray();
            GetToken mGetToken;
            final String token;
            String urlPath="http://o758frrmu.bkt.clouddn.com";
            String name="zzia-mxbt";//zzia_mxbt
            coverName= System.currentTimeMillis()+".jpg";
            coverPath ="http://o7f8zz7p8.bkt.clouddn.com/"+coverName;
            if(coverSaveByte!=null){
                mGetToken = new GetToken();
                token = mGetToken.getToken(name);
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(coverSaveByte,coverName,token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {

                                if (info.statusCode == 200) {
                                    Log.e("222", "" + info.isOK());
                                    Toast.makeText(getApplication(), "完成上传", Toast.LENGTH_LONG).show();

                                    String path2 = coverPath;
                                    Uri uri2 = Uri.parse(path2);
                                    DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                                            .setUri(uri2)
                                            .build();
                                    cover_cover.setController(controller2);
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
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popcover, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT , true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.CardView);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();

                }
                return false;
            }
        });
    }
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

}
