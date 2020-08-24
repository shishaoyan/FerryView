package com.ssy.ferryview.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ssy.ferryview.R;
import com.ssy.ferryview.Util;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ViewPagerActivity extends AppCompatActivity {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.7f;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

         viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(-Util.dip2px(this,40));
//        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
//        layoutParams.width = Util.dip2px(this,400);
//        viewPager.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyAdapter(this));
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void transformPage(@NonNull View view, float position) {
                if (position < -1) {
                    view.setScaleX(MIN_SCALE);
                    view.setScaleY(MIN_SCALE);
                } else if (position <= 1) {
                    float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                    view.setScaleX(scaleFactor);
                    if (position > 0) {
                        view.setTranslationX(-scaleFactor * 2);
                    } else if (position < 0) {
                        view.setTranslationX(scaleFactor * 2);
                    }
                    view.setScaleY(scaleFactor);
                } else {
                    view.setScaleX(MIN_SCALE);
                    view.setScaleY(MIN_SCALE);
                }
                if (position <=-0.3) {
                    view.setTranslationZ(position);
                }
                if (position >=-0.3) {
                    view.setTranslationZ(-position);
                }

            }
        });
    }
}