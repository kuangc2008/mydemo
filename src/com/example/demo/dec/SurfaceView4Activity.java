package com.example.demo.dec;

import com.example.demo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceView4Activity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySuerfaceView mySuerfaceView = new MySuerfaceView(this);
        setContentView(mySuerfaceView);
    }

    public static class MySuerfaceView extends SurfaceView  implements Runnable, SurfaceHolder.Callback{
        boolean flag = false;
        private SurfaceHolder mSurfaceHolder;
        private Bitmap mBitmap = null;
        private Paint paint = null;

        public MySuerfaceView(Context context) {
            super(context);
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fish);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
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
                canvas.translate(300, 400);
                canvas.clipRect(0, 0, mBitmap.getWidth() / 10,mBitmap.getHeight());
                canvas.drawBitmap(mBitmap, -cureentFrame * mBitmap.getWidth() / 10, 0, paint);
            } catch(Exception e) {
                e.printStackTrace();
            } finally{
                if(canvas!= null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private int cureentFrame;
        private void logic() {
            cureentFrame++;
            if(cureentFrame>=10){
                cureentFrame=0;
            }
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
