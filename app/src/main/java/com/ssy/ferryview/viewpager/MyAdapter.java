package com.ssy.ferryview.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssy.ferryview.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyAdapter extends PagerAdapter {


private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.e("haha","instantiateItem:"+position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager,container,false);
        if (position%2==0){
            view.findViewById(R.id.view).setBackgroundColor(Color.RED);
        }else {
            view.findViewById(R.id.view).setBackgroundColor(Color.BLUE);
        }
        ((TextView)view.findViewById(R.id.view)).setText(position+"");
        ((TextView)view.findViewById(R.id.view)).setTextSize(50);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
