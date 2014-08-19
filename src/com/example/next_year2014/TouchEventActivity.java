package com.example.next_year2014;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/8/18.
 */
public class TouchEventActivity extends Activity {
    RootView rootView = null;
    View1 view1 = null;
    View2 view2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = new RootView(this);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        ChildRootView childRootView = new ChildRootView();

        view1 = new View1(this);
        rootView.addView(view1, new ViewGroup.LayoutParams(600, 600));

        view2 = new View2(this);
        rootView.addView(view2, new ViewGroup.LayoutParams(400, 400));

        setContentView(rootView);
    }




    private class RootView extends ViewGroup {
        public boolean onInterceptTouchEvent;
        public boolean onTouchEvent;

        public RootView(Context context) {
            super(context);
            setBackgroundColor(getResources().getColor(R.color.holo_orange_light));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           measureChildren(widthMeasureSpec, heightMeasureSpec);
           setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
           for(int i=0; i<getChildCount(); i++) {
               View child = getChildAt(i);
               child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
           }
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.v("kcc", "RootView onInterceptTouchEvent return->" + onInterceptTouchEvent);
            return onInterceptTouchEvent;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.v("kcc", "RootView onTouchEvent return->" + onTouchEvent);
            return onTouchEvent;
        }
    }

    private class ChildRootView extends ViewGroup {

        public ChildRootView(Context context) {
            super(context);
            setBackgroundColor(getResources().getColor(R.color.holo_blue_bright));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            for(int i=0; i<getChildCount(); i++) {
                View child = getChildAt(i);
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            Log.v("kcc", "RootView dispatchTouchEvent return->" + true);
            return true;
//            return super.dispatchTouchEvent(event);

        }
    }


    private class View1 extends View {
        public boolean onInterceptTouchEvent;
        public boolean onTouchEvent;

        public View1(Context context) {
            super(context);
            setBackgroundColor(getResources().getColor(R.color.holo_red_light));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.v("kcc", "View1 onTouchEvent return->" + onTouchEvent);
            return onTouchEvent;
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            Log.v("kcc", "View1 dispatchTouchEvent return->" + false);
            return false;
//            return super.dispatchTouchEvent(event);
        }
    }

    private class View2 extends View {
        public boolean onInterceptTouchEvent;
        public boolean onTouchEvent;

        public View2(Context context) {
            super(context);
            setBackgroundColor(getResources().getColor(R.color.holo_green_light));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.v("kcc", "View2 onTouchEvent return->" + onTouchEvent);
            return onTouchEvent;
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            Log.v("kcc", "View2 dispatchTouchEvent return->" + false);
            return false;
//            return super.dispatchTouchEvent(event);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "RootView onInterceptTouchEvent " + rootView.onInterceptTouchEvent);
        menu.add(1, 1, 0, "RootView onTouchEvent open" + rootView.onTouchEvent);
        menu.add(2, 2, 0, "View1 onTouchEvent open" + view1.onTouchEvent);
        menu.add(3, 3, 0, "View2 onTouchEvent open" + view2.onTouchEvent);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                rootView.onInterceptTouchEvent = !rootView.onInterceptTouchEvent;
                break;
            case 1:
                rootView.onTouchEvent =  !rootView.onTouchEvent;
                break;
            case 2:
                view1.onTouchEvent =  !view1.onTouchEvent;
                break;
            case 3:
                view2.onTouchEvent =  !view2.onTouchEvent;
                break;
        }
        invalidateOptionsMenu();
        return true;
    }
}
