package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demo.R;
import com.example.next_year2014.ShellUtils;

/**
 * Created by kuangcheng on 2014/8/6.
 */
public class ShellActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shell_activity);

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("kc", "is root:" + ShellUtils.checkRootPermission());

                PackageUtils.install(ShellActivity.this, "");
            }
        });
    }
}
