package com.kc.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by kuangcheng on 2014/9/16.
 */
public class NetworkManager {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private static NetworkManager mInstance = null;

    private NetworkManager(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(context));
    }

    public static NetworkManager getInstance(Context context) {
        if(mInstance == null) {
             synchronized (NetworkManager.class) {
                if(mInstance == null) {
                    mInstance = new NetworkManager(context);
                }
             }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            // getApplicationContext is key. it keeps you from leaking the actiity or broadcastReciver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void loadImage(String url, ImageLoader.ImageListener imageListener) {
        loadImage(url, imageListener, 0,0);
    }

    public void loadImage(String url, ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
        mImageLoader.get(url, imageListener,maxWidth, maxHeight);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }


    public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{

        public LruBitmapCache(int maxSize) {
            super(maxSize);
        }

        public LruBitmapCache(Context ctx) {
            this(getDefaultSize(ctx));
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight()/1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }

    public static int getCacheSize(Context ctx) {
        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screehHeight = dm.heightPixels;
        final int screenBytes = screenWidth * screehHeight;
        return screenBytes *3;
    }

    public static int getDefaultSize(Context ctx) {
        int maxMemory = (int ) ( Runtime.getRuntime ().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8 ;
        return cacheSize;

    }
}
