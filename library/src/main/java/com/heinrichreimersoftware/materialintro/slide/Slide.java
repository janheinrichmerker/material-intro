package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

public interface Slide {
    Fragment getFragment();

    @ColorRes
    int getBackground();

    @ColorRes
    int getBackgroundDark();

    boolean canGoForward();

    boolean canGoBackward();
}
