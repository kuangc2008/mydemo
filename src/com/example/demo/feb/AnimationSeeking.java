
package com.example.demo.feb;

import java.util.ArrayList;

import com.example.demo.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class AnimationSeeking extends Activity {
    private static final int DURATION = 1500;
    private SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_seeking);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);
        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setMax(DURATION);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                // prevent seeking on app creation
                if (animView.getHeight() != 0) {
                    animView.seek(progress);
                }
            }
        });
    }

    public class MyAnimationView extends View implements AnimatorUpdateListener {
        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;
        private static final float BALL_SIZE = 100f;

        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        AnimatorSet animation = null;
        ValueAnimator bounceAnim = null;
        ShapeHolder ball = null;

        public MyAnimationView(Context context) {
            super(context);
            ball = addBall(200, 0);
        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            
            int red = (int)(100 + Math.random() * 155);
            int green = (int)(100 + Math.random() * 155);
            int blue = (int)(100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            
            Paint paint = drawable.getPaint();
            //值更小，则更黑
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            // 因为圆的大小是100，半径是50； 故意将半径放在它处以便有立体效果
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
        }
        
        public void startAnimation() {
            createAnimation();
            bounceAnim.start();
        }

        private void createAnimation() {
            if(bounceAnim == null) {
                bounceAnim = ObjectAnimator.ofFloat(ball, "y", ball.getY(), getHeight() - BALL_SIZE).
                        setDuration(1500);
                bounceAnim.setInterpolator(new BounceInterpolator());
                bounceAnim.addUpdateListener(this);
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
        
        public void seek(long seekTime) {
            createAnimation();
            bounceAnim.setCurrentPlayTime(seekTime);
        }
    }
}
