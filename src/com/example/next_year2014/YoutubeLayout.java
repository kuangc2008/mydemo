package com.example.next_year2014;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
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


    private float mInitialMotionX;
    private float mInitialMotionY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("kcc", "onInterceptTouchEvent-->");
        final int action = MotionEventCompat.getActionMasked(ev);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper .cancel();
            return false ;
        }

        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mHeaderView, (int)x ,(int)y);
                break;
            }
        }

        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("kcc", "onToucheEvent-->");
        mDragHelper.processTouchEvent(event);

        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int)y);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionY = x;
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_UP:
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final int slop = mDragHelper.getTouchSlop();
                if(dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
                    if(mDragOffset == 0) {
                        smoothSlideTo(1f);
                    } else {
                        smoothSlideTo(0f);
                    }
                }
                break;
        }

        return isHeaderViewUnder  && isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);   //因为没有return true，所以一直点击没反应。 因为点击事件，让子控件拿走了。
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    public void minimize() {
        smoothSlideTo(1f);
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int)  (topBound + slideOffset * mDragRange);

        if(mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)) {  //持续触发onViewPositionChnaged
            ViewCompat.postInvalidateOnAnimation(this);
        }
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

        mDragRange = getHeight() - mHeaderView.getHeight();
        Log.v("kcc", "onLayout-->" + " t-->" + t + "   b-->" + b + "   getHeight-->" + getHeight()
                +"  headderViewHeight-->" + mHeaderView.getHeight());
        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }


    private int mTop;
    private float mDragOffset;
    private class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.v("kcc", "tryCaptureView-->" + child);
            return child == mHeaderView;
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            mTop = top;
            mDragOffset = (float) top / mDragRange;
            Log.v("kcc", "onViewPositionChanged-->" + top + "   dy--." + dy+"   mDragOffset-->" + mDragOffset);
            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);   //scale不会影响到height的大小

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
        public int getViewHorizontalDragRange(View child) {   //调用smoothScroolTo时，会回调这个
            Log.v("kcc", "getViewHorizontalDragRange-->" + child);
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.v("kcc", "onViewReleased-->" + yvel);
            int top = getPaddingTop();
            if(yvel>0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);   //难道是因为realease么？
            invalidate();
        }
    }
}
