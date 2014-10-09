package com.last201409;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


public class MyHorizontalScrollView extends ViewGroup {
    private Context mContext;
    private int mIndex;
    private Scroller mScoller;
    private GestureDetector mGestureDetector;

    public MyHorizontalScrollView(Context context) {
        super(context);
        mContext = context;
        mGestureDetector = new GestureDetector(context, gestureDetector);
        mScoller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int left_right_show_widht = width/4;
        int leftPlace =  left_right_show_widht * (mIndex + 1);
        for(int i=0 ; i< getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(leftPlace, t, leftPlace + left_right_show_widht * 2   ,b);
            leftPlace +=  left_right_show_widht * 2;
        }
    }

    public void setShowChildIndex(int index) {
        mIndex = index;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if(mScoller.computeScrollOffset()) {
            scrollTo( mScoller.getCurrX(),mScoller.getCurrY() );
            invalidate();
        }
    }


    private void showCenterPos() {
        int position = getScrollX() % getWidth()/2;
    }

    private GestureDetector.SimpleOnGestureListener gestureDetector = new GestureDetector.SimpleOnGestureListener() {
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            scrollBy((int) distanceX, 0);
            showCenterPos();
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }
    };

}
