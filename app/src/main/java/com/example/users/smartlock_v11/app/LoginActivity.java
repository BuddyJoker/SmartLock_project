package com.example.users.smartlock_v11.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by BigBoss on 2017/11/15.
 */

public class LoginActivity extends Activity {

    public Context mContext;

    @Bind(R.id.user_name)
    EditText user_name;
    @Bind(R.id.pass_word)
    EditText pass_word;
    @Bind(R.id.login)
    Button btn_login;
    @Bind(R.id.about_us)
    TextView about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=LoginActivity.this;
        initListener();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.post()
                .url("")
                .addParams("name",user_name.getText().toString())
                .addParams("pwd",pass_word.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"关于我们",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
