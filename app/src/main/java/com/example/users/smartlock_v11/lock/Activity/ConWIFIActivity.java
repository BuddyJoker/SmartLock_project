package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2018/2/26.
 */

public class ConWIFIActivity extends Activity {

    @Bind(R.id.bond_con)
    TextView bond_con;
    @Bind(R.id.send_wifi)
    Button send_wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_wifi);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        send_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConWIFIActivity.this,BondDevActivity.class));
                finish();
            }
        });
        bond_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConWIFIActivity.this,NewDeviceActivity.class));
                finish();
            }
        });
    }

    private String getConnectWIFI(){
        WifiManager wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
}
