package com.example.next_year2014;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by kuangcheng on 2014/7/18.
 */
public class DrawLayout extends LinearLayout{
    private ViewDragHelper mDragHelper;
    private View mDragView;


    public DrawLayout(Context context) {
        this(context, null);
    }

    public DrawLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //1.0正常，越大则越容易检测开始滑动
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DraghelpercallBack());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    public class DraghelpercallBack extends ViewDragHelper.Callback {
        private boolean isBringToFront = false;

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth()  - mDragView.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound),rightBound );
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int newTop = 0;
            if(top<getPaddingTop()) {
                newTop = getPaddingTop();
            } else if(top > getHeight() - getPaddingBottom() - mDragView.getHeight()) {
                newTop = getHeight() - getPaddingBottom() - mDragView.getHeight();
            } else {
                newTop = top;
            }
            return  newTop;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            invalidate();
            super.onViewReleased(releasedChild, xvel, yvel);
            isBringToFront = false;
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            Log.v("kcc", "trycaptureView-->" + view + "  i-->" + i);
            mDragView = view;
            bringToFront(view);
            return true;
        }

        public void bringToFront(View childView) {
            if(!isBringToFront) {
                int childPosition =0;
                for(int i=0; i<getChildCount(); i++) {
                    View v = getChildAt(i);
                    if(v == childView) {
                        childPosition = i;
                        break;
                    }
                }
                if(childPosition != getChildCount() - 1) {
                    childView.bringToFront();
                }
                isBringToFront = true;
            }
        }
    }


}
