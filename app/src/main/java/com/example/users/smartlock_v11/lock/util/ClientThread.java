package com.example.users.smartlock_v11.lock.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by BigBoss on 2017/10/26.
 */

public class ClientThread implements Runnable {
    private Socket socket;
    private Handler handler;
    public Handler reHandler;
    OutputStream os=null;
    BufferedReader br=null;
    public ClientThread(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run() {
        try {
            socket=new Socket("192.168.1.104",9999);
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os=socket.getOutputStream();
            String flag="iphone";
            os.write(flag.getBytes());
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    String content=null;
                    try {
                        while ((content=br.readLine())!=null){
                            Message msg=Message.obtain();
                            msg.what=123;
                            msg.obj=content;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            Looper.prepare();

            reHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what==456){
                        try {
                            if (!socket.equals(null)){
                                os.write((msg.obj.toString()+"\r\n").getBytes());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
