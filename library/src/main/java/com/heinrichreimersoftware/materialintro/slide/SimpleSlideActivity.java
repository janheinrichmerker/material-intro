package com.heinrichreimersoftware.materialintro.slide;

import android.view.View;

/**
 * Created by flisar on 16.03.2017.
 */

public interface SimpleSlideActivity
{
    void onSlideViewCreated(SimpleSlide.SimpleSlideFragment fragment, View view, int id);
    void onSlideDestroyView(SimpleSlide.SimpleSlideFragment fragment, View view, int id);
}
