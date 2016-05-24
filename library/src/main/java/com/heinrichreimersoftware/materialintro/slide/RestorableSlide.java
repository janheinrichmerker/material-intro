package com.heinrichreimersoftware.materialintro.slide;

import android.support.v4.app.Fragment;

public interface RestorableSlide extends Slide {
    void setFragment(Fragment fragment);
}
