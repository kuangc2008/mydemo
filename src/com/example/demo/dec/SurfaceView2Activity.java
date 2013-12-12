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
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceView2Activity extends Activity{

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
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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
//                canvas.drawBitmap(mBitmap, 300, 300, paint);
//
//                canvas.save();
//                canvas.rotate(30, mBitmap.getWidth()/2 + 300, mBitmap.getHeight()/2 + 300);
//                canvas.drawBitmap(mBitmap, 300, 300, paint);
//                canvas.restore();
//
//                Matrix mx = new Matrix();
//                mx.postRotate(-30, mBitmap.getWidth(), 0);
//                canvas.drawBitmap(mBitmap, mx, paint);
//                
//                
//                Matrix maT = new Matrix();
//                maT.postTranslate(10, 10);
//                canvas.drawBitmap(mBitmap, maT, paint);
//                
                canvas.save();
                canvas.scale(2f, 2f, 50 + mBitmap.getWidth() / 2, 50 + mBitmap.getHeight() / 2);
                canvas.drawBitmap(mBitmap, 50, 50, paint);
                canvas.restore();
                canvas.drawBitmap(mBitmap, 50, 50, paint);
//                
//                
//                Matrix maS = new Matrix();
//                maS.postTranslate(50, 500);
//                maS.postScale(2f, 2f, 50, 500 + mBitmap.getHeight() / 2);
//                canvas.drawBitmap(mBitmap, maS, paint);
//                canvas.drawBitmap(mBitmap, 50, 500, paint);
//                
//                canvas.drawBitmap(mBitmap, 50, 800, paint);
//                canvas.save();
//                canvas.scale(-1, 1, 100 + mBitmap.getWidth() / 2, 0);
//                canvas.drawBitmap(mBitmap, 150, 1000, paint);
//                canvas.restore();
                
//                canvas.drawBitmap(mBitmap, 0, 0, paint);
//                Matrix maMiX = new Matrix();
//                maMiX.postTranslate(0, 100);
//                maMiX.postScale(-1, 1,  mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//                canvas.drawBitmap(mBitmap, maMiX, paint);
                
//                canvas.drawBitmap(mBitmap, 0, 0, paint);
//                Matrix maMiY = new Matrix();
//                maMiY.postTranslate(100, 100);
//                maMiY.postScale(1, -1, 100 + mBitmap.getWidth() / 2, 100 + mBitmap.getHeight() / 2);
//                canvas.drawBitmap(mBitmap, maMiY, paint);
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
