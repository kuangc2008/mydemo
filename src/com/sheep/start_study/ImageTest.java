package com.sheep.start_study;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.demo.R;


public class ImageTest extends Activity {
    public static final String CONTEXT_URI = "context://com.qihoo.browser/bookmark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("kcc", "width-->" + getWindowManager().getDefaultDisplay().getWidth());
        Log.i("kcc", "height-->" + getWindowManager().getDefaultDisplay().getHeight());


        final ImageView iv = new ImageView(this);
        setContentView(iv, new ViewGroup.LayoutParams(100, 100));

//        setContentView(iv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.drawable.background);
            }
        });


        Uri uri = Uri.parse(CONTEXT_URI);
        Log.i("kcc", "uri->" + uri.toString());


        uri = uri.buildUpon().appendQueryParameter("aaa", "true").build();
        Log.i("kcc", "uri->" + uri.toString());

    }



}
