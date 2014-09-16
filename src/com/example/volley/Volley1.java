package com.example.volley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by kuangcheng on 2014/9/16.
 */
public class Volley1 extends Activity{

    private String url = "http://www.baidu.com";
    TextView mTv = null;
    RequestQueue mRq = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        mRq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mTv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTv.setText("error");
            }
        });
        sr.setTag("ha");
        mRq.add(sr);
//        mRq.cancelAll("ha");
    }

    private void initViews() {
        mTv = new TextView(this);
        setContentView(mTv);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mRq != null) {
            mRq.cancelAll("ha");
        }
    }
}
