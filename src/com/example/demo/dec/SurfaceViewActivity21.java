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

public class SurfaceViewActivity21 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySuerfaceView mySuerfaceView = new MySuerfaceView(this);
        setContentView(mySuerfaceView);
    }

    public static class MySuerfaceView extends SurfaceView  implements Runnable, SurfaceHolder.Callback{
        private SurfaceHolder sfh;
        private Paint paint;
        private Thread th;
        private boolean flag;
        private Canvas canvas;
        private int screenW, screenH;
        private float smallCenterX = 120, smallCenterY = 120, smallCenterR = 20;
        private float BigCenterX = 120, BigCenterY = 120, BigCenterR = 400;
        public MySuerfaceView(Context context) {
            super(context);
            sfh = this.getHolder();
            sfh.addCallback(this);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            setFocusable(true);
        }

        
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            screenW = this.getWidth();
            screenH = this.getHeight();
            flag = true;
            th = new Thread(this);
            th.start();
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
                canvas = sfh.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    paint.setAlpha(0x77);
                    canvas.drawCircle(BigCenterX, BigCenterY, BigCenterR, paint);
                    canvas.drawCircle(smallCenterX, smallCenterY, smallCenterR, paint);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally{
                if(canvas!= null) {
                    sfh.unlockCanvasAndPost(canvas);
                }
            }
        }


        private void logic() {
            
        }


        private int mEndX,xEndY;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                smallCenterX = BigCenterX;
                smallCenterY = BigCenterY;
            } else {
                int pointX = (int) event.getX();
                int pointY = (int) event.getY();
                if (Math.sqrt(Math.pow((BigCenterX - (int) event.getX()), 2) +
                        Math.pow((BigCenterY - (int) event.getY()), 2)) <= BigCenterR) {
                    smallCenterX = pointX;
                    smallCenterY = pointY;
                } else {
                    setSmallCircleXY(BigCenterX, BigCenterY, BigCenterR, 
                            getRad(BigCenterX, BigCenterY, pointX, pointY));
                }
            }
            return true;
        }
        
        public double getRad(float px1, float py1, float px2, float py2) {
            float x = px2 - px1;
            float y = py1 - py2;
            float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            float cosAngle = x / Hypotenuse;
            float rad = (float) Math.acos(cosAngle);
            if (py2 < py1) {
                rad = -rad;
            }
            return rad;
        }
        
        public void setSmallCircleXY(float centerX, float centerY, float R, double rad) {
            smallCenterX = (float) (R * Math.cos(rad)) + centerX;
            smallCenterY = (float) (R * Math.sin(rad)) + centerY;
        }
    }
}
