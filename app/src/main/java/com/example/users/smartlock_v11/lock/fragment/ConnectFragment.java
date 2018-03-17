package com.example.users.smartlock_v11.lock.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.Activity.NewDeviceActivity;
import com.example.users.smartlock_v11.utils.BluetoothService;

import java.util.Set;

public class ConnectFragment extends Fragment {
    public static final String PREFS_NAME = "com.patricia.bluetoothremote.settings";

    private Button mScanButton;
    private ListView mDeviceList;
    private Button mConnectButton;
    private ArrayAdapter<String> mDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private BluetoothDevice mSelectedDevice;
    private Button mReconnectButton;
    private String mLastDevice = null;

    public ConnectFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ConnectFragment newInstance() {
        ConnectFragment fragment = new ConnectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
        mScanButton = (Button) rootView.findViewById(R.id.btnScan);//
        mDeviceListAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_device);
        mDeviceList = (ListView) rootView.findViewById(R.id.lsvDevices);//
        mDeviceList.setAdapter(mDeviceListAdapter);
        mConnectButton = (Button) rootView.findViewById(R.id.connect_button);//
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mReconnectButton = (Button) rootView.findViewById(R.id.reconnectbutton);//

        //扫描设备
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectButton.setVisibility(View.GONE);
                mDeviceListAdapter.clear();


                mPairedDevices = mBluetoothAdapter.getBondedDevices();//获取已经绑定好的设备
                //怎么能让它获取所有可以扫描到的设备列表呢？
                ;//获取已经绑定好的设备
                mBluetoothAdapter.setName("test");


                //
                if (mPairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : mPairedDevices) {
                        String deviceName = device.getName();
                        mDeviceListAdapter.add(deviceName);
                    }
                }
            }
        });

        //点击列表项选择设备时触发
        mDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String deviceName = ((TextView)view).getText().toString();
                    mConnectButton.setText("Connect " + deviceName);//buttton的文本改变
                    mConnectButton.setVisibility(View.VISIBLE);
                    for (BluetoothDevice device : mPairedDevices) {
                        if (device.getName().equalsIgnoreCase(deviceName)) {
                            mSelectedDevice = device;//将选定的设备赋值
                        }
                    }
            }
        });

        //点击连接时触发
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDevice(mSelectedDevice);
                SharedPreferences.Editor editor = NewDeviceActivity.sharedPreferences.edit();
                editor.putString("lastConnectedDevice", mSelectedDevice.getName());
                editor.commit();
            }
        });

        String lastDevice = NewDeviceActivity.sharedPreferences.getString("lastConnectedDevice", null);
        if (lastDevice != null) {
            mReconnectButton.setText("Reconnect " + lastDevice);
            mLastDevice = lastDevice;
            mReconnectButton.setEnabled(true);
        } else  {
            mReconnectButton.setEnabled(false);
        }
        mReconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reconnect();
            }
        });
        return rootView;
    }

    private void reconnect() {
        if (mLastDevice != null) {
            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : devices) {
                if (device.getName().equalsIgnoreCase(mLastDevice)) {
                    connectToDevice(device);
                    break;
                }
            }
        } else  {
            mReconnectButton.setEnabled(false);
        }
    }

    //用于将连接到指定的蓝牙设备
    private void connectToDevice(BluetoothDevice device) {
        BluetoothService bluetooth = BluetoothService.getInstance();
        bluetooth.connectToDevice(device);
    }
}