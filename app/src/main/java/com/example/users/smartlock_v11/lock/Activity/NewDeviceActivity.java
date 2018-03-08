package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.fragment.CommandsFragment;
import com.example.users.smartlock_v11.lock.fragment.ConnectFragment;
import com.example.users.smartlock_v11.utils.BluetoothService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2017/12/5.
 */

public class NewDeviceActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "com.patricia.bluetoothremote.settings";
    private static final String TAG = "MainAcivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static SharedPreferences sharedPreferences;

    public Context mContext;

    @Bind(R.id.iv_return_icon)
    ImageView iv_return_icon;
    @Bind(R.id.device_bind)
    Button device_bind;
    @Bind(R.id.bind)
    LinearLayout bind_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newdevice);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        BluetoothService bs = BluetoothService.getInstance();
        bs.registerNewHandlerCallback(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    Log.d(TAG, "got a notification in " + Thread.currentThread());
                    String toasttext = "";
                    if (msg.what == BluetoothService.MessageConstants.MESSAGE_READ){
                        toasttext = "Reading from device: ";
                    }
                    else if (msg.what == BluetoothService.MessageConstants.MESSAGE_TOAST) {
                        toasttext = "Info: ";
                    }
                    else if (msg.what == BluetoothService.MessageConstants.MESSAGE_WRITE) {
                        toasttext = "Sending to device: ";
                    }
                    toasttext += msg.obj.toString();

                    Toast.makeText(getApplicationContext(), toasttext, Toast.LENGTH_LONG).show();
                } catch (Throwable t) {
                    Log.e(TAG,null, t);
                }

                return false;
            }
        });

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        ButterKnife.bind(this);
        mContext=NewDeviceActivity.this;
        initListener();
    }

    private void initListener() {
        iv_return_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewDeviceActivity.this.finish();
            }
        });

        device_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                device_bind.setVisibility(View.GONE);
//                bind_view.setVisibility(View.VISIBLE);
                startActivity(new Intent(NewDeviceActivity.this,ConWIFIActivity.class));
                finish();
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ConnectFragment (defined as a static inner class below).
            switch(position) {
                case 0:
                    return ConnectFragment.newInstance();
                case 1:
                default:
                    return CommandsFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Connect";
                case 1:
                    return "Commands";
            }
            return null;
        }
    }
}
