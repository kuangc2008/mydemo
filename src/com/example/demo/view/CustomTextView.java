
package com.example.demo.view;

import com.example.demo.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomTextView extends View {

    private Paint mTextPaint;
    private String mText;
    private int mAscent;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLabelView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String text = a.getString(R.styleable.CustomTextView_text);
        if (text != null) {
            setText(text);
        }
        settextColor(a.getColor(R.styleable.CustomTextView_textColor, 0xF0000));

        int textSize = a.getDimensionPixelSize(R.styleable.CustomTextView_textSize, 0);
        if (textSize > 0) {
            setTextSize(textSize);
        }
        a.recycle();
    }

    private void setTextSize(int textSize) {
        mTextPaint.setTextSize(textSize);
        requestLayout();
        invalidate();
    }

    private void settextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    private void setText(String text) {
        mText = text;
        requestLayout();
        invalidate();
    }

    private void initLabelView() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mTextPaint.setColor(0xFF000000);
        setPadding(3, 3, 3, 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        Log.v("kc", "onMeasure");
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.v("kc", "onLayout");
    }
    

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.v("kc", "height mode-->" + specMode + "  specSize-->" + specSize);
        mAscent = (int) mTextPaint.ascent();
        if(specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop() + getPaddingBottom();
            if(specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.v("kc", "width mode-->" + specMode + "  specSize-->" + specSize);
        
        if(specMode == MeasureSpec.EXACTLY) {
            result = specSize;
            Log.v("kc", "exactly");
        } else {
            if(specMode == MeasureSpec.UNSPECIFIED) {
                Log.v("kc", "UNSPECIFIED");
            }
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft() + 
                    getPaddingRight();
            if(specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
                Log.v("kc", "AT_MOST");
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("kc", "onDraw");
        super.onDraw(canvas);
        canvas.drawColor(color.white);
//        canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
        
        
        int line = 100;
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(80);
        FontMetrics metrics = mTextPaint.getFontMetrics();
        Log.v("kc", "mtricd-->" + "  a  " + metrics.ascent + "   d  " + metrics.descent + "   b " + 
                metrics.bottom + "   t  " + metrics.top + "   end  " + metrics.leading);
        canvas.drawLine(0, line, getWidth(), line, mTextPaint);
        canvas.drawText("哈哈哈", 0, line, mTextPaint);
        
        mTextPaint.setColor(Color.GREEN);
        canvas.drawLine(0, line + 100, getWidth(), line + 100, mTextPaint);
        canvas.drawText("哈哈哈1", 0, line + 100 -  metrics.ascent, mTextPaint);
        
        
        canvas.save();
        canvas.translate(0, 300);
        canvas.drawLine(0, 0, getWidth(), 0, mTextPaint);
        canvas.drawText("哈哈哈2",0,  -  metrics.ascent, mTextPaint);
        canvas.restore();
        
        canvas.save();
        canvas.clipRect(0, 400, getWidth(), 600);
        canvas.clipRect(20, 400, 40, 600, Op.DIFFERENCE);
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(0, 420, getWidth(), 420, mTextPaint);
        canvas.drawText("哈哈哈3",0, 420 -  metrics.ascent, mTextPaint);
        canvas.restore();
        
        canvas.drawLine(0, 500, getWidth(), 500, mTextPaint);
        canvas.drawText("啊嘎嘎个4", 0,  500- metrics.ascent, mTextPaint);
//        canvas.drawText(mText,  0,-mTextPaint.ascent(), mTextPaint);
//        canvas.drawText(mText, 0,  mTextPaint.descent() ,mTextPaint);
//        canvas.drawText(mText, 0,  mTextPaint. ,mTextPaint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("kc", "onTouchEvent");
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        }, 2000);
        return super.onTouchEvent(event);
    }

}
