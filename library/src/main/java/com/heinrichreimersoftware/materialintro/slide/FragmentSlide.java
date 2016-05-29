package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.heinrichreimersoftware.materialintro.view.parallax.Parallaxable;

import java.util.LinkedList;
import java.util.Queue;

public class FragmentSlide implements Slide, RestorableSlide {

    private Fragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;

    private final boolean canGoForward;

    private final boolean canGoBackward;

    private FragmentSlide(Builder builder) {
        fragment = builder.fragment;
        background = builder.background;
        backgroundDark = builder.backgroundDark;
        canGoForward = builder.canGoForward;
        canGoBackward = builder.canGoBackward;
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public int getBackground() {
        return background;
    }

    @Override
    public int getBackgroundDark() {
        return backgroundDark;
    }

    @Override
    public boolean canGoForward() {
        if (getFragment() instanceof SlideFragment) {
            return ((SlideFragment) getFragment()).canGoForward();
        }
        return canGoForward;
    }

    @Override
    public boolean canGoBackward() {
        if (getFragment() instanceof SlideFragment) {
            return ((SlideFragment) getFragment()).canGoBackward();
        }
        return canGoBackward;
    }

    public static class Builder{
        private Fragment fragment;
        @ColorRes
        private int background;
        @ColorRes
        private int backgroundDark = 0;

        private boolean canGoForward = true;

        private boolean canGoBackward = true;

        public Builder fragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder fragment(@LayoutRes int layoutRes, @StyleRes int themeRes) {
            this.fragment = FragmentSlideFragment.newInstance(layoutRes, themeRes);
            return this;
        }

        public Builder fragment(@LayoutRes int layoutRes) {
            this.fragment = FragmentSlideFragment.newInstance(layoutRes);
            return this;
        }

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDark) {
            this.backgroundDark = backgroundDark;
            return this;
        }

        public Builder canGoForward(boolean canGoForward) {
            this.canGoForward = canGoForward;
            return this;
        }

        public Builder canGoBackward(boolean canGoBackward) {
            this.canGoBackward = canGoBackward;
            return this;
        }

        public FragmentSlide build(){
            if(background == 0 || fragment == null)
                throw new IllegalArgumentException("You must set at least a fragment and background.");
            return new FragmentSlide(this);
        }
    }

    public static class FragmentSlideFragment extends Fragment implements Parallaxable {
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";
        private static final String ARGUMENT_THEME_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_THEME_RES";

        @Nullable
        private Parallaxable parallaxLayout;

        public FragmentSlideFragment() {
        }

        public static FragmentSlideFragment newInstance(@LayoutRes int layoutRes, @StyleRes int themeRes) {
            FragmentSlideFragment fragment = new FragmentSlideFragment();

            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_LAYOUT_RES, layoutRes);
            arguments.putInt(ARGUMENT_THEME_RES, themeRes);
            fragment.setArguments(arguments);

            return fragment;
        }

        public static FragmentSlideFragment newInstance(@LayoutRes int layoutRes) {
            return newInstance(layoutRes, 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int themeRes = getArguments().getInt(ARGUMENT_THEME_RES);
            Context contextThemeWrapper;
            if (themeRes != 0) {
                contextThemeWrapper = new ContextThemeWrapper(getActivity(), themeRes);
            } else {
                contextThemeWrapper = getActivity();
            }
            LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

            View root = localInflater.inflate(getArguments().getInt(ARGUMENT_LAYOUT_RES), container, false);
            parallaxLayout = findParallaxLayout(root);
            Log.i("FragmentSlide", "parallaxLayout: " + parallaxLayout);
            return root;
        }

        public Parallaxable findParallaxLayout(View root) {
            Queue<View> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                View child = queue.remove();
                if (child instanceof Parallaxable) {
                    return (Parallaxable) child;
                } else if (child instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) child;
                    for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                        queue.add(viewGroup.getChildAt(i));
                    }
                }
            }
            return null;
        }

        @Override
        public void setOffset(@FloatRange(from = -1.0, to = 1.0) float offset) {
            if (parallaxLayout != null)
                parallaxLayout.setOffset(offset);
        }
    }
}
