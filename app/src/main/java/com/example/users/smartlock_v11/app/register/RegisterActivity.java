package com.example.users.smartlock_v11.app.register;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.users.smartlock_v11.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2017/11/17.
 */

public class RegisterActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        //性别选项改变
        sex_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==female.getId()){
                    //选中女
                }else{
                    //选中男
                }
            }
        });
        //提交注册信息
        register_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理数据
            }
        });
    }

    private void processSendData(){

    }
}
