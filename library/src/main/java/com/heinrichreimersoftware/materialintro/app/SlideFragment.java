package com.heinrichreimersoftware.materialintro.app;

import android.support.v4.app.Fragment;
import android.view.View;

public class SlideFragment extends Fragment implements IntroNavigation {

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

    @Override
    public boolean goToSlide(int position) {
        return getIntroActivity().goToSlide(position);
    }

    @Override
    public boolean nextSlide() {
        return getIntroActivity().nextSlide();
    }

    @Override
    public boolean previousSlide() {
        return getIntroActivity().previousSlide();
    }

    @Override
    public boolean goToLastSlide() {
        return getIntroActivity().goToLastSlide();
    }

    @Override
    public boolean goToFirstSlide() {
        return getIntroActivity().goToFirstSlide();
    }

    /**
     * @deprecated
     */
    public View getContentView() {
        return getActivity().findViewById(android.R.id.content);
    }
}
