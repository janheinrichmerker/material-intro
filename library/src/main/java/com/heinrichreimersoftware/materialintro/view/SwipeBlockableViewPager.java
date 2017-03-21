package com.heinrichreimersoftware.materialintro.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeBlockableViewPager extends ViewPager {

    private static final int SWIPE_LOCK_THRESHOLD = 0;
    private static final int SWIPE_UNLOCK_THRESHOLD = 0;

    private static final String STATE_SUPER = "SUPER";
    private static final String STATE_SWIPE_RIGHT_ENABLED = "SWIPE_RIGHT_ENABLED";
    private static final String STATE_SWIPE_LEFT_ENABLED = "SWIPE_LEFT_ENABLED";

    private static final int INVALID_POINTER_ID = -1;

    private int activePointerId = INVALID_POINTER_ID;
    private float lastTouchX;

    private boolean swipeRightEnabled = true;
    private boolean swipeLeftEnabled = true;

    private boolean lockedLeft = false;
    private boolean lockedRight = false;

    public SwipeBlockableViewPager(Context context) {
        super(context);
    }

    public SwipeBlockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeRightEnabled(boolean swipeRightEnabled) {
        this.swipeRightEnabled = swipeRightEnabled;
    }

    public void setSwipeLeftEnabled(boolean swipeLeftEnabled) {
        this.swipeLeftEnabled = swipeLeftEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return handleTouchEvent(event) && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return handleTouchEvent(event) && super.onInterceptTouchEvent(event);
    }

    private boolean handleTouchEvent(MotionEvent event) {
        boolean allowTouch = false;
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                lastTouchX = event.getX();

                // Save the ID of this pointer
                activePointerId = event.getPointerId(0);

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex = event.findPointerIndex(activePointerId);
                final float x = event.getX(pointerIndex);

                final float dx = x - lastTouchX;

                if (dx > 0) {
                    // Swiped right
                    if (!swipeRightEnabled && Math.abs(dx) > SWIPE_LOCK_THRESHOLD) {
                        lockedRight = true;
                    }
                    if (!lockedRight) {
                        allowTouch = true;
                        if (Math.abs(dx) > SWIPE_UNLOCK_THRESHOLD) {
                            lockedLeft = false;
                        }
                    }
                } else if (dx < 0) {
                    // Swiped left
                    if (!swipeLeftEnabled && Math.abs(dx) > SWIPE_LOCK_THRESHOLD) {
                        lockedLeft = true;
                    }
                    if (!lockedLeft) {
                        allowTouch = true;
                        if (Math.abs(dx) > SWIPE_UNLOCK_THRESHOLD) {
                            lockedRight = false;
                        }
                    }
                }

                lastTouchX = x;

                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP: {
                activePointerId = INVALID_POINTER_ID;
                lockedLeft = false;
                lockedRight = false;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                lockedLeft = false;
                lockedRight = false;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = event.getX(newPointerIndex);
                    activePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return (!lockedLeft && !lockedRight) || allowTouch;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            swipeRightEnabled = bundle.getBoolean(STATE_SWIPE_RIGHT_ENABLED, true);
            swipeLeftEnabled = bundle.getBoolean(STATE_SWIPE_LEFT_ENABLED, true);
            state = bundle.getParcelable(STATE_SUPER);
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle(4);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        bundle.putBoolean(STATE_SWIPE_RIGHT_ENABLED, swipeRightEnabled);
        bundle.putBoolean(STATE_SWIPE_LEFT_ENABLED, swipeLeftEnabled);
        return bundle;
    }
}
