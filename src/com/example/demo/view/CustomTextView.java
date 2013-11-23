
package com.example.demo.view;

import com.example.demo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
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
