package com.ssy.ferryview.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ssy.ferryview.R;
import com.ssy.ferryview.Util;

import androidx.annotation.Nullable;

public class FerryLoadingView extends View {
    private Paint paint;
    private ValueAnimator valueAnimator;
    private float cur, start;
    private int LoadingWidth;
    private int LoadingHeight;
    private int x, y, radio;
    private boolean isFirst = true;
    private float threshold = 0.5f;
    private Bitmap arrawBitmap;

    public FerryLoadingView(Context context) {
        this(context, null);
    }

    public FerryLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeWidth(Util.dip2px(context, 5));
        paint.setColor(0xff00d6ff);
        paint.setStyle(Paint.Style.STROKE);
        arrawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arraw);
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        LoadingWidth = Util.dip2px(context, 100);
        LoadingHeight = Util.dip2px(context, 100);
        x = LoadingWidth / 2;
        y = LoadingHeight / 2;
        radio = LoadingHeight / 3;
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                cur = (float) animation.getAnimatedValue();
                if (isFirst&&cur<threshold) {
                    isFirst = false;
                }else {
                    if (cur<=1&&cur>=threshold){
                        start = cur-(1-cur);
                    }else {
                        start = cur-threshold;
                    }

                }
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path trianglePath = new Path();
        Path path = new Path();
        Path clipPath = new Path();
        path.addCircle(x, y, radio, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(path, true);
        pathMeasure.getSegment( (float) (start * pathMeasure.getLength()), (float) (cur * pathMeasure.getLength()), clipPath, true);
        canvas.drawPath(clipPath, paint);

        float[] pos= new float[2];
        float[] tans= new float[2];
        pathMeasure.getPosTan((float) (cur * pathMeasure.getLength()),pos,tans);
        Matrix matrix = new Matrix();
        float degress = (float) (Math.atan2(tans[1],tans[0])*180/Math.PI)-90;
        matrix.setRotate(degress,arrawBitmap.getWidth()/2,arrawBitmap.getHeight()/2);
        matrix.postTranslate(pos[0]-arrawBitmap.getWidth()/2,pos[1]-arrawBitmap.getHeight()/2);
        canvas.drawBitmap(arrawBitmap,matrix,paint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
