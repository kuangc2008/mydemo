package com.example.demo.view;

import android.app.Activity;
import android.os.Bundle;

public class ScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollViewGroup group = new ScrollViewGroup(this);
        setContentView(group);
    }
}
