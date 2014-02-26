package com.example.demo.feb.bitmapfun.utils;

import com.example.demo.BuildConfig;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
/**
 * A bitmapDrawable that keeps track of whether it is being displayed or cached.
 * when the drawable is no longer being displayed or cached. 
 */
public class RecyclingBitmapDrawable extends BitmapDrawable {

    static final String LOG_TAG = "CountingBitmapDrawable";
    
    private int mCacheRefCount = 0;
    private int mDisplayRefCount = 0;
    private boolean mHasBeenDisplayed;

    public RecyclingBitmapDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    public void setIsDisplayed(boolean isDisplayed) {
        synchronized (this) {
            if(isDisplayed) {
                mDisplayRefCount++;
                mHasBeenDisplayed = true;
            } else {
                mDisplayRefCount --;
            }
        }
        // Check to see if recycle() can be called
        checkState();
    }
    
    public void setIsCache(boolean isCached) {
        synchronized (this) {
            if(isCached) {
                mCacheRefCount++;
            } else {
                mCacheRefCount--;
            }
        }
        checkState();
    }

    private synchronized void checkState() {
        // if the drawable cache and display re count =0;  and the drawable
        // has been displayed ,then recycle
        if(mCacheRefCount <=0 && mDisplayRefCount <=0 && mHasBeenDisplayed &&  hasValidBitmap()) {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "No longer being used or cached so recycling. "
                        + toString());
            }
            getBitmap().recycle();
        }
    }
    
    private boolean hasValidBitmap() {
        Bitmap bitmap = getBitmap();
        return bitmap != null && !bitmap.isRecycled();
    }
}
