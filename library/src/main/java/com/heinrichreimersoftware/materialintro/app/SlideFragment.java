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

    public IntroActivity getIntroActivity() {
        if (getActivity() instanceof IntroActivity) {
            return (IntroActivity) getActivity();
        } else {
            throw new IllegalStateException("SlideFragment's must be attached to an IntroActivity.");
        }
    }

    public void updateNavigation() {
        getIntroActivity().lockSwipeIfNeeded();
    }

    public void addOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        getIntroActivity().addOnNavigationBlockedListener(listener);
    }

    public void removeOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        getIntroActivity().removeOnNavigationBlockedListener(listener);
    }

    protected void nextSlide() {
        getIntroActivity().nextSlide();
    }

    protected void previousSlide() {
        getIntroActivity().previousSlide();
    }

    /**
     * @deprecated
     */
    public View getContentView() {
        return getActivity().findViewById(android.R.id.content);
    }
}
