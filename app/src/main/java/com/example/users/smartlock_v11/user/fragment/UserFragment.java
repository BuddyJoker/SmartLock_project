package com.example.users.smartlock_v11.user.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.base.BaseFragment;
import com.example.users.smartlock_v11.home.bean.TokenBean;
import com.example.users.smartlock_v11.user.adapter.UserAdapter;
import com.example.users.smartlock_v11.user.bean.UserBean;
import com.example.users.smartlock_v11.user.bean.UserInfoBean;
import com.example.users.smartlock_v11.utils.AuthService;
import com.example.users.smartlock_v11.utils.CacheUtils;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by BigBoss on 2017/8/9.
 */

public class UserFragment extends BaseFragment {

    @Bind(R.id.list_user)
    RecyclerView mRecyclerView;

    public static final String GROUP_USER_URL="https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";

    private String error_token;
    private String error_token_msg;

    private UserAdapter userAdapter;
    private List<UserInfoBean.ResultBean> result;
    private String access_token;
    private Long result_id;
    private int result_num;

    @Override
    public View initView() {
        View view=View.inflate(mContext, R.layout.fragment_user,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        //
        //getTokenFromNet(Constants.API_KEY,Constants.SECRET_KEY);
        access_token= CacheUtils.getString(mContext,"token");
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(GROUP_USER_URL)
                .addParams("access_token",access_token)
                .addParams("group_id","AnJiaSmartLock_Test_1")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            //Log.e("TAG", "联网失败" + e.getMessage());
            Toast.makeText(mContext,"联网失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                //json解析并抽取数据
                processData(response);
                //Toast.makeText(mContext,result_id.toString()+":"+result_num,Toast.LENGTH_SHORT).show();
                //设置适配器并显示数据
                userAdapter=new UserAdapter(mContext,result);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.setAdapter(userAdapter);
            }
        }
    }

    private void processData(String json) {
        Gson gson=new Gson();
        //数据bean抽取json数据
        UserInfoBean userInfoBean=gson.fromJson(json,UserInfoBean.class);
        //返回result数据集合
        result=userInfoBean.getResult();
        result_id=userInfoBean.getLog_id();
        result_num=userInfoBean.getResult_num();
    }


    private void getTokenFromNet(String ak, String sk){
        OkHttpUtils
                .post()
                .url(Constants.authHost)
                .addParams("grant_type","client_credentials")
                .addParams("client_id",Constants.API_KEY)
                .addParams("client_secret",Constants.SECRET_KEY)
                .build()
                .execute(new TokenStringCallBack());
    }

    public class TokenStringCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("error","联网失败");
        }

        @Override
        public void onResponse(String response, int id) {
            if (response!=null){
                processToken(response);
                if (error_token!=null){
                    Toast.makeText(mContext,error_token+":"+error_token_msg,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext,access_token,Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            if (access_token!=null){
                getDataFromNet();
            }else{
                Toast.makeText(mContext,"未获取到token",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void processToken(String json){
        Gson gson=new Gson();
        TokenBean tokenBean=gson.fromJson(json,TokenBean.class);
        access_token=tokenBean.getAccess_token();
        error_token=tokenBean.getError();
        error_token_msg=tokenBean.getError_description();
    }
}
