package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.ButtonCtaFragment;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.heinrichreimersoftware.materialintro.view.parallax.ParallaxFragment;

public class FragmentSlide implements Slide, RestorableSlide, ButtonCtaSlide {

    private Fragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;
    @ColorInt
    private int backgroundColor;
    @ColorInt
    private int backgroundDarkColor;

    private final boolean canGoForward;
    private final boolean canGoBackward;
    private CharSequence buttonCtaLabel = null;
    @StringRes
    private int buttonCtaLabelRes = 0;
    private View.OnClickListener buttonCtaClickListener = null;

    protected FragmentSlide(Builder builder) {
        fragment = builder.fragment;
        background = builder.background;
        backgroundColor = builder.backgroundColor;
        backgroundDark = builder.backgroundDark;
        backgroundDarkColor = builder.backgroundDarkColor;
        canGoForward = builder.canGoForward;
        canGoBackward = builder.canGoBackward;
        buttonCtaLabel = builder.buttonCtaLabel;
        buttonCtaLabelRes = builder.buttonCtaLabelRes;
        buttonCtaClickListener = builder.buttonCtaClickListener;
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
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int getBackgroundDark() {
        return backgroundDark;
    }

    @Override
    public int getBackgroundDarkColor() {
        return backgroundDarkColor;
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

    @Override
    public View.OnClickListener getButtonCtaClickListener() {
        if (getFragment() instanceof ButtonCtaFragment) {
            return ((ButtonCtaFragment) getFragment()).getButtonCtaClickListener();
        }
        return buttonCtaClickListener;
    }

    @Override
    public CharSequence getButtonCtaLabel() {
        if (getFragment() instanceof ButtonCtaFragment) {
            return ((ButtonCtaFragment) getFragment()).getButtonCtaLabel();
        }
        return buttonCtaLabel;
    }

    @Override
    public int getButtonCtaLabelRes() {
        if (getFragment() instanceof ButtonCtaFragment) {
            return ((ButtonCtaFragment) getFragment()).getButtonCtaLabelRes();
        }
        return buttonCtaLabelRes;
    }

    public static class Builder{
        private Fragment fragment;
        @ColorRes
        private int background;
        @ColorRes
        private int backgroundDark = 0;
        @ColorInt
        private int backgroundColor;
        @ColorInt
        private int backgroundDarkColor;

        private boolean canGoForward = true;
        private boolean canGoBackward = true;
        private CharSequence buttonCtaLabel = null;
        @StringRes
        private int buttonCtaLabelRes = 0;
        private View.OnClickListener buttonCtaClickListener = null;

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
        public Builder backgroundColor(@ColorInt int background) {
            this.backgroundColor = background;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDark) {
            this.backgroundDark = backgroundDark;
            return this;
        }

        public Builder backgroundDarkColor(@ColorInt int backgroundDark) {
            this.backgroundDarkColor = backgroundDark;
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

        public Builder buttonCtaLabel(CharSequence buttonCtaLabel) {
            this.buttonCtaLabel = buttonCtaLabel;
            this.buttonCtaLabelRes = 0;
            return this;
        }

        public Builder buttonCtaLabelHtml(String buttonCtaLabelHtml) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.buttonCtaLabel = Html.fromHtml(buttonCtaLabelHtml, Html.FROM_HTML_MODE_LEGACY);
            }
            else {
                //noinspection deprecation
                this.buttonCtaLabel = Html.fromHtml(buttonCtaLabelHtml);
            }
            this.buttonCtaLabelRes = 0;
            return this;
        }

        public Builder buttonCtaLabel(@StringRes int buttonCtaLabelRes) {
            this.buttonCtaLabelRes = buttonCtaLabelRes;
            this.buttonCtaLabel = null;
            return this;
        }

        public Builder buttonCtaClickListener(View.OnClickListener buttonCtaClickListener) {
            this.buttonCtaClickListener = buttonCtaClickListener;
            return this;
        }

        public FragmentSlide build(){
            if((background == 0 && backgroundColor == 0) || fragment == null)
                throw new IllegalArgumentException("You must set at least a fragment and background.");
            return new FragmentSlide(this);
        }
    }

    public static class FragmentSlideFragment extends ParallaxFragment {
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";
        private static final String ARGUMENT_THEME_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_THEME_RES";

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

            return localInflater.inflate(getArguments().getInt(ARGUMENT_LAYOUT_RES), container, false);
        }
    }
}
