package com.example.next_year2014;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

public class YoutubeLayout extends ViewGroup {
    private final ViewDragHelper mDragHelper;

    private View mHeaderView;
    private View mDescView;
    private int mDragRange;



    public YoutubeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {
        mHeaderView = findViewById(R.id.header);
        mDescView = findViewById(R.id.desc);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("kcc", "onInterceptTouchEvent-->");
        final int action = MotionEventCompat.getActionMasked(ev);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper .cancel();
            return false ;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("kcc", "onToucheEvent-->");
        mDragHelper.processTouchEvent(event);
        return true;   //因为没有return true，所以一直点击没反应。 因为点击事件，让子控件拿走了。
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidht = MeasureSpec.getSize(widthMeasureSpec);
        int maxheight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(maxWidht, widthMeasureSpec, 0),
                resolveSizeAndState(maxheight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v("kcc", "onLayout-->" + " t-->" + t + "   b-->" + b);
        mDragRange = getHeight() - mHeaderView.getHeight();

        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());

        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }


    private int mTop;
    private float mDragOffset;
    private class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.v("kcc", "tryCaptureView-->" + child);
            return true;
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.v("kcc", "onViewPositionChanged-->" + top + "   dy--." + dy);
            mTop = top;
            mDragOffset = (float) top / mDragRange;

            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);

            mDescView.setAlpha(1 - mDragOffset);

            requestLayout();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.v("kcc", "clampViewPositionVertical-->" + top + "   dy--." + dy);
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            Log.v("kcc", "getViewVerticalDragRange-->" + child);
//            return mDragRange;
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.v("kcc", "onViewReleased-->" + yvel);
            int top = getPaddingTop();
            if(yvel>0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
            invalidate();
        }
    }
}
