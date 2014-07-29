package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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
        centerView  = findViewById(R.id.center);
        centerView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AnimatorProxy ap  = null;
        switch (v.getId()) {
            case R.id.right_move:
                ap = AnimatorProxy.wrap(centerView);
                ap.setTranslationX(ap.getTranslationX() + 20);
                ap.setTranslationY(ap.getTranslationY() + 20);
         //       centerView.setTranslationX(centerView.getTranslationX()+20);


                TimeAnimation ta = new TimeAnimation();
                ta.setDuration(4000);
                ta.setValue(500, 1000);
                centerView.startAnimation(ta);
                ta.setTimeAnimationListener(new TimeAnimationListener() {
                    @Override
                    public void onAnimationUpdate(float updatevalue) {
                        Log.v("kcc", this+ "intertime-->" + updatevalue);
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
