package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSlide extends Slide{

    private final android.support.v4.app.Fragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;

    private FragmentSlide(Builder builder) {
        fragment = builder.fragment;
        background = builder.background;
        backgroundDark = builder.backgroundDark;
    }

    @Override
    public android.support.v4.app.Fragment getFragment() {
        return fragment;
    }

    @Override
    public int getBackground() {
        return background;
    }

    @Override
    public int getBackgroundDark() {
        return backgroundDark;
    }

    public static class Builder{
        private android.support.v4.app.Fragment fragment;
        @ColorRes
        private int background;
        @ColorRes
        private int backgroundDark = 0;

        public Builder fragment(android.support.v4.app.Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder fragment(@LayoutRes int layoutRes, @StyleRes int themeRes) {
            this.fragment = Fragment.newInstance(layoutRes, themeRes);
            return this;
        }

        public Builder fragment(@LayoutRes int layoutRes) {
            this.fragment = Fragment.newInstance(layoutRes);
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

        public FragmentSlide build(){
            if(background == 0 || fragment == null)
                throw new IllegalArgumentException("You must set at least a fragment and background.");
            return new FragmentSlide(this);
        }
    }

    public static class Fragment extends android.support.v4.app.Fragment{
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";
        private static final String ARGUMENT_THEME_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_THEME_RES";

        public Fragment() {
        }

        public static Fragment newInstance(@LayoutRes int layoutRes, @StyleRes int themeRes) {
            Fragment fragment = new Fragment();

            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_LAYOUT_RES, layoutRes);
            arguments.putInt(ARGUMENT_THEME_RES, themeRes);
            fragment.setArguments(arguments);

            return fragment;
        }

        public static Fragment newInstance(@LayoutRes int layoutRes) {
            return newInstance(layoutRes, -1);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Context contextThemeWrapper = new ContextThemeWrapper(getActivity(),
                    getArguments().getInt(ARGUMENT_THEME_RES));
            LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

            return localInflater.inflate(getArguments().getInt(ARGUMENT_LAYOUT_RES), container, false);
        }
    }
}
