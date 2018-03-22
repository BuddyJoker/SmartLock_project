package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.adapter.ShareLockAdapter;
import com.example.users.smartlock_v11.lock.bean.ShareListBean;
import com.example.users.smartlock_v11.utils.CacheUtils;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by hp on 2018/3/22.
 */

public class ShareDeviceActivity extends Activity {

    private Context mContext;
    private int result_num;

    private ShareLockAdapter shareLockAdapter;
    private List<ShareListBean.DeviceInfoBean> deviceInfoBeans;

    @Bind(R.id.share_list_device)
    RecyclerView share_list;
    @Bind(R.id.empty_list)
    TextView empty_list;
    @Bind(R.id.iv_return_icon_s)
    ImageView ret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedevice);
        ButterKnife.bind(this);
        mContext=getApplicationContext();
        initData();
        initListener();
    }

    private void initListener() {
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        String token = CacheUtils.getString(mContext, "loginToken");
        if (!token.equals(null)){
            OkHttpUtils.get()
                    .url(Constants.GET_SHAREDEVICE)
                    .addHeader("Authorization","Bearer "+ token)
                    .build()
                    .execute(new StringCallback(){
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(mContext,"网络连接错误"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            empty_list.setText("暂无分享设备");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (!response.equals("")&&!response.equals("[\"value1\",\"value2\"]")){
                                processData(response);
                                shareLockAdapter=new ShareLockAdapter(mContext,deviceInfoBeans);
                                share_list.setLayoutManager(new LinearLayoutManager(mContext));
                                share_list.setAdapter(shareLockAdapter);
                            }else{
                                Toast.makeText(mContext,"无返回信息/"+response,Toast.LENGTH_SHORT).show();
                                empty_list.setText("暂无分享设备");
                            }
                        }
                    });
        }else{
            Toast.makeText(mContext,"未获取token",Toast.LENGTH_SHORT).show();
        }
    }

    private void processData(String json) {
        Gson gson=new Gson();
        ShareListBean shareListBean=gson.fromJson(json,ShareListBean.class);
        deviceInfoBeans=shareListBean.getDeviceInfo();
        //result_num=shareListBean.getDevicenum();
    }


}
