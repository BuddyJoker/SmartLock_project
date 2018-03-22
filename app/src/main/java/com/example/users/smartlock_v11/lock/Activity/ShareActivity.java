package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.bean.ShareCodeBean;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by hp on 2018/3/17.
 */

public class ShareActivity extends Activity {

    private static final String QR_CODE="http://www.writebug.site/PrivateSmartHome/api/V1/GetShareCode/username/device";

    private String QR_msg;

    @Bind(R.id.QR_code)
    ImageView QR_code;
    @Bind(R.id.iv_return_icon_s1)
    ImageView ret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        initData();
        initListener();
        //QR_code.setImageBitmap(stringToBitmap(base64));
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
        OkHttpUtils.get()
                .url(QR_CODE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ShareActivity.this,"获取图片失败:"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                        String str = "data:image/jpg;base64,"+QR_msg;
                        if (!str.equals("")){
                            QR_code.setImageBitmap(stringToBitmap(str));
                        }
                    }
                });
    }

    private void processData(String json) {
        Gson gson1=new Gson();
        ShareCodeBean shareCodeBean=gson1.fromJson(json,ShareCodeBean.class);
        QR_msg=shareCodeBean.getQRShareCode();
    }

    //Base64解码图片
    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
