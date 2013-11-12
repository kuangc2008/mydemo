package com.example.demo.view;

import java.util.Random;

import com.example.demo.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 不停的通过onDraw方法，来刷新此view，注意的是： 我们没有复写onLayout，所以默认之下，是覆盖整个屏幕的！！！
 * 
 * 需要在xml中，写上此view的width与height
 */
public class MyVMView extends View {
    
    private Paint mPaint;
    private Paint mShadow;
    private float mCurrentAngle;

    public MyVMView(Context context) {
        super(context);
        init(context);
    }

    public MyVMView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Drawable bg = context.getResources().getDrawable(R.drawable.vumeter);
        setBackground(bg);
        
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        // 默认是fill
//        mPaint.setStyle(Style.STROKE);
        
        mShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadow.setColor(Color.argb(60, 0, 0, 0));
        
        mCurrentAngle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float minAngle = (float) Math.PI * 3 / 14;
        final float maxAngle = (float) Math.PI * 11 / 14;
        
        float angle = minAngle;
        angle += (float) (maxAngle - minAngle) * getRadio();

        if(angle > mCurrentAngle) {
            mCurrentAngle = angle;
        } else {
            mCurrentAngle = Math.max(angle, mCurrentAngle - 0.18f);
        }
        mCurrentAngle = Math.min(maxAngle, mCurrentAngle);
        
        
        float w = getWidth();
        float h = getHeight();
        float pivotX = w/2;
        float pivotY = h - 3.5f - 10;
        float l = h * 8/10;
        
        float sin = (float) Math.sin(mCurrentAngle);
        float cos = (float) Math.cos(mCurrentAngle);
        float x0 = pivotX - l*cos;
        float y0 = pivotY - l* sin;
        
        canvas.drawLine(x0 + 5.0f, y0 + 5.0f, pivotX + 5.0f, pivotY + 5.0f, mShadow);
        canvas.drawCircle(pivotX + 5.0f, pivotY + 5.0f, 3.5f, mShadow );
        canvas.drawLine(x0, y0, pivotX, pivotY, mPaint);
        canvas.drawCircle(pivotX, pivotY, 3.5f, mPaint);
        postInvalidateDelayed(500);
    }
    public float getRadio() {
        Random random = new Random();
        int random2 = random.nextInt(10);
        return random2 *0.1f;
        
    }
}
