package com.ssy.ferryview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ssy.ferryview.flowlayout.FlowLayoutActivity;
import com.ssy.ferryview.loadingview.LoadingActivity;
import com.ssy.ferryview.progressview.ProcessViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_process).setOnClickListener(this);
        findViewById(R.id.btn_flowlayout).setOnClickListener(this);
        findViewById(R.id.btn_loading).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_process:
                Intent intent = new Intent(MainActivity.this, ProcessViewActivity.class);
                startActivity(intent);
                break;
                case R.id.btn_flowlayout:
                Intent intent1 = new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(intent1);
                break;
                case R.id.btn_loading:
                Intent intent2 = new Intent(MainActivity.this, LoadingActivity.class);
                startActivity(intent2);
                break;
        }
    }
}