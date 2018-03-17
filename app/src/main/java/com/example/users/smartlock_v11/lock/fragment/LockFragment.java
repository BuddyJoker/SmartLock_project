package com.example.users.smartlock_v11.lock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.base.BaseFragment;
import com.example.users.smartlock_v11.lock.Activity.ContentActivity;
import com.example.users.smartlock_v11.lock.Activity.NewDeviceActivity;
import com.example.users.smartlock_v11.lock.adapter.LockAdapter;
import com.example.users.smartlock_v11.lock.bean.LockBean;
import com.example.users.smartlock_v11.lock.util.ClientThread;
import com.example.users.smartlock_v11.lock.util.TopMiddlePopup;
import com.example.users.smartlock_v11.utils.CacheUtils;
import com.example.users.smartlock_v11.utils.Constants;
import com.example.users.smartlock_v11.utils.CustomProgressDialog;
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
import butterknife.BindInt;
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
    private String log_id;
    private List<LockBean.ResultBean> resultBeen;
    private List<LockBean.SubserverInfoBean> subserverInfoBeans;
    private List<LockBean> setResultBeen;
    private LockAdapter lockAdapter;

    private String test="test_data";

    private String access_token;
    private String Error_code;
    private static final String hard_Id="ae20ea77-ad6b-4029-b820-acfe0954e8ac";
    private static final String DEV_IP="192.168.1.1";

    private CustomProgressDialog mProgressDialog;

    private TopMiddlePopup middlePopup;
    public static int screenW, screenH;
    private ArrayList<String> items;

    Handler handler;
    ClientThread clientThread;
    @Bind(R.id.list_device)
    RecyclerView mRecyclerView;
    @Bind(R.id.btnn)
    Button bn;
    @Bind(R.id.iv_add_icon)
    ImageView iv;
    @Bind(R.id.more_device)
    ImageSwitcher more_device;
    @Bind(R.id.rule_line_tv)
    TextView top_line;

    //模拟网络数据
    String data_json="{\"log_id\":73473737,"+
            "\"result_num\":2,"+
            "\"result\":[{\"ip\":0,\"port\":8866,\"mac\":123.45}]}";


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
//        String username=CacheUtils.getString(mContext,"username");
//        if (username.equals("yuancong")){
//            processTCPData(data_json);
//            lockAdapter=new LockAdapter(mContext,subserverInfoBeans);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//            mRecyclerView.setAdapter(lockAdapter);
//        }else {
////            processTCPData(null);
//        }

        getDataFromNet();

        initListener();
        //getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.SUBSEVER_LIST)
                .addHeader("Authorization","Bearer "+CacheUtils.getString(mContext,"loginToken"))
                .build()
                .execute(new SubserverCallBack());
    }

    public class SubserverCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(mContext,"网络连接错误"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if (response!=null){
                processTCPData(response);
                lockAdapter=new LockAdapter(mContext,subserverInfoBeans);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.setAdapter(lockAdapter);
            }
        }
    }

    private void processTCPData(String json){
        Gson gson=new Gson();
        LockBean lockBean=gson.fromJson(json,LockBean.class);
        subserverInfoBeans=lockBean.getSubserverInfo();
        result_num=lockBean.getSubserverNum();
        log_id=lockBean.getSubserverId();
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
            mProgressDialog=new CustomProgressDialog(mContext,R.style.loading_dialog);
            mProgressDialog.show();
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            //Log.e("TAG", "联网失败" + e.getMessage());
            Toast.makeText(mContext,"网络连接失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response, int id) {
            mProgressDialog.dismiss();
            if (response != null) {
                //json解析并抽取数据
                processData(response);
                //设置适配器并显示数据
                switch (Error_code){
                    case "2000":
                        Toast.makeText(mContext,"锁已开启",Toast.LENGTH_SHORT).show();
                        break;
                    case "2001":
                        Toast.makeText(mContext,"Subserver offline",Toast.LENGTH_SHORT).show();
                        break;
                    case "2002":
                        Toast.makeText(mContext,"SubserverId包含危险字符",Toast.LENGTH_SHORT).show();
                        break;
                    case "2003":
                        Toast.makeText(mContext,"Token包含危险字符",Toast.LENGTH_SHORT).show();
                        break;
                    case "2004":
                        Toast.makeText(mContext,"Token无效",Toast.LENGTH_SHORT).show();
                        break;
                    case "2005":
                        Toast.makeText(mContext,"SubserverId不可用",Toast.LENGTH_SHORT).show();
                        break;
                    case "2006":
                        Toast.makeText(mContext,"DeviceIP不可用",Toast.LENGTH_SHORT).show();
                        break;
                    case "2007":
                        Toast.makeText(mContext,"Token对该子服务器无权限。也就是说你尝试用一个token操作一个不属于你的子服务器",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    private void processData(String json) {
        Gson gson=new Gson();
        //数据bean抽取json数据
        LockBean lockBean=gson.fromJson(json,LockBean.class);
        //返回result数据集合
        Error_code=lockBean.getError_code();
    }

    private void initListener(){

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CacheUtils.getString(mContext,"username").equals("yuancong")){
                    //http链接
                    access_token=CacheUtils.getString(mContext,"loginToken");
                    sendControlData();
                    //TCP链接
//                try {
//                    TCPNet();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("提示","链接");
                }else{
                    Toast.makeText(mContext,"无控制设备",Toast.LENGTH_SHORT).show();
                }


            }
        });

        //绑定设备
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, NewDeviceActivity.class));
            }
        });
        more_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPopup(2);
                middlePopup.show(top_line);
            }
        });
    }
    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(mContext, screenW, screenH,
                onItemClickListener, getItemsName(), type);
    }

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private ArrayList<String> getItemsName() {
        items = new ArrayList<String>();
        items.add("扫一扫");
        items.add("分享码");

        return items;
    }

    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(mContext,items.get(position),Toast.LENGTH_SHORT).show();
            middlePopup.dismiss();
        }
    };

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    private void sendControlData() {
        OkHttpUtils.postString()
                .url("http://www.writebug.site/api/lock")
                .content(processSendData(access_token,hard_Id,DEV_IP))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new MyStringCallback());
    }

    private String processSendData(String actoken,String Id,String deviceIP){
        Gson gson=new Gson();
        LockBean lockBean=new LockBean(actoken,Id,deviceIP);
        return gson.toJson(lockBean);
    }
}
