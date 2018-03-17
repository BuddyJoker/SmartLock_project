package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.utils.BluetoothService;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static com.example.users.smartlock_v11.app.MyAppliction.getContext;

/**
 * Created by hp on 2018/3/13.
 */

public class BluetoothActivity extends Activity implements AdapterView.OnItemSelectedListener{

    public static final String PREFS_NAME = "com.patricia.bluetoothremote.settings";
    public static SharedPreferences sharedPreferences;

    @Bind(R.id.bluetooth_cancel)
    TextView bluetooth_cancel;
    @Bind(R.id.list_down)
    Spinner list_down;
    @Bind(R.id.send_blue)
    Button send_blue;

    private ArrayAdapter<String> mDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private BluetoothDevice mSelectedDevice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        initData();
        initListener();

        list_down.setOnItemSelectedListener(this);
    }

    private void initData() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        mDeviceListAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_device);
        list_down.setAdapter(mDeviceListAdapter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mDeviceListAdapter.clear();



        mPairedDevices = mBluetoothAdapter.getBondedDevices();//获取已经绑定好的设备

        if (mPairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : mPairedDevices) {
                String deviceName = device.getName();
                mDeviceListAdapter.add(deviceName);
            }
        }

    }
    //发送扫描蓝牙广播
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            // When discovery finds a device
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Get the BluetoothDevice object from the Intent
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // Add the name and address to an array adapter to show in a ListView
//                //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        }
//    };
//    // Register the BroadcastReceiver
//    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    //registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    private void initListener() {
        send_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToDevice(mSelectedDevice);
                SharedPreferences.Editor editor = BluetoothActivity.sharedPreferences.edit();
                editor.putString("lastConnectedDevice", mSelectedDevice.getName());
                editor.commit();
                if (editor.commit()){
                    startActivity(new Intent(BluetoothActivity.this,ConWIFIActivity.class));
                }
            }
        });
    }

    //用于将连接到指定的蓝牙设备
    private void connectToDevice(BluetoothDevice device) {
        BluetoothService bluetooth = BluetoothService.getInstance();
        bluetooth.connectToDevice(device);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String deviceName=mDeviceListAdapter.getItem(i);   //获取选中的那一项
        for (BluetoothDevice device : mPairedDevices) {
            if (device.getName().equalsIgnoreCase(deviceName)) {
                mSelectedDevice = device;//将选定的设备赋值
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
