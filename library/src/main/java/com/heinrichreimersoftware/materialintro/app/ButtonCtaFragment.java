package com.heinrichreimersoftware.materialintro.app;

import android.support.annotation.StringRes;
import android.view.View;

public interface ButtonCtaFragment {
    View.OnClickListener getButtonCtaClickListener();

    /**
     * Note: you must either define a {@link String} or a {@link StringRes} label
     */
    String getButtonCtaLabel();

    /**
     * Note: you must either define a {@link String} or a {@link StringRes} label
     */
    @StringRes
    int getButtonCtaLabelRes();
}
