package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.application.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistActivity extends AppCompatActivity {
    private ImageView mRegistBack;//注册返回键
    private EditText mUname;//用户帐号
    private EditText mUpassword;//用户密码
    private EditText mReupassword;//用户确认密码
    private Button mSubmit;//提交注册按钮
    private String mRegistPath;//访问后台注册Servlet的路径
    public static final String TAG = "XUTILS";
    private TextView mRegistProtocol;//协议文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //1,初始化控件
        initViews();
        //2,初始化数据
        initDatas();
        //3,焦点改变的监听事件
        focusChangedListener();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mRegistBack= (ImageView) findViewById(R.id.regist_returnback);
        mUname = (EditText) findViewById(R.id.regist_uname);
        mUpassword = (EditText) findViewById(R.id.regist_upassword);
        mReupassword = (EditText) findViewById(R.id.regist_reupassword);
        mSubmit = (Button) findViewById(R.id.regist_submit);
        mRegistProtocol= (TextView) findViewById(R.id.regist_protocol);
    }

    /**
     * 初始化数据，获取MyApplication的URL地址
     */
    private void initDatas() {
        MyApplication myApplication = (MyApplication) getApplication();
        mRegistPath = myApplication.getRegistUrl();
    }

    /**
     * 控件改变焦点的监听事件
     * 基本思路：在下一个控件集中焦点时，依据上一个控件的内容是否为空，来设定光标的移动位置
     */
    private void focusChangedListener() {

        //1,限制用户帐号的输入规范（手机号格式）
        mUname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //光标移动时，利用正则表达式判断是否为手机号格式
                    if (!isMobileNO(mUname.getText().toString())) {
                        show("请输入正确的手机号格式");
                        //setText(""):必须在这里处理，否则在密码输入框无法判断。
                        mUname.setText("");
                       /* //异步延时200ms获取焦点。最好在下一个控件处理，否则要采用异步延时的方法。
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mUname.requestFocus();
                            }
                        },200);*/
                    }
                }
            }
        });


        //2,限制用户密码输入的规范（4—20位的任意字符）
        mUpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mUname.getText().toString().equals("")) {
                    //在密码输入框，如果获取帐号输入框为null（说明帐号格式不对），重新获取焦点为帐号输入框
                    mUname.requestFocus();
                }
                if (!hasFocus) {
                    //光标移动时，利用正则表达式判断密码是4—20位的任意字符（包含首尾）
                    if (!isPasswordOk(mUpassword.getText().toString())) {
                        show("密码为4-20位的任意字符！");
                        mUpassword.setText("");
                    }
                }
            }
        });


        //3,判断密码的输入是否规范
        mReupassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mUpassword.getText().toString().equals("")) {
                    //若密码不规范，重新获取焦点在密码输入框
                    mUpassword.requestFocus();
                }
            }
        });


        //4,注册按钮的监听事件
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先判断用户输入是否合法
                if (!checkEdit()) {
                    //输入不合法，返回重新输入
                    return;
                } else {
                    //输入合法，向后台提交数据，请求注册
                    RequestParams params = new RequestParams(mRegistPath);
                    //传递用户的手机号
                    params.addQueryStringParameter("uname", mUname.getText().toString());
                    //传递用户的密码，使用MD5加密
                    params.addQueryStringParameter("upassword", getMD5Str(mUpassword.getText().toString()));
                    //利用xutils进行结果的回调时间
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //显示后台处理的信息
                            show(result);
                            if (result.equals("Success,注册用户成功！")) {
                                //如果注册成功，跳转到主页面
                                Intent intent = new Intent(RegistActivity.this, SuccessActivity.class);
                                intent.putExtra("name",mUname.getText().toString());
                                startActivity(intent);
                            } else {
                                //如果手机号已被注册，提示用户并清空所有的用户输入信息
                                mUname.getText().clear();
                                mUpassword.getText().clear();
                                mReupassword.getText().clear();
                                mUname.requestFocus();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                            Log.e(TAG, "错误原因：" + ex.toString());
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }
        });


        //5,返回键的监听事件
        mRegistBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistActivity.this,QiyuActivity.class);
                startActivity(intent);
            }
        });


        //6,协议监听事件
        mRegistProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistActivity.this,ProtocolActivity.class);
                intent.putExtra("flag","regist");
                startActivity(intent);
            }
        });
    }


    /**
     * 判断手机号格式是否正确
     * @param mobiles：用户在帐号输入框输入的内容
     * @return，符合规范,返回true
     */
    private boolean isMobileNO(String mobiles) {
        /**
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         联通：130、131、132、152、155、156、185、186
         电信：133、153、180、189、（1349卫通）
         总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9/
         */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }



    /**
     * 使用MD5将手机号加密
     * @param str：用户在密码输入框输入的内容
     * @return：将用户密码加密后返回。
     */
    private String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        //16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }


    /**
     * 判断用户的密码是否符合规范（4—20位任意字符）
     * @param password:将用户输入的密码传入，
     * @return，符合规范，返回true
     */
    private boolean isPasswordOk(String password) {
        String text = "\\S{4,20}";
        if (TextUtils.isEmpty(password))
            return false;
        else
            return password.matches(text);
    }


    /**
     * 判断用户输入的合法性，如果两次密码不一致，重新获取焦点在确认密码输入框
     *
     * @return true:表示输入合法
     * false:输入不合法，重新输入
     */
    private boolean checkEdit() {
        if (!mUpassword.getText().toString().trim().equals(mReupassword.getText().toString().trim())) {
            show("两次密码输入不一致");
            mReupassword.setText("");
            mReupassword.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    //Toast信息，在界面显示
    private void show(String text) {
        Toast.makeText(RegistActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
