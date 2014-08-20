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
import android.view.ViewTreeObserver;

/**
 * 问题所在，是其中某个的caputre控件在移动，而并不是说，整个viewGroup在移动
 */
public class ViewDragHelperDragLayout extends ViewGroup implements GestureDetector.OnGestureListener {
    private ViewDragHelper mViewDragHelper = null;
    GestureDetectorCompat mGestureDC = null;
    ScrollerCompat mScrollerCompat = null;
    private int mCloseEnough;
    private static final int CLOSE_ENOUGH = 2; // dp

    public ViewDragHelperDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelperCallback());
        mGestureDC = new GestureDetectorCompat(getContext(), this);
        mScrollerCompat = ScrollerCompat.create(getContext());
        this.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
//                Log.v("kcc", "getScrooller-->" + getScrollX());
            }
        });
        mCloseEnough = (int) (CLOSE_ENOUGH * getResources().getDisplayMetrics().density);
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

    boolean isDrag = false;
    int pointDownX = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        mViewDragHelper.shouldInterceptTouchEvent(ev);

        if(ev.getAction() == MotionEvent.ACTION_DOWN ) {
            pointDownX = (int) ev.getX();
            return false;
        }
        if( Math.abs(ev.getX() - pointDownX) > mCloseEnough ) {
            ev.setAction(MotionEvent.ACTION_DOWN);
            mGestureDC.onTouchEvent(ev);
            return true;
        }
        return isDrag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mViewDragHelper.processTouchEvent(event);
        Log.v("kcc", "onTouchEvent-->" + event.getAction());
        boolean result = mGestureDC.onTouchEvent(event);
        if(!result) {
            if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX() ,0);
                postInvalidate();
                return true;
            }
        }
        return result;
    }

    private int getFinalPos() {
        int oneWidht = getMeasuredWidth()/3;
        int p = (getScrollX() + oneWidht/2) /oneWidht;
        return p * oneWidht;
    }

    private boolean isFlinger = false;

    @Override
    public void computeScroll() {
        if(mScrollerCompat.computeScrollOffset() ) {
            this.scrollTo(mScrollerCompat.getCurrX(), 0);
            postInvalidate();
            Log.w("kcc", "computeScroll-->" + mScrollerCompat.getCurrX() + " final->" + mScrollerCompat.getFinalX() );
//            if(mScrollerCompat.getCurrX() == mScrollerCompat.getFinalX()) {
//                mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX() ,0);
//                Log.v("kcc", "getScoollX-->" + getScrollX() + " finalPos-->" + getFinalPos());
//                postInvalidate();
//            }
        } else {
            if(isFlinger) {
                Log.v("kcc", "flinger end-->" + getScrollX() + "  final-->" + getFinalPos());

                if(getScrollX() % (getMeasuredWidth()/3) != 0) {
                    mScrollerCompat.startScroll(getScrollX(), 0, getFinalPos() - getScrollX(), 0);
                }
                postInvalidate();
                isFlinger = false;
            }
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
        Log.e("kcc", "onScroll" + getScrollX() + "  distanceX->" + distanceX);
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
        if(velocityX > 0) {
            // 从左向右滑
         //   mScrollerCompat.fling(getScrollX(), 0, -(int)velocityX, - getMeasuredWidth()*2/3  + getScrollX(),  0, 0,  0, 0);
            mScrollerCompat.fling(getScrollX(), 0, -(int)velocityX,0, 0 , getScrollX(), 0,  0);
        } else {
            //从右向左滑
            //mScrollerCompat.fling(getScrollX(), 0, -(int)velocityX, 0,  0,  getMeasuredWidth()*2/3 - getScrollX(), 0,  0);
            //startX :  startX+速度       min: 最小距离   max： 最大距离。
            //先min（1, 3)  再max( o, 2)
            mScrollerCompat.fling(getScrollX(), 0, -(int)velocityX, 0,  getScrollX(),  getMeasuredWidth()*2/3 , 0,  0);
        }

        Log.e("kcc", "onFling" + getScrollX() + "  final-->" + mScrollerCompat.getFinalX() + "  spped->" + velocityX);
        postInvalidate();
        isFlinger = true;
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