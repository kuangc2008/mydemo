package com.example.volley;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.last201409.UrlUtils;

import org.apache.http.HttpConnection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AVOSDemo1 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Button bt = new Button(this);
        bt.setText("点击我，获取服务器数据");
        ll.addView(bt);
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


        bt = new Button(this);
        bt.setText("点击我，上传服务器数据");
        ll.addView(bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlS = UrlUtils.BASE_URL + "Hehe";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlS);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.addRequestProperty("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
                            urlConnection.addRequestProperty("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
                            urlConnection.addRequestProperty("Content-Type", "application/json");

                            urlConnection.setDoOutput(true);
                            urlConnection.setDoInput(true);
                            urlConnection.setRequestMethod("POST");
                            urlConnection.connect();

                            String s = "{\"id\":2}";
                            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                            // 发送请求参数
                            out.print(s);
                            // flush输出流的缓冲
                            out.flush();
                            // 定义BufferedReader输入流来读取URL的响应
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(urlConnection.getInputStream()));
                            String line;
                            String result = "";
                            while ((line = in.readLine()) != null) {
                                result += line;
                            }

                            Log.i("kcc", "sucess  " + result);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.w("kcc", "error", e);
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
