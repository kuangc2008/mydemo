package com.example.demo.view;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("ViewUseActivity");
        setContentView(tv);
    }
}
