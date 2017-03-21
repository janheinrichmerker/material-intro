package com.heinrichreimersoftware.materialintro.slide;

import android.view.View;

public interface SimpleSlideActivity
{
    void onSlideViewCreated(SimpleSlide.SimpleSlideFragment fragment, View view, long id);
    void onSlideDestroyView(SimpleSlide.SimpleSlideFragment fragment, View view, long id);
}
