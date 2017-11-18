package com.example.users.smartlock_v11.app.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.MainActivity;
import com.example.users.smartlock_v11.app.register.RegisterActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by BigBoss on 2017/11/15.
 */

public class LoginActivity extends Activity {

    public Context mContext;
    private String jsonObject;
    private String Error_code;
    private String msg;

    @Bind(R.id.user_name)
    EditText user_name;
    @Bind(R.id.pass_word)
    EditText pass_word;
    @Bind(R.id.login)
    Button btn_login;
    @Bind(R.id.register_link)
    TextView register_link;
    @Bind(R.id.about_us)
    TextView about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode= TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext=LoginActivity.this;
        initListener();
    }

    private void sendDataToNet() {
        OkHttpUtils.postString()
                .url("url")
                .content(processSendData(user_name.getText().toString(),pass_word.getText().toString()))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext,"发送失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response!=null){
//                            Intent intent=new Intent(mContext,MainActivity.class);
//                            startActivity(intent);
//                            finish();
                            processRecData(response);
                            if (Error_code.equals("0000")){
                                Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext,"失败："+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void initListener() {
        //登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_name.getText().toString().equals("")&&!pass_word.getText().toString().equals("")){
                    sendDataToNet();
                }else{
                    Toast.makeText(mContext,"请输入用户名和密码"+msg,Toast.LENGTH_SHORT).show();
                }

            }
        });
        //注册
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //关于我们
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"关于我们",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String processSendData(String name, String pwd){
        Gson gson=new Gson();
        LoginBean loginBean=new LoginBean(name,pwd);
        jsonObject=gson.toJson(loginBean);
        return jsonObject;
    }

    private void processRecData(String json){
        Gson gson=new Gson();
        LoginBean loginBean=gson.fromJson(json,LoginBean.class);
        Error_code=loginBean.getError_code();
        msg=loginBean.getMsg();
    }


}
