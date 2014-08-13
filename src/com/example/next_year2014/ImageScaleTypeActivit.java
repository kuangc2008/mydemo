package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;


public class ImageScaleTypeActivit extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaletype_activity);

        findViewById(R.id.large_image).setOnClickListener(this);
        findViewById(R.id.small_image).setOnClickListener(this);
        findViewById(R.id.scaleTyp1).setOnClickListener(this);
        findViewById(R.id.scaleTyp2).setOnClickListener(this);
        findViewById(R.id.scaleTyp3).setOnClickListener(this);
        findViewById(R.id.scaleTyp4).setOnClickListener(this);
        findViewById(R.id.scaleTyp5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.large_image:

                break;
            case R.id.small_image:

                break;
            case R.id.scaleTyp1:
                break;
            case R.id.scaleTyp2:
                break;
            case R.id.scaleTyp3:
                break;
            case R.id.scaleTyp4:
                break;
            case R.id.scaleTyp5:
                break;
        }
    }
}
