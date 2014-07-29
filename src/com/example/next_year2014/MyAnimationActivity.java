package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.demo.R;


public class MyAnimationActivity extends Activity implements View.OnClickListener {
    private View centerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_animation_1);

        findViewById(R.id.right_move).setOnClickListener(this);
        findViewById(R.id.rotation_x).setOnClickListener(this);
        findViewById(R.id.rotation_z).setOnClickListener(this);
        findViewById(R.id.scaleY).setOnClickListener(this);
        findViewById(R.id.animation_test).setOnClickListener(this);
        findViewById(R.id.animation_old).setOnClickListener(this);
        findViewById(R.id.alpha).setOnClickListener(this);
        centerView  = findViewById(R.id.center);
        centerView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AnimatorProxy ap  = null;
        switch (v.getId()) {
            case R.id.right_move:

//                ap.setTranslationX(ap.getTranslationX() + 20);
//                ap.setTranslationY(ap.getTranslationY() + 20);
         //       centerView.setTranslationX(centerView.getTranslationX()+20);

                TimeAnimation ta = new TimeAnimation();
                ta.setDuration(4000);
                ta.setValue(0, 500);
                ((View)centerView.getParent()).startAnimation(ta);
                ta.setTimeAnimationListener(new TimeAnimationListener() {
                    @Override
                    public void onAnimationUpdate(float updatevalue) {
                        Log.v("kcc", this+ "intertime-->" + updatevalue);
                        AnimatorProxy ap = AnimatorProxy.wrap(centerView);
                        ap.setTranslationX(updatevalue);
                        ap.setTranslationY(updatevalue);
                    }
                });
                break;
            case R.id.center:
                Toast.makeText(this, "hehe", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rotation_x:
                ap = AnimatorProxy.wrap(centerView);
                ap.setRotationX(ap.getRotationX() + 20);
                break;
            case R.id.rotation_z:
                ap = AnimatorProxy.wrap(centerView);
                ap.setRotation(ap.getRotation() + 20);
                break;
            case R.id.scaleY:
                ap = AnimatorProxy.wrap(centerView);
                ap.setScaleY((float) (ap.getScaleY() + 0.2));
                break;
            case R.id.animation_test:
                //不起作用
                ap = AnimatorProxy.wrap(centerView);
                ap.setTranslationY(300);
                ap.setTranslationX(300);
                ap.setDuration(300);
                centerView.startAnimation(ap);
                break;
            case R.id.animation_old:
                TranslateAnimation ta2 = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 500,
                        TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 500);
                ta2.setDuration(4000);
                centerView.startAnimation(ta2);
                break;
            case R.id.alpha:
                ap = AnimatorProxy.wrap(centerView);
                ap.setAlpha(0.1f);
                break;

        }
    }


    private class TimeAnimation extends Animation {
        TimeAnimationListener tl = null;
        float startValue = 0f;
        float endValue = 1f;
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            tl.onAnimationUpdate(interpolatedTime * (endValue - startValue) + startValue);
        }

        public void setTimeAnimationListener(TimeAnimationListener tl) {
            this.tl = tl;
        }

        public void setValue(float start, float end) {
            startValue = start;
            endValue = end;
        }
    }

    private interface TimeAnimationListener {
        void onAnimationUpdate(float updatevalue);
    }
}
