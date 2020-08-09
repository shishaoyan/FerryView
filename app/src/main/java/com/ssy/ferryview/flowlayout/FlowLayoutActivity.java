package com.ssy.ferryview.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssy.ferryview.R;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {
    private static final String TAG = FlowLayoutActivity.class.getSimpleName();
    private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        for (int i = 0; i <1; i++) {
            list.add("Android Android Android");
            list.add("Java");
            list.add("IOS");
            list.add("python");
            list.add("hahha");
            list.add("xxixiixi");
            list.add("1111");
            list.add("222");
            list.add("333");
            list.add("444");
            list.add("55");
        }
        FlowLayout flowLayout = findViewById(R.id.flowlayout);
        flowLayout.setOnItemListener(new FlowLayout.OnItemListener() {
            @Override
            public void onItemClick(int index) {
                Log.e(TAG,"index:"+index+":"+list.get(index));
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setBackgroundResource(R.drawable.btn_bg);
            tv.setLayoutParams(layoutParams);
            flowLayout.addView(tv, layoutParams);
        }
    }
}