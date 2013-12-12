package com.example.demo.dec;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceView1Activity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySuerfaceView mySuerfaceView = new MySuerfaceView(this);
        setContentView(mySuerfaceView);
    }

    public static class MySuerfaceView extends SurfaceView  implements Runnable, SurfaceHolder.Callback{
        boolean flag = false;
        private SurfaceHolder mSurfaceHolder;
        private Path path = new Path();

        public MySuerfaceView(Context context) {
            super(context);
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);
        }

        
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            flag = true;
            new Thread(this).start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            flag = false;
        }

        @Override
        public void run() {
            while(flag) {
                try {
                    long start = System.currentTimeMillis();
                    logic();
                    draw();
                    long a = System.currentTimeMillis()  - start;
                    Thread.sleep(50 - a <=0 ? 0 : (50-a));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        private void draw() {
            Canvas canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                path.reset();
                path.moveTo(100,100);
                path.quadTo((mEndX-100)/2, (xEndY-100)/2, mEndX, xEndY);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, paint);
            } catch(Exception e) {
                e.printStackTrace();
            } finally{
                if(canvas!= null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }


        private void logic() {
            
        }


        private int mEndX,xEndY;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            mEndX = (int) event.getX();
            xEndY = (int) event.getY();
            return super.onTouchEvent(event);
        }
        
    }
}
