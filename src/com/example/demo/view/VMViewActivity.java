package com.example.demo.view;

import com.example.demo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 *  一个不停的移动的子view
 */
public class VMViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nov_vm_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
