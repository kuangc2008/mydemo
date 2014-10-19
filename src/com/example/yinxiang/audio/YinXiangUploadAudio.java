package com.example.yinxiang.audio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.example.demo.R;

import org.apache.http.client.utils.URIUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kuangcheng on 2014/10/19.
 */
public class YinXiangUploadAudio extends Activity{
    private EditText mTitleTV;
    private EditText mFileNameET;
    private EditText mDescET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0,0 ,"上传").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                upload();
                break;
        }
        return true;
    }

    private void upload() {
        String filename = null;
        String filePath = null;
        Intent i = getIntent();
        if (i != null) {
            Uri fileUri = i.getData();
            filename = fileUri.getLastPathSegment();
            filePath = fileUri.getPath();
        }
        if (filename == null || filePath == null) {
            return;
        }

        new HttpUpload(this, "https://cn.avoscloud.com/1.1/classes/UpdateConfig", filePath).execute();
    }

    private void initValues() {
        Intent i = getIntent();
        if(i != null) {
            Uri fileUri = i.getData();
            String filename = fileUri.getLastPathSegment();
            int lastEnd = filename.lastIndexOf('.');
            String fileNameNoEnd = (lastEnd == -1 ) ? filename : filename.substring(0, filename.lastIndexOf('.'));
            mTitleTV.setText(fileNameNoEnd);
            mFileNameET.setText(filename);
        }
     }

    private void initViews() {
        setContentView(R.layout.upload_file);
        mTitleTV = (EditText) findViewById(R.id.title);
        mFileNameET = (EditText) findViewById(R.id.file_name);
        mDescET = (EditText) findViewById(R.id.desc);
    }
}

