package com.example.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

public class TestUseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("TextUseActivity");
        setContentView(tv);
    }
}
