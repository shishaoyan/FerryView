package com.ssy.ferryview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawCubicView extends View {

    private int left;
    private int right;
    private int top;
    private int bottom;
    private int r = 10;
    Paint paint;

    int curX;
    int curY;

    public DrawCubicView(Context context) {
        this(context, null);
    }

    public DrawCubicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStyle(Paint.Style.STROKE );
        left = 100;
        right = 500;
        top = 300;
        bottom = 600;
        curX = right;
        curY = top;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path  path= new Path();
        //先画四个圆
        canvas.drawCircle(left,top,r,paint);
        canvas.drawCircle(left,bottom,r,paint);
        canvas.drawCircle(right,top,r,paint);
        canvas.drawCircle(right,top,r,paint);

        //开始连线
        canvas.drawLine(left,top,left,bottom,paint);
        canvas.drawLine(left,top,curX,curY,paint);
        canvas.drawLine(right,bottom,curX,curY,paint);

        //画贝塞尔曲线
        path.moveTo(left,bottom);
        path.cubicTo(left,top,curX,curY,right,bottom);
        canvas.drawPath(path,paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                curX = (int) event.getX();
                curY = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
