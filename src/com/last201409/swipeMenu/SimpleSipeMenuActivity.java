package com.last201409.swipeMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/11/6.
 */
public class SimpleSipeMenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleSwipeLayout layout = new SimpleSwipeLayout(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout.addButton("wahaha");

        setContentView(layout, lp);
    }
}
