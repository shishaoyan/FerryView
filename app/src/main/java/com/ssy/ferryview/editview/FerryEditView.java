package com.ssy.ferryview.editview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ssy.ferryview.Util;

import androidx.annotation.Nullable;

public class FerryEditView extends View {
    private Paint paint;
    private Paint textPaint;
    private int tvWidth, tvHeight;
    private int baseLine;
    private Paint.FontMetrics fontMetrics;
    private boolean isShow = true;


    public FerryEditView(Context context) {
        this(context, null);
    }

    public FerryEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        textPaint = new Paint();

        paint.setColor(0xff00d6ff);
        paint.setStrokeWidth(10);

        textPaint.setColor(0xff00d6ff);
        textPaint.setTextSize(200);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        baseLine = Util.dip2px(context, 100);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        tvWidth = w;
        tvHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String str = "asdfghj";
        canvas.drawText(str, 0, baseLine, textPaint);
        fontMetrics = textPaint.getFontMetrics();
        Bitmap b = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(b);
        canvas1.drawLine(0, baseLine + fontMetrics.ascent, tvWidth, baseLine + fontMetrics.ascent, paint);
        canvas1.drawLine(0, baseLine + fontMetrics.top, tvWidth, baseLine + fontMetrics.top, paint);
        canvas1.drawLine(0, baseLine, tvWidth, baseLine, paint);
        canvas1.drawLine(0, baseLine + fontMetrics.descent, tvWidth, baseLine + fontMetrics.descent, paint);
        canvas1.drawLine(0, baseLine + fontMetrics.bottom, tvWidth, baseLine + fontMetrics.bottom, paint);
        int indexWidth = measureTextX(str, str.length() - 1);
        if (isShow){
            canvas1.drawLine(indexWidth, baseLine + fontMetrics.top, indexWidth, baseLine + fontMetrics.bottom, paint);
        }
        canvas.drawBitmap(b,0,0,paint);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isShow = !isShow;
                postInvalidate();
            }
        }, 500);

    }

    private int measureTextX(String str, int index) {
        int indexWidth = 0;
        for (int i = 0; i < str.length(); i++) {
            indexWidth += textPaint.measureText(str.charAt(i) + "");
            if (index == i) {
                return indexWidth;
            }
        }
        return indexWidth;
    }
}
