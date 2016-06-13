package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity {
    /**
     * 用户的正常登录途径
     */
    private EditText mUname;//用户帐号
    private EditText mUpassword;//用户密码
    private String mUnameString;//用户帐号字符串结果
    private String mUpasswordString;//用户密码字符串结果
    private String mLoginPath;//访问后台登录的路径
    private ImageView mLoginBack;//登录页面返回键
    private TextView mLoginProtocol;//登录协议

    /**
     * 用户记住密码的相关信息
     */
    private CheckBox mCheckBox;//复选框，标志是否记住密码
    public static final String SAVE = "save";//偏好设置，保存用户帐号和密码的文件名
    SharedPreferences mSharedPreferences;//偏好设置,保存用户是否记住密码以及相关信息
    SharedPreferences.Editor mEditor;//偏好设置保存信息

    /**
     * 第三方登录：QQ，Sina微博（使用的友盟第三方登录）
     */
    private ImageView mLoginSinaImg;
    private ImageView mLoginQQImg;
    UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");//整个平台的Controller，
    private String QQ_APPID = "1105320869";
    private String QQ_APPKEY = "1Dx8sDvBBTGYOmi9";

    /**
     * 忘记密码时，采用Mob短信验证，进入主界面
     */
    private Button mForgetButton;//忘记密码按钮
    private String Mob_APPKEY = "11f2cd0844236";//Mob短信验证的key
    private String Mob_APPSECRET = "1789e20b33bf0a1b79419b32077c7b72";//Mob短信验证的secret负责管理整个SDK的配置、操作等处理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();//初始化控件
        adjustIsLogin();//判断用户是不是记住了密码
        initDatas();//初始化数据
        focusChangedListener();//焦点改变的监听事件（主要用于检测用户的输入是否正确）
        SMSSDK.initSDK(this, Mob_APPKEY, Mob_APPSECRET); //初始化SMSSDK，用于Mob短信验证
        configPlatforms();//第三方登录配置需要分享的相关平台
        setShareContent();//第三方登录设置分享内容
        initListeners(); //第三方登录的监听事件
    }





    /***************************************************以下是判断用户是否记住了密码（BEGIN）*******************************************************/

    /**
     * 判断用户是否记住了密码，记住：下次登录在页面显示帐号和密码信息；未记住：下次输入框清空
     */
    private void adjustIsLogin() {
        mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
        //取出偏好设置文件中的值，第一个参数：数据的键值；第二个参数：键值不存在时默认的值
        boolean isLogin = mSharedPreferences.getBoolean("isLogin", false);
        Log.e("QIYU", isLogin + "");
        if (isLogin) {
            //已经记住了用户名和密码，记住的用户名和密码显示在输入框
            mUnameString = mSharedPreferences.getString("uname", "");
            mUpasswordString = mSharedPreferences.getString("upassword", "");
            Log.e("QIYU", mUnameString);
            Log.e("QIYU", mUpasswordString);
            mUname.setText(mUnameString);
            mUpassword.setText(mUpasswordString);
            mCheckBox.setChecked(true);
        }
    }

    /***************************************************以上是判断用户是否记住了密码（END）*******************************************************/

    /**
     * 初始化控件
     */
    private void initViews() {
        mLoginBack= (ImageView) findViewById(R.id.login_returnback);
        mUname = (EditText) findViewById(R.id.login_uname);
        mUpassword = (EditText) findViewById(R.id.login_upassword);
        mCheckBox = (CheckBox) findViewById(R.id.login_checkbox);
        mForgetButton = (Button) findViewById(R.id.login_forget);
        mLoginSinaImg= (ImageView) findViewById(R.id.login_weibo);
        mLoginQQImg= (ImageView) findViewById(R.id.login_qq);
        mLoginProtocol= (TextView) findViewById(R.id.login_protocol);
    }

    /**
     * 初始化数据，获取登录验证的ServletURL
     */
    private void initDatas() {
        MyApplication myApplication = (MyApplication) getApplication();
        mLoginPath = myApplication.getLoginUrl();
    }

    /**
     * 控件焦点的监听事件
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


        //2,判断用户帐号输入的合法性
        mUpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mUname.getText().toString().equals("")) {
                    //在密码输入框，如果获取帐号输入框为null（说明帐号格式不对），重新获取焦点为帐号输入框
                    mUname.requestFocus();
                }
            }
        });


        //3,忘记密码的单击事件
        mForgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建手机号界面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        //判断是否已经完成
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //获取数据data
                            HashMap<String, Object> maps = (HashMap<String, Object>) data;
                            //获取国家信息
                            String country = (String) maps.get("country");
                            //获取手机号信息
                            String phone = (String) maps.get("phone");
                            //提交验证信息
                            submitUserInfo(country, phone);
                        }
                    }
                });
                //验证成功，显示成功
                // registerPage.show(LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
                //intent.putExtra("name", "Success22!");
                registerPage.show(LoginActivity.this, intent);


            }
        });


        //4,返回键的监听事件
        mLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,QiyuActivity.class);
                startActivity(intent);
            }
        });

        //5,查看协议的监听事件
        mLoginProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ProtocolActivity.class);
                intent.putExtra("flag","login");
                startActivity(intent);

            }
        });

    }

    //Mob短信验证的提交信息
    private void submitUserInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickname = "qiyu";
        SMSSDK.submitUserInfo(uid, nickname, null, country, phone);
    }


    /**
     * 用户登录的单击按钮
     *
     * @param view
     */
    public void loginNow(View view) {
        //首先判断用户的密码是否为空
        if (!checkEdit()) {
            //密码为空，焦点移动到密码输入框
            mUpassword.requestFocus();
        } else {
            //密码不为空，向后台提交登录的验证信息
            RequestParams params = new RequestParams(mLoginPath);
            mUnameString = mUname.getText().toString();
            mUpasswordString = mUpassword.getText().toString();
            params.addQueryStringParameter("uname", mUnameString);
            params.addQueryStringParameter("upassword", getMD5Str(mUpasswordString));
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    mUnameString = mUname.getText().toString();
                    mUpasswordString = mUpassword.getText().toString();
                    if (mCheckBox.isChecked()) {
                        //用户选择了记住密码
                        if (result!=null) {
                            //验证结果正确，保存
                            mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                            mEditor = mSharedPreferences.edit();
                            mEditor.putString("uname", mUnameString);
                            mEditor.putString("upassword", mUpasswordString);
                            mEditor.putBoolean("isLogin", true);
                            mEditor.commit();
                        } else {
                            //验证结果不正确
                            mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                            mEditor = mSharedPreferences.edit();
                            mEditor.putString("uname", mUnameString);
                            mEditor.putString("upassword", mUpasswordString);
                            mEditor.putBoolean("isLogin", false);
                            mEditor.commit();
                        }
                    } else {
                        //用户取消记住密码
                        mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                        mEditor = mSharedPreferences.edit();
                        mEditor.putString("uname", mUnameString);
                        mEditor.putString("upassword", mUpasswordString);
                        mEditor.putBoolean("isLogin", false);
                        mEditor.commit();

                    }
                    if (result !=null) {
                        //登录成功，跳转至主界面
                        show("登录成功！");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Gson gson=new Gson();
                        Type type = new TypeToken<User>() {
                        }.getType();
                        Bundle bundle = new Bundle();
                        User user = gson.fromJson(result ,type);
                        bundle.putSerializable("user", user);
                        bundle.putBoolean("isLogin",true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        //登录不成功，提示信息并将页面输入框全部置空
                        show("登录失败！");
                        mUname.setText("");
                        mUpassword.setText("");
                        mUname.requestFocus();
                    }

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
    }

    /**
     * 判断手机号格式是否正确
     *
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
     *
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
     * 判断用户输入的合法性
     *
     * @return true:表示输入合法
     * false:输入不合法，重新输入
     */
    private boolean checkEdit() {
        if (mUname.getText().toString().trim().equals("")) {
            show("帐号不能为空");
        } else if (mUpassword.getText().toString().trim().equals("")) {
            show("密码不能为空");
        } else {
            return true;
        }
        return false;
    }

    //Toast信息
    private void show(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    /***************************************************以下是友盟第三方登录的内容（BEGIN）***********************************************************/

    /**
     * 设置第三方分享内容
     */
    private void setShareContent() {
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.this, QQ_APPID, QQ_APPKEY);
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
    }

    /**
     * 配置需要分享的相关平台
     */
    private void configPlatforms() {
        // 添加新浪sso授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        //添加QQ、QZone平台
        addQQQZonePlatform();
    }

    /**
     * 添加QQ、QZone平台
     */
    private void addQQQZonePlatform() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, QQ_APPID, QQ_APPKEY);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.this, QQ_APPID, QQ_APPKEY);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                show("授权开始");
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                // 获取uid
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    //uid不为空，获取用户信息
                    getUserInfo(platform);
                    Intent intent=new Intent(LoginActivity.this,SuccessActivity.class);
                    intent.putExtra("name","第三方登录");
                    startActivity(intent);
                } else {
                    show("授权失败...");
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                show("授权失败...");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                show("授权取消");
            }
        });
    }

    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                String showText = "";
                if (status == StatusCode.ST_CODE_SUCCESSED) {
                    showText = "用户名：" +
                            info.get("screen_name").toString();
                    com.umeng.socialize.utils.Log.d("#########", "##########" + info.toString());
                    Log.e("QIYU", showText);
                } else {
                    showText = "获取用户信息失败";
                }

                if (info != null) {
                    Toast.makeText(LoginActivity.this, info.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 如果有使用任一平台的SSO(Single Sign On,单点登录)授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                resultCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 第三方登录的监听事件
     */
    private void initListeners() {
        mLoginQQImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(SHARE_MEDIA.QQ);
            }
        });
        mLoginSinaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(SHARE_MEDIA.SINA);
            }
        });
    }
    /***************************************************以上是友盟第三方登录的内容（END）***********************************************************/
}
