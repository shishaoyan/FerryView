package com.ssy.ferryview.progressview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ssy.ferryview.R;

public class ProcessViewActivity extends AppCompatActivity {

    private static final String TAG = ProcessViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_view);
        FerryProgressView ferryProgressView=   findViewById(R.id.progress_view);
        ferryProgressView.setOnProcessListener(new FerryProgressView.ProcessListener() {
            @Override
            public void updatePorcess(int process) {
                Log.e(TAG,"process:"+process);
            }
        });
    }
}