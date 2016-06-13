package com.example.acer.zzia_mxbt.activity;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.utils.GetToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

public class ContentCreateActivity extends AppCompatActivity {
    private static final int CONTENT_CODE = 106;
    ImageView content_back ;
    TextView content_save;
    EditText content_editor;
    String mpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        findView();
        setView();
    }

    private void setView() {
        content_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        content_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content_editor.getText().toString()==null){
                    Toast.makeText(ContentCreateActivity.this, "请您输入内容后进行保存", Toast.LENGTH_SHORT).show();
                }
                else{
                    commit(content_editor.getText().toString());

                }
            }
        });
    }

    private void commit(String content) {
        String andwritename=System.currentTimeMillis()+".txt";
        mpath="http://o7f8zz7p8.bkt.clouddn.com/"+andwritename;
        byte [] data=content.getBytes();
        GetToken mGetToken;
        final String token;
        String urlPath="http://o758frrmu.bkt.clouddn.com";
        String name="zzia-mxbt";//zzia_mxbt

        if(data!=null){
            mGetToken = new GetToken();
            token = mGetToken.getToken(name);
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(data,andwritename,token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {

                            if (info.statusCode == 200) {
                                Toast.makeText(getApplication(), "完成上传", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.putExtra("content", content_editor.getText().toString());
                                intent.putExtra("path",mpath);
                                setResult(CONTENT_CODE,intent);
                                finish();
                            } else {

                                Toast.makeText(getApplication(), info.statusCode + "上传失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, null);
        }

    }

    private void findView() {

        content_back= (ImageView) findViewById(R.id.content_back);
        content_save = (TextView) findViewById(R.id.content_save);
        content_editor = (EditText) findViewById(R.id.content_editor);
    }


}
