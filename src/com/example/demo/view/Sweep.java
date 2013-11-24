package com.example.demo.view;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Sweep extends Activity {

    SampleView view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new SampleView(this);
        setContentView(view);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "dither").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0, 1, 0, "dotime").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                view.toggleDither();
                break;
            case 1:
                view.toggleDoTIme();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private static class SampleView extends View {
        private Paint mPaint = new Paint();
        private float mRotate;
        private Matrix mMatrix = new Matrix();
        SweepGradient shader = null;
        private boolean mDotiming = false;

        public SampleView(Context context) {
            super(context);
            
            shader = new SweepGradient(400, 400, new int[] {
                    Color.GREEN, Color.RED, Color.BLUE, Color.GREEN 
            }, null);
            mPaint.setShader(shader);
        }

        protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;
            canvas.drawColor(Color.WHITE);
            
            mMatrix.setRotate(mRotate, 400, 400);
            shader.setLocalMatrix(mMatrix);
            mRotate += 3;
            if(mRotate > 360) {
                mRotate = 0;
            }
            if(mDotiming) {
                long now = System.currentTimeMillis();
                Log.v("kc", "pre-" +  System.currentTimeMillis());
                for(int i=0 ; i<100; i++) {
                    canvas.drawCircle(400, 400, 300, paint);
                }
                Log.v("kc", "nex-" +  System.currentTimeMillis());
                now = System.currentTimeMillis() - now;
                Log.i("kc", "sweep ms =" + (now));
            } else {
                canvas.drawCircle(400, 400, 300, paint);
            }
            postInvalidateDelayed(25);
        }

        public void toggleDither() {
            mPaint.setDither(!mPaint.isDither());
            invalidate();
        }

        public void toggleDoTIme() {
            mDotiming = !mDotiming;
            invalidate();
        }
    }
}
