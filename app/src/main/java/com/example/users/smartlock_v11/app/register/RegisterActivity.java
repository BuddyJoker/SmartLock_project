package com.example.users.smartlock_v11.app.register;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.MainActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by BigBoss on 2017/11/17.
 */

public class RegisterActivity extends Activity {

    public Context mContext;
    private String jsonObject;
    private String Error_code;
    private String msg;

    @Bind(R.id.register_user_name)
    EditText register_user_name;
    @Bind(R.id.register_pass_word)
    EditText register_pass_word;
    @Bind(R.id.mail)
    EditText mail;
    @Bind(R.id.num_phone)
    EditText num_phone;
    @Bind(R.id.sex_select)
    RadioGroup sex_select;
    @Bind(R.id.female)
    RadioButton female;
    @Bind(R.id.male)
    RadioButton male;
    @Bind(R.id.register_commit)
    Button register_commit;
    @Bind(R.id.iv_return_icon)
    ImageView iv_return_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition slide= TransitionInflater.from(this).inflateTransition(R.transition.slide);//activity侧滑动画
        getWindow().setEnterTransition(slide);//进入时调用动画
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext=RegisterActivity.this;
        initListener();
    }

    private void initListener() {
        //性别选项改变
        sex_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==female.getId()){
                    //选中女
                    Toast.makeText(RegisterActivity.this,"女",Toast.LENGTH_SHORT).show();
                }else{
                    //选中男
                    Toast.makeText(RegisterActivity.this,"男",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //提交注册信息
        register_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理数据
                if (!register_pass_word.getText().toString().equals("")&&!register_user_name.getText().toString().equals("")){
                    sendDataToNet();
                }else{
                    Toast.makeText(mContext,"用户名和密码不能为空"+msg,Toast.LENGTH_SHORT).show();
                }
            }
        });
        //返回
        iv_return_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
    }

    private void sendDataToNet() {
        OkHttpUtils.postString()
                .url("url")
                .content(processSendData(register_user_name.getText().toString(),register_pass_word.getText().toString()))
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
//
                            processRecData(response);
                            if (Error_code.equals("0000")){
                                Toast.makeText(mContext,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(mContext,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(mContext,"失败："+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private String processSendData(String name,String passwd){
        Gson gson=new Gson();
        RegisterBean registerBean=new RegisterBean(name,passwd);
        jsonObject=gson.toJson(registerBean);
        return jsonObject;
    }

    private void processRecData(String json){
        Gson gson=new Gson();
        RegisterBean registerBean=gson.fromJson(json,RegisterBean.class);
        Error_code=registerBean.getUsername();
    }
}
