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

public class SurfaceView5Activity extends Activity{

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
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
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

                
                int frameW = mBitmap.getWidth() / 6;
                int frameH = mBitmap.getHeight() / 2;
                int x = currentFrame % 6 * frameW;
                int y = currentFrame / 6 * frameH;
                canvas.save();
                canvas.clipRect(robot_x, robot_y, robot_x + mBitmap.getWidth() / 6, robot_y + mBitmap.getHeight() / 2);
                if (isLeft) {
                    canvas.scale(-1, 1, robot_x - x + mBitmap.getWidth() / 2, robot_y - y + mBitmap.getHeight() / 2);
                }
                canvas.drawBitmap(mBitmap, robot_x - x, robot_y - y, paint);
                canvas.restore();
            } catch(Exception e) {
                e.printStackTrace();
            } finally{
                if(canvas!= null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private int currentFrame;
        private int robot_x, robot_y;
        private boolean isUp, isDown, isLeft, isRight;
        private void logic() {
            if (isUp) {
                robot_y -= 5;
            }
            if (isDown) {
                robot_y += 5;
            }
            if (isLeft) {
                robot_x -= 5;
            }
            if (isRight) {
                robot_x += 5;
            }
            currentFrame++;
            if (currentFrame >= 12) {
                currentFrame = 0;
            }
        }
        
        


        private int mEndX,xEndY;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isLeft = true;
                    break;
                case MotionEvent.ACTION_UP:
                    isLeft = false;
                    break;

                default:
                    break;
            }
            return true;
        }
        
    }
}
