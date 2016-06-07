package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.StringRes;
import android.view.View;

public interface ButtonCtaSlide extends Slide {
    View.OnClickListener getButtonCtaClickListener();

    /**
     * Note: you must either define a {@link CharSequence} or a {@link StringRes} label
     */
    CharSequence getButtonCtaLabel();

    /**
     * Note: you must either define a {@link CharSequence} or a {@link StringRes} label
     */
    @StringRes
    int getButtonCtaLabelRes();
}
