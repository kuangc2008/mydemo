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


                TimeAnimation ta = new TimeAnimation();
                ta.setDuration(1000);
                centerView.startAnimation(ta);
                ta.setTimeAnimationListener(new TimeAnimationListener() {
                    @Override
                    public void onAnimationUpdate(float interpolatedTime) {
                        Log.v("kcc", "intertime-->" + interpolatedTime);
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
        }
    }


    private class TimeAnimation extends Animation {
        TimeAnimationListener tl = null;
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            tl.onAnimationUpdate(interpolatedTime);
        }

        public void setTimeAnimationListener(TimeAnimationListener tl) {
            this.tl = tl;
        }
    }

    private interface TimeAnimationListener {
        void onAnimationUpdate(float interpolatedTime);
    }
}
