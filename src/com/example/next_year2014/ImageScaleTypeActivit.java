package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;


public class ImageScaleTypeActivit extends Activity implements View.OnClickListener {
    ImageView fishImageView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaletype_activity);

        findViewById(R.id.large_image).setOnClickListener(this);
        findViewById(R.id.small_image).setOnClickListener(this);
        findViewById(R.id.long_image).setOnClickListener(this);
        findViewById(R.id.scaleTyp1).setOnClickListener(this);
        findViewById(R.id.scaleTyp2).setOnClickListener(this);
        findViewById(R.id.scaleTyp3).setOnClickListener(this);
        findViewById(R.id.scaleTyp4).setOnClickListener(this);
        findViewById(R.id.scaleTyp5).setOnClickListener(this);
        fishImageView = (ImageView) findViewById(R.id.imageview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.large_image:
                fishImageView.setImageResource(R.drawable.larg_image2);
                break;
            case R.id.small_image:
                fishImageView.setImageResource(R.drawable.th);
                break;
            case R.id.long_image:
                fishImageView.setImageResource(R.drawable.fish);
                break;
            case R.id.scaleTyp1:
                fishImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case R.id.scaleTyp2:
                fishImageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case R.id.scaleTyp3:
                fishImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case R.id.scaleTyp4:
                fishImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case R.id.scaleTyp6:
                fishImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            case R.id.scaleTyp7:
                fishImageView.setScaleType(ImageView.ScaleType.FIT_END);
            case R.id.scaleTyp8:
                fishImageView.setScaleType(ImageView.ScaleType.MATRIX);
                break;
        }
    }
}
