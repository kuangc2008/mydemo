package com.example.demo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;

public class ShapeDrawableActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleVIew(this));
    }

    private static class SampleVIew extends View {
        private ShapeDrawable[] mDrawables;

        public static Shader makeTiling() {
            int[] pixels = new int[] {0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};
            Bitmap bm = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.ARGB_8888);
            return new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
        
        
        public SampleVIew(Context context) {
            super(context);
            float[] outerR = new float[] {12,12, 12,12, 0,0, 0,0};
            RectF inset = new RectF(6, 6, 6, 6);
            float[] innterR = new float[] {12,12, 0,0, 12,12, 0,0};
            
            Path path = new Path();
            path.moveTo(50, 0);
            path.lineTo(0, 50);
            path.lineTo(50, 100);
            path.lineTo(100, 50);
            path.close();
            
            
            mDrawables = new ShapeDrawable[7];
            mDrawables[0] = new ShapeDrawable(new RectShape());
            mDrawables[1] = new ShapeDrawable(new OvalShape());
            mDrawables[2] = new ShapeDrawable(new RoundRectShape(outerR, null, null));
            mDrawables[3] = new ShapeDrawable(new RoundRectShape(outerR, inset, null));
            mDrawables[4] = new ShapeDrawable(new RoundRectShape(outerR, inset, innterR));
            mDrawables[5] = new ShapeDrawable(new PathShape(path, 100, 100));
            mDrawables[6] = new ShapeDrawable(new ArcShape(45, 270));
            
            mDrawables[0].getPaint().setColor(0xFFFF0000);
            mDrawables[1].getPaint().setColor(0xFF00FF00);
            mDrawables[2].getPaint().setColor(0Xff0000ff);
            
            mDrawables[5].getPaint().setShader(makeTiling());
        }
        
        
        @Override
        protected void onDraw(Canvas canvas) {
            int x = 10;
            int y = 10;
            
            int width = 300;
            int height = 50;
            
            for(Drawable dr : mDrawables) {
                dr.setBounds(x, y, x+width, y+height);
                dr.draw(canvas);
                y += height + 5;
            }
            
            int[] pixels = new int[] {0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};
            Bitmap bitmap = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.ARGB_8888);
            canvas.drawBitmap(bitmap, null, new Rect(20, y+height+20, 40 , y+height+20 + 40), null);
        }
    }
}
