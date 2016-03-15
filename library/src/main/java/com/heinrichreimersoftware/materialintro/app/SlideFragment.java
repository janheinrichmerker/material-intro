package com.heinrichreimersoftware.materialintro.app;

import android.support.v4.app.Fragment;

/**
 * Created by juced on 15.03.2016.
 */
public class SlideFragment extends Fragment {

    protected void allowNextSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).setAllowNextForSlideByFragmentTag(getTag());
        }
    }

    protected void allowPreviousSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).setAllowPreviousForSlideByFragmentTag(getTag());
        }
    }

    protected void showNextSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).nextSlide(true);
        }
    }

    protected void showPreviousSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).previousSlide(true);
        }
    }

}
