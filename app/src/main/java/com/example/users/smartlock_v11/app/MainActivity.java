package com.example.users.smartlock_v11.app;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.base.BaseFragment;
import com.example.users.smartlock_v11.home.fragment.HomeFragment;
import com.example.users.smartlock_v11.lock.fragment.LockFragment;
import com.example.users.smartlock_v11.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_1)
    RadioButton rbOther;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rg_main;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private LockFragment lockFragment;
    private BaseFragment mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments=new ArrayList<>();
        lockFragment=new LockFragment();
        fragments.add(new HomeFragment());
        fragments.add(lockFragment);
        fragments.add(new UserFragment());
    }


    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home:
                        position=0;
                        break;

                    case R.id.rb_1:
                        position=1;
                        break;

                    case R.id.rb_user:
                        position=2;
                        break;
                }

                BaseFragment baseFragment=getFragment(position);
                switchFragment(mContext,baseFragment);
            }
        });
        rg_main.check(R.id.rb_home);
    }

    private void switchFragment(Fragment fromFragment,BaseFragment nextFragment) {
        if (mContext!=nextFragment){
            mContext=nextFragment;
            if (nextFragment!=null){
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

                if (!nextFragment.isAdded()){
                    if (fromFragment!=null){
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else{
                    if (fromFragment!=null){
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private BaseFragment getFragment(int position){
        if (fragments!=null&&fragments.size()>0){
            BaseFragment baseFragment=fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    /**
     * 点击两次退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    private long mExitTime;
    public void exit() {
        if ((System.currentTimeMillis()-mExitTime)>2000){
            Toast.makeText(MainActivity.this,"又要学习去了？",Toast.LENGTH_SHORT).show();
            mExitTime=System.currentTimeMillis();
        }else{
            finish();
            System.exit(0);
        }
    }

}
