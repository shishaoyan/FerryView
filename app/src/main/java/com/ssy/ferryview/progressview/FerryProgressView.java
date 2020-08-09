package com.ssy.ferryview.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ssy.ferryview.R;
import com.ssy.ferryview.Util;

import androidx.annotation.Nullable;

public class FerryProgressView extends View {
    private ProcessListener processListener;
    private float cicleWidth;
    private int defaultWidth;
    private int defaultHeight;
    private int mWidth, mHeight;
    private int cx, cy;
    private Paint textPaint;
    private Paint ciclePaint;
    private Paint miniCiclePaint;
    private Paint btnPaint;
    private Context context;
    private int radio;
    private int miniRadio;
    private float btnX, btnY;
    private double degrees;
    private double angle;
    private boolean isTouchOnArc;
    private int lastQuadrant;
    public FerryProgressView(Context context) {
        this(context, null);
    }

    public FerryProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FerryProgressView);
        cicleWidth = array.getDimension(R.styleable.FerryProgressView_cicleWidth,0);

        defaultWidth = Util.dip2px(context, 200);
        defaultHeight = Util.dip2px(context, 200);

        ciclePaint = new Paint();
        btnPaint = new Paint();
        textPaint = new Paint();
        miniCiclePaint = new Paint();
        miniCiclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        miniCiclePaint.setAntiAlias(true);
        ciclePaint.setColor(0xff657c91);

        ciclePaint.setStyle(Paint.Style.STROKE);
        ciclePaint.setShadowLayer(10, 5, 5, Color.GRAY);//阴影


        textPaint.setColor(0xff657c91);
        textPaint.setTextSize(Util.dip2px(this.context, 30));
        textPaint.setStyle(Paint.Style.STROKE);

        btnPaint.setAntiAlias(true);
        btnPaint.setColor(0xffffffff);
        btnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        btnPaint.setShadowLayer(10, 5, 5, Color.GRAY);//阴影

        miniCiclePaint.setColor(0xfffff700);
        miniCiclePaint.setStyle(Paint.Style.STROKE);
        miniCiclePaint.setStrokeCap(Paint.Cap.ROUND);
        miniCiclePaint.setShadowLayer(10, 5, 5, Color.GRAY);//阴影


        degrees = 0;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //外部大圆环
        canvas.drawCircle(cx, cy, radio, ciclePaint);
        canvas.drawCircle(cx, cy, miniRadio, textPaint);

        //环上着色区域
        RectF rect = new RectF(cx - radio, cy - radio, cx + radio, cy + radio);
        canvas.drawArc(rect, -90, (float) (angle), false, miniCiclePaint);
        //外环上的小圆


        canvas.drawCircle(btnX, btnY, miniRadio, btnPaint);
        // canvas.drawCircle(x, y, ciclieRadio, ciclePaint);


        String stt = ((int)  (angle/359*100)) + "";
        int length = (int) textPaint.measureText(stt);
        int asent = (int) textPaint.getFontMetrics().ascent;
        canvas.drawText(stt + "°", cx - length / 2, cy - asent / 2, textPaint);
        if (processListener!=null){
            processListener.updatePorcess((int) (angle/359*100));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchBtn(event)) {
                    updateProgress(event.getX(), event.getY());
                    isTouchOnArc = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchOnArc) {
                    updateProgress(event.getX(), event.getY());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isTouchOnArc = false;
                break;
        }


        return true;
    }


    private boolean isTouchMiniCicle(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        float curRadio = (float) Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
        if (curRadio >= (radio - Util.dip2px(this.context, 30)) && (curRadio <= (radio + Util.dip2px(this.context, 30)))) {
            return true;
        }
        return false;
    }
    private boolean isTouchBtn(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
if (x>(btnX-miniRadio)&&x<(btnX+miniRadio)&&y>(btnY-miniRadio)&&y<(btnY+miniRadio)){
    return true;
}


        return false;
    }

    private void updateProgress(float eventX, float eventY) {
        int curX = (int) (eventX - cx);
        int curY = (int) (eventY - cy);
        //第一象限
        if (curX > 0 && curY < 0) {
            if (lastQuadrant >= 4) {
                return;
            }
            curY = -curY;
            degrees = Math.atan2(curY, curX);
            angle = Math.toDegrees(Math.PI / 2 - degrees);
            btnX = (float) (cx + radio * Math.cos(degrees));
            btnY = (float) (cy - radio * Math.sin(degrees));
            lastQuadrant = 1;
        } else
            //第二象限
            if (curX > 0 && curY > 0) {
                degrees = Math.atan2(curY, curX);
                angle = Math.toDegrees(degrees) + 90;
                btnX = (float) (cx + radio * Math.cos(degrees));
                btnY = (float) (cy + radio * Math.sin(degrees));
                lastQuadrant = 2;
            } else
                //第三象限
                if (curX < 0 && curY > 0) {
                    curX = -curX;
                    degrees = Math.atan2(curY, curX);
                    angle = Math.toDegrees(Math.PI / 2 - degrees) + 180;
                    btnX = (float) (cx - radio * Math.cos(degrees));
                    btnY = (float) (cy + radio * Math.sin(degrees));
                    lastQuadrant = 3;
                } else
                    //第四象限
                    if (curX < 0 && curY < 0) {

                        if (lastQuadrant <= 1) {
                            return;
                        }
                        curX = -curX;
                        curY = -curY;
                        degrees = Math.atan2(curY, curX);
                        angle = Math.toDegrees(degrees) + 270;
                        btnX = (float) (cx - radio * Math.cos(degrees));
                        btnY = (float) (cy - radio * Math.sin(degrees));
                        lastQuadrant = 4;
                    }


        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        if (mWidth > mHeight) {
            mWidth = mHeight;
        }
        if (mWidth < mHeight) {
            mHeight = mWidth;
        }

        cx = mWidth / 2;
        cy = mHeight / 2;
        if (cicleWidth==0){
            cicleWidth = cx / 5;
        }
        radio = (int) (cx - cicleWidth - cicleWidth / 2);
        miniRadio = (int) cicleWidth;

        ciclePaint.setStrokeWidth(cicleWidth);
        miniCiclePaint.setStrokeWidth(cicleWidth);


        btnX = (float) (cx + radio * Math.sin(degrees));
        btnY = (float) (cy - radio * Math.cos(degrees));
        LinearGradient linearGradient = new LinearGradient(cx, cy - radio, cx + radio, cy + radio, new int[]{
                Color.rgb(0, 221, 255),
                Color.rgb(255, 255, 255),
                Color.rgb(245, 232, 0)},
                new float[]{0, .3F, .6F}, Shader.TileMode.CLAMP);
        //new float[]{},中的数据表示相对位置，将150,50,150,300，划分10个单位，.3，.6，.9表示它的绝对位置。300到400，将直接画出rgb（0,232,210）
        miniCiclePaint.setShader(linearGradient);
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        if (modeWidth == MeasureSpec.AT_MOST) {
            sizeWidth = defaultWidth;
        }

        if (modeHeight == MeasureSpec.AT_MOST) {
            sizeHeight = defaultHeight;
        }
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    interface ProcessListener{
        void updatePorcess(int process);
    }
    public void setOnProcessListener(ProcessListener processListener){
        this.processListener = processListener;
    }
}
