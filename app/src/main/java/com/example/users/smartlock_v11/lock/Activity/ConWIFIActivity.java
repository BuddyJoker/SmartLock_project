package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.utils.BluetoothService;

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
    @Bind(R.id.wifi_name)
    TextView wifi_name;
    @Bind(R.id.wifi_pass)
    EditText wifi_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_wifi);
        ButterKnife.bind(this);
        wifi_name.setText(getConnectWIFI());
        initListener();
    }

    private void initListener() {
        send_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifi_name.getText();
                wifi_pass.getText();
                String wifi_info=wifi_name+"@"+wifi_pass;
                //sendCommand(wifi_info);

                startActivity(new Intent(ConWIFIActivity.this,BondDevActivity.class));
                //finish();
            }
        });
        bond_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ConWIFIActivity.this,NewDeviceActivity.class));
                finish();
            }
        });
    }

    private String getConnectWIFI(){
        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

    private void sendCommand(String command) {
        BluetoothService bluetooth = BluetoothService.getInstance();
        boolean sent =  bluetooth.send(command);
    }
}
