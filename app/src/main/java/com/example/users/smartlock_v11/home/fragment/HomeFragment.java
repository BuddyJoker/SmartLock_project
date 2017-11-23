package com.example.users.smartlock_v11.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.auth.DevAuth;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.base.BaseFragment;
import com.example.users.smartlock_v11.home.bean.HomeBean;
import com.example.users.smartlock_v11.home.bean.TokenBean;
import com.example.users.smartlock_v11.utils.AuthService;
import com.example.users.smartlock_v11.utils.CacheUtils;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.app.Activity.RESULT_OK;

/**
 * Created by BigBoss on 2017/8/9.
 *
 * 问题：无效参数(解决)
 *      1.文件大小，编码后不超过10M
 *      2.未获取access_token参数
 *
 * 问题：access_token失效(解决)
 *      未获取到token
 *
 * 问题：异常：expected an int but ...一串数字(解决）
 *      返回的log_id超过int的取值范围
 *
 * 问题：token首次请求无返回值(解决)
 *      网络请求具有一定延迟，即数据还未获取就继续顺序执行代码了
 *
 * 问题：自拍图片加载不清晰(解决)
 *      1.从intent中获取的数据是经过压缩的，无论怎样处理都无法保持图片得清晰度
 *      2.获取手机系统文件路径，并自定义图片的路径
 *      3.将图片路径转化成URI
 *      4.创建图片的文件流并解析成bitmap以显示在imageView中
 *
 * 问题：二次拍照不能刷新imageView(解决)
 *      调试大法好，不解释了
 */

public class HomeFragment extends BaseFragment {

    String FileName;


    private String access_token;
    private String error_token;
    private String error_token_msg;
    private String result_id;
    private int error_code;
    private String error_msg;
    private static String imgStr;
    private static FileInputStream fis=null;
    private static Bitmap bitmap=null;
    JSONObject res;

    @Bind(R.id.cam)
    ImageView cam;
    @Bind(R.id.camera_input)
    ImageView camera_input;
    @Bind(R.id.pic_upload)
    ImageView pic_upload;
    @Bind(R.id.txt_info)
    TextView txt_info;
    @Bind(R.id.user_info)
    EditText user_info;
    @Bind(R.id.register_info)
    LinearLayout register_info_view;

    private String mFilePath=Environment.getExternalStorageDirectory().getPath();//获取手机sdcard目录
    private static final int REQ = 30212;//标识码

    @Override
    public View initView() {
        View view=View.inflate(mContext, R.layout.fragment_home,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        initListener();
        getTokenFromNet(Constants.API_KEY,Constants.SECRET_KEY);
    }

    private void initListener(){
        pic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgStr!=null){
                    if (access_token!=null){
                        if (!user_info.getText().toString().equals("")){
                            getDataFromNet();
                        }else{
                            Toast.makeText(mContext,"请完善信息",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(mContext,"未获取到token",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(mContext,"请先拍照",Toast.LENGTH_LONG).show();
                }


            }
        });

        camera_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (!mFilePath.equals(Environment.getExternalStorageDirectory().getPath())){
                    mFilePath=Environment.getExternalStorageDirectory().getPath();
                }
                mFilePath=mFilePath+"/"+System.currentTimeMillis()+".jpg";
                Uri photoUri=Uri.fromFile(new File(mFilePath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(intent,REQ);
            }
        });
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case REQ:
                    //将文件流解析为bitmap并显示
                    //FileInputStream fis=null;
                    try {
                        fis=null;
                        bitmap=null;
                        fis=new FileInputStream(mFilePath);
                        bitmap=BitmapFactory.decodeStream(fis);
                        String test=mFilePath;
                        cam.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //Base64
                    try {
                        byte[] imgData= Util.readFileByBytes(mFilePath);
                        imgStr= Base64Util.encode(imgData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //显示视图
                    if (register_info_view.getVisibility()==View.GONE){
                        cam.setVisibility(View.VISIBLE);
                        register_info_view.setVisibility(View.VISIBLE);
                    }
                    break;

            }
        }
    }

    private void getDataFromNet(){
        OkHttpUtils.post()
                .url("https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add?access_token="+access_token)
                .addParams("uid", "user"+new Random().nextInt(100)+1)
                .addParams("user_info",user_info.getText().toString())
                .addParams("group_id","AnJiaSmartLock_Test_1")
                .addParams("image",imgStr)
                .build()
                .execute(new MyStringCallBack());
    }

    public class MyStringCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(mContext, "联网失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if (response!=null){
                processData(response);
                if (error_msg!=null){
                    Toast.makeText(mContext, "上传失败:"+error_code+";"+error_msg, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "上传成功:"+result_id, Toast.LENGTH_SHORT).show();
                    mFilePath=Environment.getExternalStorageDirectory().getPath();
                    cam.setImageBitmap(null);
                    register_info_view.setVisibility(View.GONE);
                }
            }

        }
    }

    private void processData(String json){
        Gson gson=new Gson();
        HomeBean homebean=gson.fromJson(json,HomeBean.class);
        result_id=homebean.getLog_id();
        error_code=homebean.getError_code();
        error_msg=homebean.getError_msg();
    }

    private void getTokenFromNet(String ak, String sk){
        OkHttpUtils
                .post()
                .url(Constants.authHost)
                .addParams("grant_type","client_credentials")
                .addParams("client_id",ak)
                .addParams("client_secret",sk)
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
                    //Toast.makeText(mContext,access_token,Toast.LENGTH_LONG).show();
                    CacheUtils.putString(mContext,"token",access_token);
                }
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
