package com.example.volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kc.volley.GesonRequest;
import com.kc.volley.NetworkManager;

import java.util.List;

/**
 * Created by kuangcheng on 2014/9/16.
 */
public class Volley4 extends Activity {

    private String url = "http://mbs.hao.360.cn/?c=config&v=6.0.0&m=useragent,seswitch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GesonRequest<VolleyBean> request = new GesonRequest<VolleyBean>(url, VolleyBean.class, null, new Response.Listener<VolleyBean>() {
            @Override
            public void onResponse(VolleyBean response) {
                Log.v("kcc", "response" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("kcc", "error");
            }
        });
        NetworkManager.getInstance(this).addToRequestQueue(request);
    }

    private class VolleyBean {
        VolleyBeanSamll useragent;
        List<VolleyBeanSamll2> seswitch;
    }

    private class VolleyBeanSamll {
        String useragent;
        int umengUpPercent;
    }

    private class VolleyBeanSamll2 {
        int type;
        String prefix;
    }
}


