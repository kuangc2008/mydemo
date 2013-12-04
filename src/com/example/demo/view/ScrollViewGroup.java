package com.example.demo.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.Toast;


public class ScrollViewGroup extends ViewGroup implements OnGestureListener {
    private Context mContext;
    private Scroller mScroller;
    private GestureDetector detector;
    private int mTouchSlop;
    
    public ScrollViewGroup(Context context) {
        super(context);
        mContext = context;
        setBackgroundResource(android.R.drawable.alert_light_frame);
        mScroller = new Scroller(context);
        detector = new GestureDetector(context, this);
        
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        
        for(int i=0; i<50; i++) {
            final Button mButton = new Button(context);
            mButton.setText("" + (i+1));
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mButton.getText().toString(), 1000).show();
                }
            });
            addView(mButton);
        }
    }
    

    public ScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), 3000);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        int childLeft = 0;
        final int count = getChildCount();
        for(int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            //虽然没有调用，好像并不影响！
//            child.measure(r-l, b-t);
            child.layout(childLeft, childTop, childLeft+300, childTop + 100);
            childTop += 80;
        }
        
        MAXMOVE = getHeight();
    }
    
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;
    private int mTouchState = TOUCH_STATE_REST;
    float mLastMotionY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST: TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_MOVE:
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                boolean yMoved = yDiff > mTouchSlop;
                if(yMoved) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    int move;  //移动距离
    int up_excess_move = 0;  //向上多移动的距离
    int down_excess_move = 0;
    int MAXMOVE = 850;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float y = event.getY();
        move = getScrollY();
        Log.v("kc", "move onTouchEvent" + move);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                    move = mScroller.getFinalY();
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount() == 1) {
//                    int deltay = 0;
//                    deltay = (int) (mLastMotionY - y);
//                    mLastMotionY = y;
//                    Log.d("kc", "move-->" + move);
//                    if(deltay < 0) {  //向下移动
//                        scrollBy(0, deltay);
//                        if(move < 0) {
//                            up_excess_move = -move;
//                        }
                        
//                        if(move > 0) {
//                            int move_this = Math.max(-move, deltay);
//                            scrollBy(0, move_this);
//                        } else if(move == 0) {
//                            Log.v("kc", "down-excess_move" + down_excess_move);
//                            down_excess_move = down_excess_move - deltay/2;
//                            scrollBy(0, deltay/2);
//                        } else if(up_excess_move > 0) { //之前有上移过头
//                            if(up_excess_move >= (-deltay)) {
//                                up_excess_move = up_excess_move + deltay;
//                                scrollBy(0, deltay);
//                            } else {
//                                up_excess_move = 0;
//                                scrollBy(0, deltay);
//                            }
//                        } 
//                    } else if(deltay > 0) {
//                        scrollBy(0, deltay);
//                        if(move - MAXMOVE > 0) {
//                            down_excess_move = move - MAXMOVE;
//                        }
//                        // 上移
//                        if (down_excess_move == 0) {
//                            if (MAXMOVE - move > 0) {
//                                int move_this = Math.min(MAXMOVE - move, deltay);
//                                scrollBy(0, move_this);
//                            } else if (MAXMOVE - move == 0) {
//                                if (up_excess_move <= 100) {
//                                    up_excess_move = up_excess_move + deltay / 2;
//                                    scrollBy(0, deltay / 2);
//                                }
//                            }
//                        } else if (down_excess_move > 0) {
//                            if (down_excess_move >= deltay) {
//                                down_excess_move = down_excess_move - deltay;
//                                scrollBy(0, deltay);
//                            } else {
//                                down_excess_move = 0;
//                                scrollBy(0, down_excess_move);
//                            }
//                        }
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // 向上移动
                if (up_excess_move > 0) {
                    scrollBy(0, up_excess_move);
                    invalidate();
                    up_excess_move = 0;
                }
                
                // 向下移动
                if (down_excess_move > 0) {
                    scrollBy(0, -down_excess_move);
                    invalidate();
                    down_excess_move = 0;
                }
                mTouchState = TOUCH_STATE_REST;
                break;
            default:
                break;
        }
        return this.detector.onTouchEvent(event);
    }

    
    private int down;
    @Override
    public boolean onDown(MotionEvent e) {
        down = (int) e.getY();
        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {
        
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    int lastY = 0;
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.v("kc", "e1" + e1  + "   e2-" + e2 + "   distanceX-" + distanceX + "  distanceY-" + distanceY);
        move = getScrollY();
       
        mScroller.startScroll(0,move, 0,lastY==0?0: ((int)e2.getY() - lastY));
        lastY = (int) e2.getY();
        computeScroll();
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {
        
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("onFling", "onFling");
        if (up_excess_move == 0 && down_excess_move == 0) {
            move = getScrollY();
            int slow = -(int) velocityY * 3 / 4;
            mScroller.fling(0, move, 0, slow, 0, 0, 0, MAXMOVE);
            mScroller.getFinalY();
            computeScroll();
        }
        return false;
    }

}
