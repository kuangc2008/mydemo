package com.example.volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.kc.toolbox.MultiPartRequest;
import com.kc.toolbox.UploadHttpStack;
import com.last201409.UrlUtils;

import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.json.JSONObject;

import java.io.File;


public class LVolley3_Upload extends Activity implements View.OnClickListener {
    private RequestQueue mQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVIews();
        initValues();
    }

    private void initVIews() {
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ll.setOrientation(LinearLayout.VERTICAL);
        Button button1 = new Button(this);
        button1.setText("上传第一个数据");
        ll.addView(button1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button1.setTag("1");
        button1.setOnClickListener(this);

        button1 = new Button(this);
        button1.setText("上传第2个数据");
        ll.addView(button1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button1.setTag("2");
        button1.setOnClickListener(this);
    }

    private void initValues() {
        File cacheDir = new File(getCacheDir(), "upload");
        Network network = new BasicNetwork( new UploadHttpStack());
        mQueue = new RequestQueue( new DiskBasedCache(cacheDir), network);
        mQueue.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag().equals("1")) {
             uploadData1();
        } else if(v.getTag().equals("2")) {

        } else if(v.getTag().equals("3")) {

        } else if(v.getTag().equals("4")) {

        }
    }

    private void uploadData1() {

        MultiPartRequest request = new MultiPartRequest(Request.Method.POST, UrlUtils.UPLOAD_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("kcc", "response" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("kcc", "error", error );
            }
        });
        request.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        request.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        request.addStringUpload("text", "{\"id\":2}");
        mQueue.add(request);
    }
}
