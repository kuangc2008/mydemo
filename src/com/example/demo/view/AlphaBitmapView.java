package com.example.demo.view;

import java.io.InputStream;

import com.example.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 默认大小为整个屏幕
 * @author spreadst
 *
 */
public class AlphaBitmapView extends View {
        private Bitmap mBitmap;
        private Bitmap mBitmap2;
        private Bitmap mBitmap3;
        private Shader mShader;

        public AlphaBitmapView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setFocusable(true);

            InputStream is = context.getResources().openRawResource(R.drawable.app_sample_code);
            mBitmap = BitmapFactory.decodeStream(is);

            //1 脱去了bitmap的透明值，将来以paint的颜色绘制 
            mBitmap2 = mBitmap.extractAlpha();
            
            mBitmap3 = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
            drawIntoBitmap(mBitmap3);
            
            mShader = new LinearGradient(0, 0, 100, 70, new int[]{
                    Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.MIRROR);
        }
        

        private void drawIntoBitmap(Bitmap bm) {
            float x = bm.getWidth();
            float y = bm.getHeight();
            //2 canvas将在此bm上进行绘制
            Canvas c = new Canvas(bm);
            Paint p = new Paint();
            p.setAlpha(0x80);
            c.drawCircle(x/2, y/2, x/2, p);
            
            p.setAlpha(0x40);
            //3 一种颜色混合方式，当当前的alpah也为0x80时，则将看不到
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            p.setTextSize(100);
            p.setTextAlign(Paint.Align.CENTER);
            // 3 fm与paintsize有关，而与图片的大小无关。
            Paint.FontMetrics fm = p.getFontMetrics();
            Log.v("kc", "fm-->" + "fm.ascent " + fm.ascent + " fm.bottom " +
                 fm.bottom + " fm.descent " + fm.descent + " fm.leading " +fm.leading + " fm.top " + fm.top );
            Log.v("kc", "y-->" + y + " size-->" + p.getTextSize());
            c.drawText("Alpha", x/2, (y - fm.ascent)/2, p);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            
            Paint p = new Paint();
            float y = 10;
            p.setColor(Color.BLUE);
            
            canvas.drawBitmap(mBitmap, 10, y, p);
            y += mBitmap.getHeight() + 10;
            
            canvas.drawBitmap(mBitmap2, 10, y, p);
            y += mBitmap2.getHeight() + 10;
            
            p.setShader(mShader);
            canvas.drawBitmap(mBitmap3, 10, y, p);
        }
}
