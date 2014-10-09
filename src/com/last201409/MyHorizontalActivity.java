package com.last201409;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/10/8.
 */
public class MyHorizontalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        MyHorizontalScrollView rootView = new MyHorizontalScrollView(this);
        for(int i=0; i<7; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.guide_page_one_content + i);
            rootView.measure(0, 0);
            rootView.addView(imageView, new ViewGroup.LayoutParams(rootView.getMeasuredWidth()/2, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        rootView.setBackgroundResource(android.R.color.holo_orange_light);
        setContentView(rootView);
    }
}
