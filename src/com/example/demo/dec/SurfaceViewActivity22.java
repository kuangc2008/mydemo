package com.example.demo.dec;

import com.example.demo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewActivity22 extends Activity{

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
        //声明一张icon位图
        private Bitmap bmpIcon;
        //记录两个触屏点的坐标
        private int x1, x2, y1, y2;
        //倍率
        private float rate = 1;
        //记录上次的倍率
        private float oldRate = 1;
        //记录第一次触屏时线段的长度
        private float oldLineDistance;
        //判定是否头次多指触点屏幕
        private boolean isFirst = true;

        public MySuerfaceView(Context context) {
            super(context);
            sfh = this.getHolder();
            sfh.addCallback(this);
            paint = new Paint();
            paint.setAntiAlias(true);
            setFocusable(true);
            bmpIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.open_001);
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
                    canvas.save();
                    //缩放画布(以图片中心点进行缩放，XY轴缩放比例相同)
                    canvas.scale(rate, rate, screenW / 2, screenH / 2);
                    //绘制位图icon
                    canvas.drawBitmap(bmpIcon, screenW / 2 - bmpIcon.getWidth() / 2, screenH / 2 - bmpIcon.getHeight() / 2, paint);
                    canvas.restore();
                    //便于观察，这里绘制两个触点时形成的线段
                    canvas.drawLine(x1, y1, x2, y2, paint);
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
            //用户手指抬起默认还原为第一次触屏标识位，并且保存本次的缩放比例
            if (event.getAction() == MotionEvent.ACTION_UP) {
                isFirst = true;
                oldRate = rate;
            } else {
                x1 = (int) event.getX(0);
                y1 = (int) event.getY(0);
                if (event.getPointerCount() == 2) {
                    x2 = (int) event.getX(1);
                    y2 = (int) event.getY(1);
                    if (isFirst) {
                        //得到第一次触屏时线段的长度
                        oldLineDistance = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2) + Math.pow(event.getY(1) - event.getY(0), 2));
                        isFirst = false;
                    } else {
                        //得到非第一次触屏时线段的长度
                        float newLineDistance = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2) + Math.pow(event.getY(1) - event.getY(0), 2));
                        //获取本次的缩放比例
                        rate = oldRate * newLineDistance / oldLineDistance;
                    }
                }
            }
            return true;
        }
    }
}
