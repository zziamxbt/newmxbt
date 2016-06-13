package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.User;

public class EditNickname extends AppCompatActivity {
    private static final int NICKNAME_CODE = 1000;
    EditText editnickname_nickname;
    TextView editnickname_save;
    User user;
    public static EditNickname instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nickname);
        UserGetter();
        instance = this;
        initView();
        setView();

    }

    private void UserGetter() {
        Intent intent = getIntent();
       user= (User) intent.getSerializableExtra("user");
    }

    private void initView() {
       editnickname_nickname = (EditText) findViewById(R.id.editnickname_nickname);
        editnickname_save= (TextView) findViewById(R.id.editnickname_save);
    }

    private void setView() {
        editnickname_nickname.setText(user.getUnickname());
        editnickname_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUnickname(editnickname_nickname.getText().toString());
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                setResult(NICKNAME_CODE , intent);
                finish();
            }
        });

    }


}
