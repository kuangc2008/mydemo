
package com.example.demo.feb;

import java.util.ArrayList;

import com.example.demo.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimationLoadding extends Activity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_loading);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });
    }

    public class MyAnimationView extends View implements AnimatorUpdateListener {
        private static final float BALL_SIZE = 100f;

        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        Animator animation = null;

        public MyAnimationView(Context context) {
            super(context);
            addBall(50, 50);
            addBall(200, 50);
            addBall(350, 50);
            addBall(500, 50, Color.GREEN);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball : balls) {
                canvas.translate(ball.getX(), ball.getY());
                ball.getShape().draw(canvas);
                canvas.translate(-ball.getX(), -ball.getY());
            }
        }

        private void addBall(int x, int y, int color) {
            ShapeHolder shapeHolder = createBall(x, y);
            shapeHolder.setColor(color);
            balls.add(shapeHolder);
        }

        private void addBall(int x, int y) {
            ShapeHolder shapeHolder = createBall(x, y);
            int red = (int) (100 + Math.random() * 155);
            int green = (int) (100 + Math.random() * 155);
            int blue = (int) (100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = shapeHolder.getShape().getPaint();
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor,
                    TileMode.CLAMP);
            paint.setShader(gradient);
            balls.add(shapeHolder);
        }

        private ShapeHolder createBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            return shapeHolder;
        }

        public void startAnimation() {
            createAnimation();
            animation.start();
        }

        private void createAnimation() {
            Context appContext = AnimationLoadding.this;
            if (animation == null) {
                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext,
                        R.anim.object_animator);
                anim.addUpdateListener(this);
                anim.setTarget(balls.get(0));

                ValueAnimator fader = (ValueAnimator) AnimatorInflater.loadAnimator(appContext,
                        R.anim.animator);
                fader.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        balls.get(1).setAlpha((Float) animation.getAnimatedValue());
                    }
                });

                AnimatorSet seq = (AnimatorSet) AnimatorInflater.loadAnimator(appContext, R.anim.animator_set);
                seq.setTarget(balls.get(2));
                
                ObjectAnimator colorizer = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.anim.color_animator);
                colorizer.setTarget(balls.get(3));
                
                animation = new AnimatorSet();
                ((AnimatorSet) animation).playTogether(anim, fader, seq, colorizer);
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
//            ShapeHolder ball = balls.get(0);
//            ball.setY((Float)animation.getAnimatedValue());
        }

    }
}
