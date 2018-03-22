package com.example.users.smartlock_v11.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.login.LoginActivity;
import com.example.users.smartlock_v11.utils.PermissionUtils;

import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by BigBoss on 2017/10/30.
 */

public class SplashActivity extends Activity{

    private AlertDialog dialog;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageView showView = (ImageView) findViewById(R.id.splash_icon);

        dialog = new AlertDialog.Builder(this)
                .setTitle("权限相关")
                .setMessage("请开启应用所必需的存储读取、相机权限")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog1 = new AlertDialog.Builder(SplashActivity.this)
                                .setTitle("权限相关")
                                .setMessage("请务必开启相关权限，否则无法使用")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create();
                        dialog1.setCanceledOnTouchOutside(false);
                        dialog1.show();

                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转权限设置页面
                        toGetAppDetail(SplashActivity.this);
                        finish();
                    }
                })
                .create();


        //显示动画
        showView.animate()
                .alpha(1)
                .setDuration(2000)
                .setListener(null);

        //
        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                //每次进入前判断是否获取了必须的权限
                PermissionUtils.checkAndRequestPermissions(SplashActivity.this, new PermissionUtils.PermissionCallbacks() {
                    @Override
                    public void onPermissionsGranted() {
                        toMainActivity();
                    }

                    @Override
                    public void onPermissionsDenied(int requestCode, List<String> perms) {

                    }
                });
                //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        }.execute(new Void[]{});
    }

    //判断权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_STATUS_CODE) {

            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {//读权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//已获得该权限
                    PermissionUtils.checkAndRequestPermissions(this, new PermissionUtils.PermissionCallbacks() {
                        @Override
                        public void onPermissionsGranted() {
                            toMainActivity();
                        }

                        @Override
                        public void onPermissionsDenied(int requestCode, List<String> perms) {

                        }


                    });//请求
                } else {//未获取该权限-提示信息
                    Toast.makeText(SplashActivity.this,"请开启读权限",Toast.LENGTH_LONG).show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    setDialog();
                }
            }

            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//写权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//已获得该权限
                    PermissionUtils.checkAndRequestPermissions(this, new PermissionUtils.PermissionCallbacks() {
                        @Override
                        public void onPermissionsGranted() {
                            toMainActivity();
                        }

                        @Override
                        public void onPermissionsDenied(int requestCode, List<String> perms) {

                        }


                    });//请求
                } else {//未获取该权限-提示信息
                    Toast.makeText(SplashActivity.this,"请开启写权限",Toast.LENGTH_LONG).show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    setDialog();
                }
            }


            if (permissions[0].equals(Manifest.permission.CAMERA)) {//相机权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//已获得该权限
                    //所有权限均获取
                    toMainActivity();
                } else {//未获取该权限-提示信息
                    Toast.makeText(SplashActivity.this,"请开启相机权限",Toast.LENGTH_LONG).show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    setDialog();
                }
            }
        }
    }



    private void setDialog(){
        // 在dialog执行show之后才能来设置
        TextView tvMsg = (TextView) dialog.findViewById(android.R.id.message);
        assert tvMsg != null;
        tvMsg.setTextSize(16);
        tvMsg.setTextColor(Color.parseColor("#4E4E4E"));

        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextSize(16);
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8C8C8C"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DA6DD"));

        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object alertController = mAlert.get(dialog);

            Field mTitleView = alertController.getClass().getDeclaredField("mTitleView");
            mTitleView.setAccessible(true);

            TextView tvTitle = (TextView) mTitleView.get(alertController);
            if (null != tvTitle) {
                tvTitle.setTextSize(16);
                tvTitle.setTextColor(Color.parseColor("#000000"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转权限管理
     * 机型适配：
     * 可直接跳转设置：小米、三星；
     * vivo、oppo不可用；
     * 其他：未知
     * @param context
     */
    private void toGetAppDetail(Context context){

        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if(appIntent != null){
            context.startActivity(appIntent);
            //floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 0);
            //floatingView.createFloatingView();
            return;
        }

        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
        appIntent = context.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        if(appIntent != null){
            context.startActivity(appIntent);
            //floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 1);
            //floatingView.createFloatingView();
            return;
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }

    private void toMainActivity() {
        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
