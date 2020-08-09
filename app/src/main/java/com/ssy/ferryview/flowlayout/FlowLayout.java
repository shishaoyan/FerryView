package com.ssy.ferryview.flowlayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    private Context context;
    private SparseArray<Rect> rects;
    private OnItemListener onItemListener;
    int index = -1;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rects = new SparseArray<>();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int measureWidth = 0;
        int measureHeight = 0;

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int allChildWidth = 0;
        int allChildHeight = 0;
        int maxChildWidth = 0;
        int maxChildHeight = 0;

        MarginLayoutParams marginLayoutParams;
        for (int i = 0; i < getChildCount(); i++) {

            int childLeft = 0;
            int childTop = 0;
            View childView = getChildAt(i);

            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + childView.getMeasuredWidth();
            int childHeight = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + childView.getMeasuredHeight();

            if (i == 0) {
                allChildHeight += childHeight;
                maxChildHeight = Math.max(allChildHeight, maxChildHeight);
            }
            if (allChildWidth + childWidth > sizeWidth - getPaddingRight() - getPaddingLeft()) {
                maxChildWidth = Math.max(maxChildWidth, allChildWidth);
                allChildWidth = 0;
                allChildHeight += childHeight;
                maxChildHeight = Math.max(allChildHeight, maxChildHeight);
            }

            childLeft = allChildWidth + marginLayoutParams.leftMargin;
            allChildWidth += childWidth;
            maxChildWidth = Math.max(maxChildWidth, allChildWidth);

            childTop = allChildHeight - childHeight + marginLayoutParams.topMargin;


            rects.put(i, new Rect(childLeft, childTop, childLeft + childView.getMeasuredWidth(), childTop + childView.getMeasuredHeight()));
        }

        setMeasuredDimension(maxChildWidth, maxChildHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                index = touchInIndex(event);
                break;
            case MotionEvent.ACTION_UP:
                if (onItemListener != null && index != -1) {
                    onItemListener.onItemClick(index);
                }
                index = -1;
                break;
        }


        return true;
    }

    private int touchInIndex(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        for (int i = 0; i < rects.size(); i++) {
            Rect rect = rects.get(i);
            if (x > rect.left && x < rect.right && y > rect.top && y < rect.bottom) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect rect = rects.get(i);
            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }

    }

    public interface OnItemListener {
        void onItemClick(int index);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

}
