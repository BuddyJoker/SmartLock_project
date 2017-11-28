package com.example.users.smartlock_v11.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.users.smartlock_v11.R;

/**
 * Created by BigBoss on 2017/11/28.
 */

public class CustomProgressDialog extends Dialog {
    Context context;
    private ImageView spaceshipImage;
    private Animation hyperspaceJumpAnimation;
    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        spaceshipImage = (ImageView) v.findViewById(R.id.img);
        // 加载动画
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        setCancelable(false);// 不可以用“返回键”取消
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }
}
