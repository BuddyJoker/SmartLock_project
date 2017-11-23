package com.example.users.smartlock_v11.lock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.base.BaseFragment;
import com.example.users.smartlock_v11.lock.Activity.ContentActivity;
import com.example.users.smartlock_v11.lock.adapter.LockAdapter;
import com.example.users.smartlock_v11.lock.bean.LockBean;
import com.example.users.smartlock_v11.lock.util.ClientThread;
import com.example.users.smartlock_v11.utils.Constants;
import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientAddress;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketPacket;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;
import com.vilyever.socketclient.util.IPUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by BigBoss on 2017/8/9.
 */

public class LockFragment extends BaseFragment{

    private SocketClient socketClient;
    //"192.168.171.179",8888
    public static final int REQUEST_DETAIL=0x110;

    private int result_num;
    private int log_id;
    private List<LockBean.ResultBean> resultBeen;
    private List<LockBean> setResultBeen;
    private LockAdapter lockAdapter;

    private String test="test_data";

    Handler handler;
    ClientThread clientThread;
    @Bind(R.id.list_device)
    RecyclerView mRecyclerView;
    @Bind(R.id.btnn)
    Button bn;
    @Bind(R.id.iv_add_icon)
    ImageView iv;

    //网络数据
    String data_json="{\"log_id\":73473737,"+
            "\"result_num\":2,"+
            "\"result\":[{\"ip\":0,\"port\":8866,\"mac\":123.45},{\"ip\":0,\"port\":2333,\"mac\":456.12}]}";


    @Override
    public View initView() {
        View view=View.inflate(mContext, R.layout.fragment_lock,null);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void initData() {
        //接受TCP链接的数据
//        handler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what==123){
//                    Toast.makeText(mContext,msg.obj.toString()+"",Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        clientThread=new ClientThread(handler);
//        new Thread(clientThread).start();

        //未经过网络的固定数据
        processTCPData(data_json);
        lockAdapter=new LockAdapter(mContext,resultBeen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(lockAdapter);
        initListener();
        //getDataFromNet();
    }

    private void processTCPData(String json){
        Gson gson=new Gson();
        LockBean lockBean=gson.fromJson(json,LockBean.class);
        resultBeen=lockBean.getResult();
        result_num=lockBean.getResult_num();
        log_id=lockBean.getLog_id();
    }

    public void TCPNet() throws JSONException{
//        OkHttpUtils.postString()
//                .url(Constants.ACCESSORY_URL)
//                .content(new Gson().toJson())   将json字符串作为请求体传入服务器
        Log.e("方法提示","内部");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name", test.toString().trim());
        jsonObject.put("age", 1);
        final String  result=jsonObject.toString();
        Log.i("jSON字符串", result);

        /**
         *
         */
//        Gson gson=new Gson();
//        LockBean lockBean=new LockBean();
//        lockBean.setLog_id(666);
//        lockBean.setResult_num(2);
//        HashMap<String,String> mac_map=new HashMap<String,String>();
//        LockBean.ResultBean lr=resultBeen.get();
//        lr.setMac(44.3);
//        lr.setPort(8866);
//        String data=gson.toJson(lockBean);


        Message msg=Message.obtain();
        msg.what=456;
        msg.obj="unlock";
        if (clientThread.reHandler!=null){
            clientThread.reHandler.sendMessage(msg);
        }else{
            Toast.makeText(mContext,"未与目标设备连接",Toast.LENGTH_SHORT).show();
        }


        /**
         * socketClient 凉了凉了
         */
//        socketClient=new SocketClient();
//        socketClient.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.Manually);
//        socketClient.getAddress().setRemoteIP(IPUtil.getLocalIPAddress(true));
//        socketClient.getAddress().setRemotePort("8888");
//        socketClient.setCharsetName(CharsetUtil.UTF_8);

    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            //Log.e("TAG", "联网失败" + e.getMessage());
            Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        //json解析并抽取数据
                        processData(response);
                        //设置适配器并显示数据
                        if (log_id==1000){
                            Toast.makeText(mContext,"开锁成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext,"开锁失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 101:
                    break;
            }
        }
    }

    private void processData(String json) {
        Gson gson=new Gson();
        //数据bean抽取json数据
        LockBean lockBean=gson.fromJson(json,LockBean.class);
        //返回result数据集合
        log_id=lockBean.getLog_id();
    }

    private void initListener(){

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //http链接
                sendControlData();
                //TCP链接
//                try {
//                    TCPNet();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("提示","链接");

            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"添加设备",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendControlData() {
        OkHttpUtils.postString()
                .url("")
                .content(data_json)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new MyStringCallback());
    }

}
