package com.example.volley;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.last201409.UrlUtils;

import org.apache.http.HttpConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AVOSDemo1 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button bt = new Button(this);
        bt.setText("点击我，获取服务器数据");
        setContentView(bt, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlS = UrlUtils.BASE_URL + "Images/5427fa9de4b030c336a16d79";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlS);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.addRequestProperty("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
                            urlConnection.addRequestProperty("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
                            urlConnection.connect();
                            int code = urlConnection.getResponseCode();
                            Log.w("kcc", "code-->" + code);
                            InputStream inputStream = urlConnection.getInputStream();


                            String hehe = stream2String(inputStream);
                            Log.w("kcc", hehe);
                            inputStream.close();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    private String stream2String(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*8];
        int len = 0;
        while( (len = inputStream.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        baos.close();
        return baos.toString();

    }
}
