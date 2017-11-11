package com.example.users.smartlock_v11.lock.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.fragment.ContentFragment;

/**
 * Created by BigBoss on 2017/8/19.
 */

public class ContentActivity extends FragmentActivity {

    private ContentFragment contentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        FragmentManager fm=getSupportFragmentManager();
        contentFragment=(ContentFragment)fm.findFragmentById(R.id.fragment_lock);

        if (contentFragment==null){
            String title=getIntent().getStringExtra(ContentFragment.ARGUMENT);
            contentFragment=ContentFragment.newInstance(title);
            fm.beginTransaction().add(R.id.fragment_lock,contentFragment).commit();
        }

    }
}
