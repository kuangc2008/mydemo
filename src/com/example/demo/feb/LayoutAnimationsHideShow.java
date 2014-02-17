package com.example.demo.feb;

import java.security.KeyFactory;

import com.example.demo.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class LayoutAnimationsHideShow extends Activity {
    private LayoutTransition mTransitioner;
    LinearLayout container = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animations_hideshow);
        
        final CheckBox hideGoneCB = (CheckBox) findViewById(R.id.hideGoneCB);
        container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        for(int i=0; i<4; i++) {
            Button newButton = new Button(this);
            newButton.setText(String.valueOf(i));
            container.addView(newButton);
            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.setVisibility(hideGoneCB.isChecked() ? View.GONE : View.INVISIBLE);
                }
            });
        }
        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        parent.addView(container);
        
        Button addButton = (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < container.getChildCount(); ++i) {
                    View view = (View) container.getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        
        resetTransition();
        
        CheckBox customAnimCB = (CheckBox) findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long duration  = 0;
                if(isChecked) {
                    mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 300);
                    mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 300);
                    setupCustomAnimations();
                    duration = 500;
                    //也可以只设置某一种显示、消失的动画
                } else {
                    resetTransition();
                    duration = 300;
                }
                mTransitioner.setDuration(duration);
            }
        });
    }

    private void resetTransition() {
        mTransitioner = new LayoutTransition();
        container.setLayoutTransition(mTransitioner);
    }
    
    private void setupCustomAnimations() {
        //changing whild Adding
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",1f, 0f, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(this, pvhLeft, pvhTop, pvhRight,
                pvhBottom, pvhScaleX, pvhScaleY).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
        changeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1f);
                view.setScaleY(1f);
            }
        });
        
        //changeing while removing
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(this,
                pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
        changeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator)animation).getTarget();
                view.setRotation(0f);
            }
        });
        
        //Adding
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationY(0f);
            }
        });
        
        //removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).
                setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationX(0f);
            }
        });
    }
}
