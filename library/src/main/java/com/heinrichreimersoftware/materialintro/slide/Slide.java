package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

public abstract class Slide {
    public abstract Fragment getFragment();
    @ColorRes
    public abstract int getBackground();
    @ColorRes
    public int getBackgroundDark(){
        return 0;
    }

    private int position;

    private boolean allowSlideNext = true;
    private boolean allowSlidePrevious = true;

    public boolean isAllowSlideNext() {
        return allowSlideNext;
    }

    public boolean isAllowSlidePrevious() {
        return allowSlidePrevious;
    }

    public void setAllowSlidePrevious(boolean allowSlidePrevious) {
        this.allowSlidePrevious = allowSlidePrevious;
    }

    public void setAllowSlideNext(boolean allowSlideNext) {
        this.allowSlideNext = allowSlideNext;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
