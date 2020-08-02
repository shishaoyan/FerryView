package com.ssy.ferryview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CicleView extends View {

    int left;
    int right;
    int bottom;
    int top;
    Paint paint;

    int rX;
    int rY;
    int r;
    Point p6 = new Point();
    Point p5 = new Point();
    Point p4 = new Point();
    Point p3 = new Point();
    public CicleView(Context context) {
        this(context,null);
    }

    public CicleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint= new Paint();
        paint.setColor(0xff000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        left = 0;
        right = 500;
        top = 0;
        bottom = 500;

        r = (right-left)/2;
        rX = right -r;
        rY = bottom -r;

        p3.x = right;
        p3.y = rY;

        p4.x = right;
        p4.y = rY-r/2;

        p5.x = rX+r/2;
        p5.y = top;

        p6.x = rX;
        p6.y = top;



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(100,100);
        //先画坐标
        canvas.drawLine(left,rY,right,rY,paint);
        canvas.drawLine(rX,top,rX,bottom,paint);

        Path path = new Path();
        path.moveTo(p6.x,p6.y);
        path.cubicTo(p5.x,p5.y,p4.x,p4.y,p3.x,p3.y);
        canvas.drawPath(path,paint);



    }

    public void setProcess(int progress) {
        p3.x = right+r/50*progress;
        p4.x = right+r/50*progress;
        int newR =  p4.x-rX;
        p5.x = p4.x-newR/2;
        invalidate();
    }
}
