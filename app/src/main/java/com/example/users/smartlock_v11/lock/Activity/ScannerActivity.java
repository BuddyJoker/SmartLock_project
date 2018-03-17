package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by hp on 2018/3/17.
 */

public class ScannerActivity extends Activity implements QRCodeView.Delegate {

    @Bind(R.id.zxingview)
    QRCodeView zxingview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        ButterKnife.bind(this);
        zxingview.setDelegate(this);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
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
