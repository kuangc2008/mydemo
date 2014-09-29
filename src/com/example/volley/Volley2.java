package com.example.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.demo.R;
import com.kc.toolbox.NetworkManager;

/**
 * Created by kuangcheng on 2014/9/16.
 */
public class Volley2 extends Activity {
    ImageView iv = null;
    private static String[] imageUrls = {
            "http://test.designer.c-launcher.com/resources/wallpaper/img/848/5397d1250cf267d0f0d15dd8/1402458405568/wallpaper_s.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv = new ImageView(this);
        setContentView(iv);

        NetworkManager rm = NetworkManager.getInstance(this);
        ImageRequest ir = new ImageRequest(imageUrls[0], new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 300,300, Bitmap.Config.ARGB_8888,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iv.setImageResource(R.drawable.ic_launcher);
            }
        });
        rm.addToRequestQueue(ir);
    }
}
