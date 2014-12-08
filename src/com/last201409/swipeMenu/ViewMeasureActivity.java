package com.last201409.swipeMenu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;


public class ViewMeasureActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.w("kcc", "View.MeasureSpec.AT_MOST-->" + Integer.toBinaryString( View.MeasureSpec.AT_MOST));
//        Log.w("kcc", "View.MeasureSpec.EXACTLY-->" + Integer.toBinaryString( View.MeasureSpec.EXACTLY));
//        Log.w("kcc", "View.MeasureSpec.UNSPECIFIED-->" + Integer.toBinaryString( View.MeasureSpec.UNSPECIFIED));
//
//        Log.w("kcc", "LayoutParams.MATCH_PARENT-->" + Integer.toBinaryString(ViewGroup.LayoutParams.MATCH_PARENT));
//        Log.w("kcc", "LayoutParams.WRAP_CONTENT-->" + Integer.toBinaryString(ViewGroup.LayoutParams.WRAP_CONTENT));



        setContentView(R.layout.view_measure_layout);

    }
}
