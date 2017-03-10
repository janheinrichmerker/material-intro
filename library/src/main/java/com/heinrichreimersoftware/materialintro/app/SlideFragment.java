package com.heinrichreimersoftware.materialintro.app;

import android.support.v4.app.Fragment;
import android.view.View;

public class SlideFragment extends Fragment {

    public boolean canGoForward() {
        return true;
    }

    public boolean canGoBackward() {
        return true;
    }

    public void updateNavigation() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).lockSwipeIfNeeded();
        }
    }

    public void addOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).addOnNavigationBlockedListener(listener);
        }
    }

    public void removeOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).removeOnNavigationBlockedListener(listener);
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

    public View getContentView() {
        return getActivity().findViewById(android.R.id.content);
    }
}
