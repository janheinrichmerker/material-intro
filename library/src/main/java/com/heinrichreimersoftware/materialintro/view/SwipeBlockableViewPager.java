package com.heinrichreimersoftware.materialintro.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SwipeBlockableViewPager extends ViewPager {
    @IntDef({SWIPE_DIRECTION_LEFT, SWIPE_DIRECTION_NONE, SWIPE_DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface SwipeDirection {
    }

    private static final int SWIPE_DIRECTION_LEFT = 1;

    private static final int SWIPE_DIRECTION_NONE = 0;

    private static final int SWIPE_DIRECTION_RIGHT = -1;

    private static final int SWIPE_THRESHOLD = 10;

    private float initialX;

    private boolean swipeRightEnabled = true;

    private boolean swipeLeftEnabled = true;

    public SwipeBlockableViewPager(Context context) {
        super(context);
    }

    public SwipeBlockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isSwipeRightEnabled() {
        return swipeRightEnabled;
    }

    public void setSwipeRightEnabled(boolean swipeRightEnabled) {
        this.swipeRightEnabled = swipeRightEnabled;
    }

    public boolean isSwipeLeftEnabled() {
        return swipeLeftEnabled;
    }

    public void setSwipeLeftEnabled(boolean swipeLeftEnabled) {
        this.swipeLeftEnabled = swipeLeftEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getSwipeDirection(event) == SWIPE_DIRECTION_RIGHT && !swipeRightEnabled) {
            return false;
        } else if (getSwipeDirection(event) == SWIPE_DIRECTION_LEFT && !swipeLeftEnabled) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (getSwipeDirection(event) == SWIPE_DIRECTION_RIGHT && !swipeRightEnabled) {
            return false;
        } else if (getSwipeDirection(event) == SWIPE_DIRECTION_LEFT && !swipeLeftEnabled) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @SwipeDirection
    public int getSwipeDirection(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialX = event.getX();
            return SWIPE_DIRECTION_NONE;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE ||
                event.getAction() == MotionEvent.ACTION_UP) {
            float distanceX = event.getX() - initialX;

            if (SWIPE_THRESHOLD > Math.abs(distanceX)) {
                return SWIPE_DIRECTION_NONE;
            }
            if (distanceX > 0) {
                return SWIPE_DIRECTION_RIGHT;
            } else {
                return SWIPE_DIRECTION_LEFT;
            }
        }

        return SWIPE_DIRECTION_NONE;
    }
}
