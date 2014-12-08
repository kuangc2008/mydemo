package com.last201409.swipeMenu;

import android.content.Context;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import java.security.MessageDigestSpi;

/**
 * Created by kuangcheng on 2014/12/4.
 */
public class MyFrameLayout extends FrameLayout {


    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        printModeSize(widthMeasureSpec);
        printModeSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.w("kcc", "getMeasureWidth-->" + getMeasuredWidth() + "  getMeaasreHeight-->" + getMeasuredHeight());
    }

    private static void printModeSize(int measreSpec) {
        int mode = MeasureSpec.getMode(measreSpec);
        int size = MeasureSpec.getSize(measreSpec);

        if(mode == MeasureSpec.AT_MOST) {
            Log.w("kcc", "mode-->AT_Most" );
        } else if(mode == MeasureSpec.UNSPECIFIED) {
            Log.w("kcc", "mode-->UNSPECIFIED" );
        } else if(mode == MeasureSpec.EXACTLY) {
            Log.w("kcc", "mode-->EXACTLY" );
        }

        Log.w("kcc", "size-->" + size);
    }

}
