package com.example.GEARVN.View.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.GEARVN.R;

public class ChartMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_menu);

        findViewById(R.id.cThongKeDoanhThu).setOnClickListener(this);
        findViewById(R.id.cThongKeDonHang).setOnClickListener(this);
        Init();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.cThongKeDonHang: startActivity(new Intent( ChartMenuActivity.this,ChartBillActivity.class));break;
            case R.id.cThongKeDoanhThu: startActivity(new Intent( ChartMenuActivity.this,StaticThongKe.class));break;
        }
    }

    private void Init() {

        findViewById(R.id.backadmin).setOnClickListener(view -> {
            finish();
        });


    }
}