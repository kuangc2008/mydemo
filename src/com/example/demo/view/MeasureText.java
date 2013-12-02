package com.example.demo.view;

import java.nio.ReadOnlyBufferException;
import java.util.Arrays;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MeasureText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }
    
    private static class SampleView extends View {

        private Paint mPaint;
        private float mOriginX = 10;
        private float mOriginY = 80;
        public SampleView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(5);
            mPaint.setStrokeCap(Cap.ROUND);
            mPaint.setTextSize(64);
            mPaint.setTypeface(Typeface.create(Typeface.SERIF ,Typeface.BOLD_ITALIC));
            
        }
        
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            
            canvas.translate(mOriginX, mOriginY);
            showText(canvas, "Measure", Paint.Align.LEFT);
            canvas.translate(0, 80);
            showText(canvas, "wiggy!", Paint.Align.CENTER);
            canvas.translate(0, 80);
            showText(canvas, "Text", Paint.Align.RIGHT);
        }

        private void showText(Canvas canvas, String text, Align left) {
           
            float[] widths = new float[text.length()];
            int count = mPaint.getTextWidths(text, 0, text.length(), widths);
            Log.v("kc", "count-->" + count + "  widths-->" + Arrays.toString(widths));
            float w = mPaint.measureText(text, 0, text.length());
            
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            mPaint.setColor(Color.MAGENTA);
            canvas.drawLine(0, mPaint.ascent(), bounds.right, mPaint.ascent(), mPaint);
            canvas.drawLine(0, mPaint.descent(), bounds.right, mPaint.descent(), mPaint);
            
            canvas.drawLine(0, mPaint.getFontMetrics().top, bounds.right, mPaint.ascent(), mPaint);
            canvas.drawLine(0, mPaint.descent(), bounds.right, mPaint.descent(), mPaint);
            Log.v("kc", "bounds" + bounds);
            Log.v("kc", " a " + mPaint.ascent() + "  d  " + mPaint.descent());
            Log.v("kc", " a2 " + mPaint.getFontMetrics().ascent + "  d2  " + mPaint.getFontMetrics().descent);
            Log.v("kc", " t " + mPaint.getFontMetrics().top + "  b  " + mPaint.getFontMetrics().bottom);
            mPaint.setColor(0Xff88ff88);
            canvas.drawRect(bounds, mPaint);
            
            mPaint.setColor(Color.BLACK);
            canvas.drawText(text, 0, 0, mPaint);
            
            float[] pts = new float[2 + count*2];
            float x = 0;
            float y = 0;
            pts[0] = x;
            pts[1] = y;
            for(int i=0; i<count; i++) {
                x += widths[i];
                pts[2 + i*2] = x;
                pts[2 + i*2 + 1] = y;
            }
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(0);
            canvas.drawLine(0, 0, w, 0, mPaint);
            mPaint.setStrokeWidth(5);
            canvas.drawPoints(pts, 0, (count + 1) << 1, mPaint);
        }
        
    }
}
