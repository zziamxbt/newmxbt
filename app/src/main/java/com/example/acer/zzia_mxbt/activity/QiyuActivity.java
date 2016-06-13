package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.acer.zzia_mxbt.R;

public class QiyuActivity extends AppCompatActivity {
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiyu);

    }

    public void userLogin(View view) {
        intent=new Intent(QiyuActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void userRegist(View view) {
        intent=new Intent(QiyuActivity.this,RegistActivity.class);
        startActivity(intent);
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void themePage(View view) {
        intent=new Intent(QiyuActivity.this,ThemeActivity.class);
        startActivity(intent);
    }
}
