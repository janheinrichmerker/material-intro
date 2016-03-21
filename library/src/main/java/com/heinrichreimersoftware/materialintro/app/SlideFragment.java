package com.heinrichreimersoftware.materialintro.app;

import android.support.v4.app.Fragment;

public class SlideFragment extends Fragment {

    public boolean canGoForward() {
        return true;
    }

    public boolean canGoBackward() {
        return true;
    }

    protected void updateNavigation() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).lockSwipeIfNeeded();
        }
    }

    protected void nextSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).nextSlide();
        }
    }

    protected void previousSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).previousSlide();
        }
    }

}
