package com.example.next_year2014;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.demo.R;
import com.example.next_year2014.ShellUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kuangcheng on 2014/8/6.
 */
public class ShellActivity extends Activity {

    String fileName = "com.jiubang.app.bgz_407180.apk";

    String JAR_NAME = "install.jar";
    @Override
    protected void onCreate(Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shell_activity);

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("kc", "is root:" + ShellUtils.checkRootPermission());


                AssetManager am = getAssets();
                File filePath = null;
                try {
                    InputStream is = am.open(fileName);
                    filePath = new File(Environment.getExternalStorageDirectory() , fileName);
                    if(filePath!=null && !filePath.exists()) {
                        filePath.createNewFile();
                    }
                    FileOutputStream os = new FileOutputStream(filePath);

                    int len = 0;
                    byte[] buffer = new byte[1024*8];
                    while( (len = is.read(buffer)) > 0 ) {
                        os.write(buffer, 0, len);
                    }
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }

                PackageUtils.install(ShellActivity.this, filePath.toString());
            }
        });


        findViewById(R.id.root2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AssetManager assetManager = getResources().getAssets();
                    InputStream in = assetManager.open(JAR_NAME);
                    if (in == null) {
                        return;
                    }
                    int length = in.available();
                    byte fileByte[] = new byte[length];
                    in.read(fileByte, 0, fileByte.length);
                    OutputStream out = openFileOutput(JAR_NAME,
                            Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
                    out.write(fileByte);
                    in.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                File filePath = new File(Environment.getExternalStorageDirectory() , fileName);
                installApp(filePath.toString());

            }

        });
    }



    public boolean installApp(String path) {
        File temp = new File(path);
        if (!temp.exists())
            return false;
        String exportClassPath = "export CLASSPATH=/data/data/"
                + getPackageName() + "/files/install.jar";
        String INSTALL_ACTION_CMD = " exec app_process /system/bin com.example.next_year2014.StartMain install ";
        String cmd[] = { exportClassPath, INSTALL_ACTION_CMD + path };
        try {
            consoleExec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void consoleExec(String[] cmd) throws IOException {
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        for (int i = 0; i < cmd.length; i++) {
            os.writeBytes(cmd + "\n");
        }
        os.writeBytes("exit\n");
        os.flush();
        os.close();
    }

}
