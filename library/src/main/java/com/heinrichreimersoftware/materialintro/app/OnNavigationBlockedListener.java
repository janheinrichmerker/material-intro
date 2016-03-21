package com.heinrichreimersoftware.materialintro.app;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface OnNavigationBlockedListener {
    @IntDef({DIRECTION_FORWARD, DIRECTION_BACKWARD})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    int DIRECTION_FORWARD = 1;

    int DIRECTION_BACKWARD = -1;

    void onNavigationBlocked(int position, @Direction int direction);
}
