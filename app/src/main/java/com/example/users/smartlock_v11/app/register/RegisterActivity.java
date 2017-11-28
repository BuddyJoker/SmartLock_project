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
import com.example.users.smartlock_v11.app.login.LoginActivity;
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
    private String Error_code;
    private String msg;
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String sex;

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
                    sex="F";
                }else{
                    //选中男
                    Toast.makeText(RegisterActivity.this,"男",Toast.LENGTH_SHORT).show();
                    sex="M";
                }
            }
        });
        //提交注册信息
        register_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=register_pass_word.getText().toString();
                password=register_pass_word.getText().toString();
                email=mail.getText().toString();
                phoneNum=num_phone.getText().toString();
                //处理数据
                if (!username.equals("")&&!password.equals("")&&!email.equals("")&&!phoneNum.equals("")&&!sex.equals("")){
                    sendDataToNet();
                }else{
                    Toast.makeText(mContext,"请补全信息",Toast.LENGTH_SHORT).show();
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
                .url("http://www.writebug.site/api/register")
                .content(processSendData(username,password,email,phoneNum,sex))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext,"发送失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response!=null){
                            processRecData(response);
                            switch (Error_code){
                                case "0000":
                                    Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    finish();
                                    break;
                                case "0001":
                                    Toast.makeText(mContext, "失败：发送的JSON包格式不符合要求", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0002":
                                    Toast.makeText(mContext, "失败：用户名包含危险字符", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0003":
                                    Toast.makeText(mContext, "失败：密码包含危险字符", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0006":
                                    Toast.makeText(mContext, "失败：邮箱格式不正确", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0007":
                                    Toast.makeText(mContext, "失败：手机号码格式不正确", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0008":
                                    Toast.makeText(mContext, "失败：用户名已存在", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });

    }

    private String processSendData(String name,String passwd,String p_email,String p_phonenum,String p_sex){
        Gson gson=new Gson();
        RegisterBean registerBean=new RegisterBean(name,passwd,p_email,p_phonenum,p_sex);
        return gson.toJson(registerBean);
    }

    private void processRecData(String json){
        Gson gson=new Gson();
        RegisterBean registerBean=gson.fromJson(json,RegisterBean.class);
        Error_code=registerBean.getError_code();
    }
}
