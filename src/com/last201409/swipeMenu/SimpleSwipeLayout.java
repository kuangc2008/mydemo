package com.last201409.swipeMenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SimpleSwipeLayout extends ViewGroup {

    public SimpleSwipeLayout(Context context) {
        super(context);
        setBackgroundColor(0xffff0000);
        setMinimumHeight(100);
        TextView tv = new TextView(getContext());
        tv.setBackgroundColor(0xff00ff00);
        tv.setText("hhhhhhhhhhhhhhhhhhhhhh");
        addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for(int i=0; i<getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();

            childView.measure(params.width, params.height);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lastLeft = 0;
        for(int i=0; i<getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(lastLeft, t, lastLeft + childView.getMeasuredWidth(), b);
            lastLeft += childView.getMeasuredWidth();
        }
    }

    public void addButton(String text) {
        Button button = new Button(getContext());
        button.setText(text);
        button.setBackgroundColor(0xff0000ff);
        addView(button);
        requestLayout();
        invalidate();
    }

}
