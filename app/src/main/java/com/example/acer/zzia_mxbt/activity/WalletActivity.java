package com.example.acer.zzia_mxbt.activity;

<<<<<<< HEAD
import android.app.ProgressDialog;
=======
<<<<<<< HEAD
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
=======
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.application.MyApplication;
<<<<<<< HEAD
import com.example.acer.zzia_mxbt.bean.ArticleBean;
import com.example.acer.zzia_mxbt.bean.WalletBean;
import com.example.acer.zzia_mxbt.utils.InstallPlugin;
import com.example.acer.zzia_mxbt.utils.Pay;
=======
import com.example.acer.zzia_mxbt.bean.WalletBean;
import com.example.acer.zzia_mxbt.utils.InstallPlugin;
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import c.b.BP;
import c.b.PListener;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import c.b.QListener;
=======
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
import c.b.Unity;
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d

public class WalletActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    //判断是获取信息还是修改金币数
    private boolean goldFlag=true;
    //充值金币数
    private TextView mone;
    private TextView mtwo;
    private TextView mthree;
    private TextView mfour;
    private TextView mfive;
    private TextView msix;
    private TextView mseven;
    private TextView meight;
    private TextView mnine;

    //支付钱数
    private TextView mPayMoney;
    //背景转换
    private Handler handler_background;
    private Bitmap bitmap;
    //布局控件定义
    private RelativeLayout mRelativeLayout;//背景模糊
    private RelativeLayout mUser_bg;//背景
    private SimpleDraweeView UserPortraits;
    private TextView mUserName;
    private ImageView mUser_sex;
    private TextView mgoldNum;
    //获取路径
    private String mPath;
    //获取数据
    List<WalletBean> mWalletBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_wallet);
        //初始化控件
        init();
        //动态获取editText的值
        setListener();
        //获取路径
        getPath();
        //获取钱包信息
        getTest();

    }

    private void setListener() {
        mnine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           //     Toast.makeText(WalletActivity.this, "beforeTextChanged"+ s + "-" + "-" + start + "-" + after + "-" + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
          //      Toast.makeText(WalletActivity.this, "onTextChanged"+ s + "-" + "-" + start + "-" + before + "-" + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                float floatNum=0;
                String sNum=s.toString();
                if (sNum.equals("")){
                    mPayMoney.setText(goldNumber(floatNum));
                }else {
                    floatNum=Float.valueOf(sNum);
                    mPayMoney.setText(goldNumber(floatNum));
                }

                if(floatNum==10){
                    changeColor(R.id.one);
                }else if(floatNum==25){
                    changeColor(R.id.two);
                }else if(floatNum==50){
                    changeColor(R.id.three);
                }else if(floatNum==100){
                    changeColor(R.id.four);
                }else if(floatNum==500){
                    changeColor(R.id.five);
                }else if(floatNum==1000){
                    changeColor(R.id.six);
                }else if(floatNum==5000){
                    changeColor(R.id.seven);
                }else if(floatNum==10000){
                    changeColor(R.id.eight);
                }else {
                    changeColor(R.id.nine);
                }

            }
        });

    }

    private void initData() {
        Log.e("data", "data：" + mWalletBeen.get(0).getWbalance());
        ReadBackground(mWalletBeen.get(0).getUserBk());
        handler_background = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    //   drawable.setAlpha(150);
                    mRelativeLayout.setAlpha(0.6f);
                    mUser_bg.setBackgroundDrawable(drawable);
                }
            }
        };
        UserPortraits.setImageURI(Uri.parse(mWalletBeen.get(0).getUserHead()));
        mUserName.setText(mWalletBeen.get(0).getUserName());
        if (mWalletBeen.get(0).getUserSex().equals("男")) {
            mUser_sex.setImageResource(R.drawable.sex_boy);
        } else {
            mUser_sex.setImageResource(R.drawable.sex_girl);
        }

        mgoldNum.setText(mWalletBeen.get(0).getWbalance() + "");
    }


    private void init() {
        //支付初始化
        BP.init(this, "ff10725dbe4d53831b19cf1ed7232283");
        mWalletBeen = new ArrayList<>();

        mRelativeLayout = (RelativeLayout) findViewById(R.id.mohu);
        mUser_bg = (RelativeLayout) findViewById(R.id.User_bg);
        UserPortraits = (SimpleDraweeView) findViewById(R.id.UserPortraits);
        mUserName = (TextView) findViewById(R.id.UserName);
        mUser_sex = (ImageView) findViewById(R.id.User_sex);
        mgoldNum = (TextView) findViewById(R.id.goldNum);


        //支付钱初始化
        mPayMoney = (TextView) findViewById(R.id.PayMoney);
        //金币数初始化
        mone = (TextView) findViewById(R.id.one);
        mtwo = (TextView) findViewById(R.id.two);
        mthree = (TextView) findViewById(R.id.three);
        mfour = (TextView) findViewById(R.id.four);
        mfive = (TextView) findViewById(R.id.five);
        msix = (TextView) findViewById(R.id.six);
        mseven = (TextView) findViewById(R.id.seven);
        meight = (TextView) findViewById(R.id.eight);
        mnine = (TextView) findViewById(R.id.nine);

    }

    //充值监听
    public void Charge(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        if(mnine.getText().toString().equals("")){

        }else {
            myPay("金币"+mnine.getText(), "可用来打赏", Float.valueOf(mPayMoney.getText().toString()), false);
        }

        Toast.makeText(WalletActivity.this, "充值监听", Toast.LENGTH_SHORT).show();
    }

    //10金币监听
    public void one(View view) {
        changeColor(mone.getId());
        mnine.setText("10");
        mPayMoney.setText(goldNumber(10));
        Toast.makeText(WalletActivity.this, "10金币监听", Toast.LENGTH_SHORT).show();
    }

    //25金币监听
    public void two(View view) {
        changeColor(mtwo.getId());
        mnine.setText("25");
        mPayMoney.setText(goldNumber(25));
        Toast.makeText(WalletActivity.this, "25金币监听", Toast.LENGTH_SHORT).show();
    }

    //50金币监听
    public void three(View view) {
        changeColor(mthree.getId());
        mnine.setText("50");
        mPayMoney.setText(goldNumber(50));
        Toast.makeText(WalletActivity.this, "50金币监听", Toast.LENGTH_SHORT).show();
    }

    //100金币监听
    public void four(View view) {
        changeColor(mfour.getId());
        mnine.setText("100");
        mPayMoney.setText(goldNumber(100));
        Toast.makeText(WalletActivity.this, "100金币监听", Toast.LENGTH_SHORT).show();
    }

    //500金币监听
    public void five(View view) {
        changeColor(mfive.getId());
        mPayMoney.setText(goldNumber(500));
        mnine.setText("500");
        Toast.makeText(WalletActivity.this, "500金币监听", Toast.LENGTH_SHORT).show();
    }

    //1000金币监听
    public void six(View view) {
        changeColor(msix.getId());
        mnine.setText("1000");
        mPayMoney.setText(goldNumber(1000));
        Toast.makeText(WalletActivity.this, "1000金币监听", Toast.LENGTH_SHORT).show();
    }

    //5000金币监听
    public void seven(View view) {
        changeColor(mseven.getId());
        mnine.setText("5000");
        mPayMoney.setText(goldNumber(5000));
        Toast.makeText(WalletActivity.this, "5000金币监听", Toast.LENGTH_SHORT).show();
    }

    //10000金币监听
    public void eight(View view) {
        changeColor(meight.getId());
        mnine.setText("10000");
        mPayMoney.setText(goldNumber(10000));
        Toast.makeText(WalletActivity.this, "10000金币监听", Toast.LENGTH_SHORT).show();
    }


    public void changeColor(int Id) {
        //10金币
        if (R.id.one == Id) {
            mone.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mone.setBackgroundResource(R.drawable.button_radio);
        }
//25金币
        if (R.id.two == Id) {
            mtwo.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mtwo.setBackgroundResource(R.drawable.button_radio);
        }
        //50金币
        if (R.id.three == Id) {
            mthree.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mthree.setBackgroundResource(R.drawable.button_radio);
        }
        //100金币
        if (R.id.four == Id) {
            mfour.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mfour.setBackgroundResource(R.drawable.button_radio);
        }
//500金币
        if (R.id.five == Id) {
            mfive.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mfive.setBackgroundResource(R.drawable.button_radio);
        }
//1000金币
        if (R.id.six == Id) {
            msix.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            msix.setBackgroundResource(R.drawable.button_radio);
        }
//5000金币
        if (R.id.seven == Id) {
            mseven.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            mseven.setBackgroundResource(R.drawable.button_radio);
        }
//10000金币
        if (R.id.eight == Id) {
            meight.setBackgroundResource(R.drawable.select_button_radio);
        } else {
            meight.setBackgroundResource(R.drawable.button_radio);
        }

        if (R.id.nine == Id) {

        }
    }

    public String goldNumber(float money) {
        return "" + money / 10;
    }

    private void getTest() {

<<<<<<< HEAD
      /*  Intent intent=getIntent();
        int User_id=intent.getIntExtra("Uid",0);
        Log.e("abc", "getTest: "+User_id );*/
        RequestParams params = new RequestParams(mPath);
        if(goldFlag){
            params.addQueryStringParameter("Uid", 1 + "");
            goldFlag=false;
        }else {
            params.addQueryStringParameter("Uid", 1 + "");
=======
        Intent intent=getIntent();
        int User_id=intent.getIntExtra("Uid",0);
        Log.e("abc", "getTest: "+User_id );
        RequestParams params = new RequestParams(mPath);
        if(goldFlag){
            params.addQueryStringParameter("User_id", 1 + "");
            params.addQueryStringParameter("gold","true");
            goldFlag=false;
        }else {
            params.addQueryStringParameter("User_id", 1 + "");
<<<<<<< HEAD
            params.addQueryStringParameter("gold","true");

=======
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
>>>>>>> 909f427a22bef6f1ec40c14c1224720ec811368d
            params.addQueryStringParameter("goldNum",""+(Integer.valueOf(mnine.getText().toString())+Integer.valueOf(mgoldNum.getText().toString())));
        }

        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                    Type type = new TypeToken<List<WalletBean>>() {
                    }.getType();
                    mWalletBeen = gson.fromJson(result, type);
                    //赋值
                    initData();
                    Log.e("data", "data：" + mWalletBeen);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("data", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void getPath() {
        MyApplication myApplication = (MyApplication) getApplication();
        mPath = myApplication.getUrl_Wallet();
    }

    public void ReadBackground(final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection mConnection = (HttpURLConnection) url.openConnection();
                    mConnection.setRequestMethod("GET");
                    if (mConnection.getResponseCode() == 200) {
                        InputStream inputStream = mConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();

                        Message message = new Message();
                        message.what = 0;
                        handler_background.sendMessage(message);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    //调用支付方法
    public void myPay(String title, final String describe,double price,boolean paytype) {
        /**
<<<<<<< HEAD
         * 第一个参数是上下文，第二个参数是商品名称,第三个参数是商品描述
=======
         * 第一个参数是上下文，第二个参数是商品名称,第三个参数是商品描述                                                                                                        0
>>>>>>> f8b6b108d6a2b67396c89d0a2acb5dba08316d44
         * 第四个是商品价格
         * 第5个参数为true时调用支付宝支付，为false时调用微信支付
         */
       /* BP.pay(this, title, describe, price, paytype, new PListener() {
            @Override
            public void orderId(String s) {
                Show("订单号：" + s);
            }

            @Override
            public void succeed() {
                getTest();
                Show("支付成功");

            }

            @Override
            public void fail(int i, String s) {
           //     onPayReaultReturn(price, describe, paytype, 0);
                if (i == 7777) {
                    Show("未安装微信客户端");
                }
                if (i == -3) {
                    Show("未安装微信插件");
                    InstallPlugin.installBmobPayPlugin("bp.qb");
                }
                Show("支付失败" + i + s);
            }

            @Override
            public void unknow() {
                Show("未知");
            }
        });*/

        BP.pay(title, describe, price, paytype, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(WalletActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                getTest();
                Toast.makeText(WalletActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            WalletActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_LONG).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(WalletActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }

                hideDialog();
            }
        });

    }



    //订单查询方法
 /*   public void onsearchOrder(String orderid) {
        *//**
         *
         *//*
        BP.query(WalletActivity.this, orderid, new QListener() {
            @Override
            public void succeed(String s) {
                Show("查询结果：" + s);
            }

            @Override
            public void fail(int i, String s) {
                Show("查询失败：" + s);
            }
        });
    }*/

    private void Show(String s) {
        Toast.makeText(WalletActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
