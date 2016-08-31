package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
    private final boolean canGoForward;
    private final boolean canGoBackward;
    private CharSequence buttonCtaLabel = null;
    @StringRes
    private int buttonCtaLabelRes = 0;
    private View.OnClickListener buttonCtaClickListener = null;

    protected FragmentSlide(Builder builder) {
        fragment = builder.fragment;
        background = builder.background;
        backgroundDark = builder.backgroundDark;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FragmentSlide that = (FragmentSlide) o;

        if (background != that.background) return false;
        if (backgroundDark != that.backgroundDark) return false;
        if (canGoForward != that.canGoForward) return false;
        if (canGoBackward != that.canGoBackward) return false;
        if (buttonCtaLabelRes != that.buttonCtaLabelRes) return false;
        if (fragment != null ? !fragment.equals(that.fragment) : that.fragment != null)
            return false;
        if (buttonCtaLabel != null ? !buttonCtaLabel.equals(that.buttonCtaLabel) : that.buttonCtaLabel != null)
            return false;
        return buttonCtaClickListener != null ? buttonCtaClickListener.equals(that.buttonCtaClickListener) : that.buttonCtaClickListener == null;

    }

    @Override
    public int hashCode() {
        int result = fragment != null ? fragment.hashCode() : 0;
        result = 31 * result + background;
        result = 31 * result + backgroundDark;
        result = 31 * result + (canGoForward ? 1 : 0);
        result = 31 * result + (canGoBackward ? 1 : 0);
        result = 31 * result + (buttonCtaLabel != null ? buttonCtaLabel.hashCode() : 0);
        result = 31 * result + buttonCtaLabelRes;
        result = 31 * result + (buttonCtaClickListener != null ? buttonCtaClickListener.hashCode() : 0);
        return result;
    }

    public static class Builder{
        private Fragment fragment;
        @ColorRes
        private int background;
        @ColorRes
        private int backgroundDark = 0;
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
            if(background == 0 || fragment == null)
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
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_LAYOUT_RES, layoutRes);
            arguments.putInt(ARGUMENT_THEME_RES, themeRes);

            FragmentSlideFragment fragment = new FragmentSlideFragment();
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
