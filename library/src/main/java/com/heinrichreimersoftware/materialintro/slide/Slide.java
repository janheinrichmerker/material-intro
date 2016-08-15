package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

public interface Slide {
    Fragment getFragment();

    @ColorRes
    int getBackground();

    @ColorInt
    int getBackgroundColor();

    @ColorRes
    int getBackgroundDark();

    @ColorInt
    int getBackgroundDarkColor();

    boolean canGoForward();

    boolean canGoBackward();
}
