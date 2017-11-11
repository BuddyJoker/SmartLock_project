package com.example.users.smartlock_v11.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.users.smartlock_v11.home.bean.TokenBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by BigBoss on 2017/10/16.
 */

public class AuthService {
    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        return getAuth(Constants.API_KEY, Constants.SECRET_KEY);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        OkHttpUtils
                .post()
                .url(Constants.authHost)
                .addParams("grant_type","client_credentials")// 1. grant_type为固定参数
                .addParams("client_id",ak)// 2. 官网获取的 API Key
                .addParams("client_secret",sk)// 3. 官网获取的 Secret Key
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("error","联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }
                });
        return token;
    }

    private static String token;

    private static void processData(String json) {
        Gson gson=new Gson();
        TokenBean tokenBean=gson.fromJson(json,TokenBean.class);
        token=tokenBean.getAccess_token();
    }
}
