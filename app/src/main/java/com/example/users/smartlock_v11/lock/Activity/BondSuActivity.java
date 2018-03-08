package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.app.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2018/2/26.
 */

public class BondSuActivity extends Activity {

    @Bind(R.id.bond_finish)
    Button bond_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_sf);
        ButterKnife.bind(this);
        bond_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BondSuActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
