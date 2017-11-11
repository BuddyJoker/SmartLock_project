package com.example.users.smartlock_v11.lock.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.example.users.smartlock_v11.base.BaseFragment;

import java.util.Random;

/**
 * Created by BigBoss on 2017/8/19.
 */

public class ContentFragment extends BaseFragment {
    private String mArgument;
    public static final String ARGUMENT="argument";
    public static final String RESPONSE="response";
    @Override
    public View initView() {
        Random random=new Random();
        TextView tv =new TextView(getActivity());
        tv.setText(mArgument);
        tv.setGravity(Gravity.CENTER);
//        tv.setBackgroundColor(Color.argb(random.nextInt(100),random.nextInt(255),
//                random.nextInt(255),random.nextInt(255)));

        return tv;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if (bundle!=null){
            mArgument=bundle.getString(ARGUMENT);
            Intent intent=new Intent();
            intent.putExtra(RESPONSE,"good");
            getActivity().setResult(LockFragment.REQUEST_DETAIL,intent);
        }
    }

    public static ContentFragment newInstance(String argument){
        Bundle bundle =new Bundle();
        bundle.putString(ARGUMENT,argument);
        ContentFragment contentFragment=new ContentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
}
