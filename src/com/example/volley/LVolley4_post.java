package com.example.volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kc.toolbox.MyJsonRequest;
import com.last201409.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LVolley4_post extends Activity implements View.OnClickListener {
    private RequestQueue mQueue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVIews();
        mQueue = Volley.newRequestQueue(this);
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
        JSONObject js = new JSONObject();
        try {
            js.put("nibu", "zhenniubi");
            js.put("gaga", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyJsonRequest jor = new MyJsonRequest(Request.Method.POST, UrlUtils.UPLOAD_URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.w("kcc", "onResponse" );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("kcc", "error", error);
            }
        });
        jor.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        jor.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        mQueue.add(jor);
    }
}
