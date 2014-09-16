package com.example.next2_09;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/9/15.
 */
public class SystemUIActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifaction_layout);

        View decorView =  getWindow ().getDecorView ();
        int uiOptions = View .SYSTEM_UI_FLAG_LOW_PROFILE ;
        decorView .setSystemUiVisibility ( uiOptions );

    }
}
