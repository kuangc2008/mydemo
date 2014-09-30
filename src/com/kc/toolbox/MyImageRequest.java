package com.kc.toolbox;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MyImageRequest extends ImageRequest {
    /**
     * Creates a new image request, decoding to a maximum specified width and
     * height. If both width and height are zero, the image will be decoded to
     * its natural size. If one of the two is nonzero, that dimension will be
     * clamped and the other one will be set to preserve the image's aspect
     * ratio. If both width and height are nonzero, the image will be decoded to
     * be fit in the rectangle of dimensions width x height while keeping its
     * aspect ratio.
     *
     * @param url           URL of the image
     * @param listener      Listener to receive the decoded bitmap
     * @param maxWidth      Maximum width to decode this bitmap to, or zero for none
     * @param maxHeight     Maximum height to decode this bitmap to, or zero for
     *                      none
     * @param decodeConfig  Format to decode the bitmap to
     * @param errorListener Error listener, or null to ignore errors
     */
    public MyImageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        super(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
    }

    private Map<String, String> headers = new HashMap<String, String>();
    public void setHeader(Map<String, String> header) {
        headers = header;
    }


    public Map<String, String> getHeaders()  {
        return headers;
    }
}
