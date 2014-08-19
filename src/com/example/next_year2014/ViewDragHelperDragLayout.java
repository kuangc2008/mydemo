package com.example.next_year2014;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 问题所在，是其中某个的caputre控件在移动，而并不是说，整个viewGroup在移动
 */
public class ViewDragHelperDragLayout extends ViewGroup implements GestureDetector.OnGestureListener {
    private ViewDragHelper mViewDragHelper = null;
    GestureDetectorCompat mGestureDC = null;
    ScrollerCompat mScrollerCompat = null;

    public ViewDragHelperDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelperCallback());
        mGestureDC = new GestureDetectorCompat(getContext(), this);
        mScrollerCompat = ScrollerCompat.create(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int defaultWidht = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defaultHeight =  getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.w("kcc", "defautlWidht-->" + (defaultWidht) + "  height-->" + defaultHeight);
        setMeasuredDimension(defaultWidht * getChildCount(),
                defaultHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childOne = r/getChildCount();
        for(int i=0; i<getChildCount(); i++) {
            View v = getChildAt(i);
            v.layout(childOne * i, t, childOne * (i+1), b);
            Log.w("kcc", "left-->" + (childOne * i) + "  right-->" + childOne * (i+1));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        mViewDragHelper.shouldInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mViewDragHelper.processTouchEvent(event);
        Log.v("kcc", "onTouchEvent-->");
        if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX() ,0);
            postInvalidate();
            return true;
//            if(!mGestureDC.onTouchEvent(event)) {
//            }
//            return true;
        }
        return mGestureDC.onTouchEvent(event);
    }

    private int getFinalPos() {
        int oneWidht = getMeasuredWidth()/3;
        int p = (getScrollX() + oneWidht/2) /oneWidht;
        return p * oneWidht;
    }

    private boolean isMoveing = false;

    @Override
    public void computeScroll() {
        Log.w("kcc", "computeScroll-->" + isMoveing );
        if(mScrollerCompat.computeScrollOffset() ) {
            Log.v("kcc", "computeScroll-->1" );
            this.scrollTo(mScrollerCompat.getCurrX(), 0);
            postInvalidate();
            isMoveing = true;
            Log.w("kcc", "computeScroll-->" + mScrollerCompat.getCurrX() + " final->" + mScrollerCompat.getFinalX() );
//            if(mScrollerCompat.getCurrX() == mScrollerCompat.getFinalX()) {
//                mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX() ,0);
//                Log.v("kcc", "getScoollX-->" + getScrollX() + " finalPos-->" + getFinalPos());
//                postInvalidate();
//            }
        } else {
            Log.v("kcc", "computeScroll-->2" );
//            if(isMoveing) {
//                mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX() ,0);
//                postInvalidate();
//            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("kcc", "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.e("kcc", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e("kcc", "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e("kcc", "onScroll");
        if(getScrollX() + distanceX < 0) {
            this.scrollTo(0, 0);
        } else if( getScrollX() + distanceX > getMeasuredWidth() * 2/3 ) {
            this.scrollTo(getMeasuredWidth() * 2/ 3, 0);
        } else {
            this.scrollBy((int) distanceX, 0);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.e("kcc", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("kcc", "onFling");
        mScrollerCompat.fling(getScrollX(), 0, (int)velocityX/4, 0, 0, getMeasuredWidth()/3, 0, 0);
        postInvalidate();
        return true;
    }

//    private class ViewDragHelperCallback extends ViewDragHelper.Callback {
//        @Override
//        public boolean tryCaptureView(View child, int pointerId) {
//            return true;
//        }
//
//        @Override
//        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            return  left;
//        }
//
//        @Override
//        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//            invalidate();
//        }
//    }
}