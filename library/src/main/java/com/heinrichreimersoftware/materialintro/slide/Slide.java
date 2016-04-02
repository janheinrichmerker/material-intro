package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

public abstract class Slide {

    public abstract Fragment getFragment();

    @ColorRes
    public abstract int getBackground();
    @ColorRes
    public int getBackgroundDark(){
        return android.R.color.black;
    }

    public boolean canGoForward() {
        return true;
    }

    public boolean canGoBackward() {
        return true;
    }
}
