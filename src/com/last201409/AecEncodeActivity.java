package com.last201409;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by kuangcheng on 2014/12/8.
 */
public class AecEncodeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        Button b1 = new Button(this);
        ll.addView(b1);
        Button b2 = new Button(this);
        ll.addView(b2);
        b1.setText("编码");
        b2.setText("解码");
        b1.setId(0);
        b2.setId(2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        setContentView(ll);
    }

    private String getMyFilePath() {
        return new File(getFilesDir(), "hehe.js").getAbsolutePath();
    }

    String text = null;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                try {
                    InputStream is=getAssets().open("about.txt");
//                    AECUtils.encryptFile(is, getMyFilePath());

                    //DEC
//                    DECUtils.getUtil().encrypt(is, getMyFilePath());


//                    text = AECUTils3.encrypt(AECUTils3.SEED, "sfessssssssssssssss");
//                    Log.w("kcc", "a->" + text);

/*                    StringBuffer sb = new StringBuffer();
                    int len = 0;
                    byte [] buffer = new byte [1024];
                    while( (len = is.read(buffer)) != -1) {
                        sb.append(new String(buffer, 0, len));
                    }
                    is.close();*/

                    String aa = readAssetFile(this, "about.txt");


                    Log.w("kcc","os-->" +aa);
//                    String  bbb = URLEncoder.encode(os.toString(), "utf-8");
                    text = AESUtils5.encode(aa.toString().getBytes());
                    Log.w("kcc", "text" + text);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case 2:
//                String text = AECUtils.decryptFile(new File(getMyFilePath()));
//                Log.w("kcc", "text-->" + text);


            //DEC
//Log.w("kcc", "start time-->" + System.currentTimeMillis());
//                String hehe = DECUtils.getUtil().decrypt(getMyFilePath());
//                Log.i("kcc", "hehe-des->" + hehe);
//                Log.w("kcc", "end time-->" + System.currentTimeMillis());


//                try {
//                    String text2 = AECUTils3.decrypt(AECUTils3.SEED, text);
//                    Log.w("kcc", "a->" + text2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                String text2 = AESUtils5.decode(text);
                Log.w("kcc", "text2-->" + text2);
//                try {
//                    String text3 = URLDecoder.decode(text2, "utf-8");
//                    Log.w("kcc", text3);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

                break;
        }
    }


    public static String readAssetFile(Context context, String fileName) {
        String result = null;
        InputStream in = null;
        try {
            in = context.getAssets().open(fileName);
            StringBuffer sb = new StringBuffer();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, length));
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
