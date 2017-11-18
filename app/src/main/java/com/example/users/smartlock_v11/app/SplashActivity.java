package com.example.users.smartlock_v11.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.login.LoginActivity;

import butterknife.Bind;

/**
 * Created by BigBoss on 2017/10/30.
 */

public class SplashActivity extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageView showView = (ImageView) findViewById(R.id.splash_icon);

        //icon.setVisibility(View.VISIBLE);
        showView.animate()
                .alpha(1)
                .setDuration(2000)
                .setListener(null);


        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        }.execute(new Void[]{});
    }
}
