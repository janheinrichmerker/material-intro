/*
 * MIT License
 *
 * Copyright (c) 2017 Jan Heinrich Reimer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    
    protected void goToFirstSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).goToFirstSlide();
        }
    }

    protected void goToLastSlide() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).goToLastSlide();
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
