package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.User;

public class EditSign extends AppCompatActivity {


    private static final int SIGN_CODE = 1002;
    EditText editsign_sign;
    TextView editsign_save;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign);
        UserGetter();
        initView();
        setView();

    }

    private void UserGetter() {
        Intent intent = getIntent();
        user= (User) intent.getSerializableExtra("user");
    }

    private void initView() {
        editsign_sign = (EditText) findViewById(R.id.editsign_sign);
        editsign_save= (TextView) findViewById(R.id.editsign_save);
    }

    private void setView() {
        editsign_sign.setText(user.getUsign());
        editsign_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsign(editsign_sign.getText().toString());
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                setResult(SIGN_CODE,intent);
                finish();
            }
        });

    }


}
