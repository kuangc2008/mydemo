
package com.example.demo.Nov;

import com.example.demo.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListFlipper extends Activity {

    private static final String[] LIST_STRINGS_EN = new String[] {
            "One", "Two", "Three", "Four", "Five", "Six"
    };
    private static final String[] LIST_STRINGS_FR = new String[] {
            "Un", "Deux", "Trois", "Quatre", "Le Five", "Six"
    };

    ListView mEnglishList;
    ListView mFrenchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotating_list);
        mEnglishList = (ListView) findViewById(R.id.list_en);
        mFrenchList = (ListView) findViewById(R.id.list_fr);
        final ArrayAdapter<String> adapterEn = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_EN);
        final ArrayAdapter<String> adapterFr = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_FR);
        mEnglishList.setAdapter(adapterEn);
        mFrenchList.setAdapter(adapterFr);
        
        mFrenchList.setRotationY(-90f);
        
        Button starter = (Button) findViewById(R.id.button);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flipit();
            }
        });
    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
    
    protected void flipit() {
        final ListView visibleListView;
        final ListView invisiableListView;
        if(mEnglishList.getVisibility() == View.GONE) {
            visibleListView = mFrenchList;
            invisiableListView = mEnglishList;
        } else {
            invisiableListView = mFrenchList;
            visibleListView = mEnglishList;
        }

        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleListView, "rotationY", 0, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);

        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisiableListView, "rotationY", -90, 0);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                visibleListView.setVisibility(View.GONE);
                invisToVis.start();
                invisiableListView.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }
}
