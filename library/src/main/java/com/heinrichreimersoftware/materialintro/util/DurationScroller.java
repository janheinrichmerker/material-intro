package com.heinrichreimersoftware.materialintro.util;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Custom scroller that multiplies the duration of the scroll
 * by a 'scrollFactor' which can be manually set
 */
public class DurationScroller extends Scroller {

    public DurationScroller(Context context) {
        super(context);
    }

    public DurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public DurationScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    private double mScrollFactor = 1;

    public void setScrollDurationFactor(double scrollDurationFactor) {
        mScrollFactor = scrollDurationFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
    }
}
