package com.example.users.smartlock_v11.lock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2018/2/26.
 */

public class BondDevActivity extends Activity {

    @Bind(R.id.bond_start)
    Button bond_start;
    @Bind(R.id.bond_ret)
    TextView bond_ret;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_device);
        ButterKnife.bind(this);
        bond_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BondDevActivity.this,BondSuActivity.class));
                finish();
            }
        });
        bond_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BondDevActivity.this,ConWIFIActivity.class));
                finish();
            }
        });
    }
}
