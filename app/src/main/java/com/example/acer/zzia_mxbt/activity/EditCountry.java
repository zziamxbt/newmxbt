package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.User;

public class EditCountry extends AppCompatActivity {

    private static final int intent = 1001;
    private static final int COUNTRY_CODE = 1001;
    EditText editcountry_country;
    TextView editcountry_save;
    public static EditCountry instance = null;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_country);
        UserGetter();
        instance =this;
        initView();
        setView();

    }

    private void UserGetter() {
        Intent intent = getIntent();
        user= (User) intent.getSerializableExtra("user");
    }

    private void initView() {
        editcountry_country = (EditText) findViewById(R.id.editcountry_country);
        editcountry_save= (TextView) findViewById(R.id.editcountry_save);
    }

    private void setView() {
        editcountry_country.setText(user.getUcountry());
        editcountry_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUcountry(editcountry_country.getText().toString());
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                setResult(COUNTRY_CODE,intent);
                finish();
            }
        });

    }


}
