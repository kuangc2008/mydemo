package com.kc.toolbox;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

import java.util.HashMap;
import java.util.Map;


public class MyImageLoader extends ImageLoader {
    /**
     * Constructs a new ImageLoader.
     *
     * @param queue      The RequestQueue to use for making image requests.
     * @param imageCache The cache to use as an L1 cache.
     */
    public MyImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }


    protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth, int maxHeight, final String cacheKey) {
        MyImageRequest imageRequest = new MyImageRequest(requestUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                onGetImageSuccess(cacheKey, response);
            }
        }, maxWidth, maxHeight,
                Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGetImageError(cacheKey, error);
            }
        });
        imageRequest.setHeader(headers);
        return imageRequest;
    }

    private Map<String, String> headers = new HashMap<String, String>();
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }
    public void setHeader(String title, String content) {
        headers.put(title, content);
    }
}
