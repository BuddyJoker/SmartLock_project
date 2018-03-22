package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.MainActivity;
import com.example.users.smartlock_v11.lock.bean.ScannerBean;
import com.example.users.smartlock_v11.utils.CacheUtils;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by hp on 2018/3/17.
 */

public class ScannerActivity extends Activity implements QRCodeView.Delegate {

    private String token;
    private String code;

    @Bind(R.id.zxingview)
    QRCodeView zxingview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        ButterKnife.bind(this);
        zxingview.setDelegate(this);
        token=CacheUtils.getString(ScannerActivity.this,"loginToken");
    }

    @Override
    public void onScanQRCodeSuccess(String sharecode) {
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        //返回shareCode
        Intent intent=new Intent();
        intent.putExtra("request_url",sharecode);
        this.setResult(-1,intent);

        //processSendData(sharecode);
        OkHttpUtils.postString()
                .url(Constants.SHARE)
                .content(sharecode)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("Authorization","Bearer "+token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!response.equals("")){
                            processNetData(response);
                            switch (code){
                                case "0000":
                                    Toast.makeText(ScannerActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
                                    break;
                                case "0001":
                                    Toast.makeText(ScannerActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }else{
                            Toast.makeText(ScannerActivity.this,"未获取返回值",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        finish();
    }

    private void processNetData(String json) {
        Gson gson=new Gson();
        ScannerBean scannerBean=gson.fromJson(json,ScannerBean.class);
        code=scannerBean.getReturnCode();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "相机打开失败", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }
}
