package com.last201409;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.demo.R;
import com.utils.FileUtils;


public class ReaderActivity extends Activity {
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_layout);

        Uri dataUri = getIntent().getData();
        if(dataUri != null) {
            filePath = dataUri.getPath();
        }

        String text = new String(FileUtils.fileToByteArray(filePath));
        Log.w("kcc", "text->" + text);

    }


}
