package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.R;
import com.heinrichreimersoftware.materialintro.view.parallax.ParallaxSlideFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlide implements Slide, RestorableSlide, ButtonCtaSlide {

    private static final int DEFAULT_PERMISSIONS_REQUEST_CODE = 34; //Random number
    private SimpleSlideFragment fragment;
    @ColorRes
    private final int background;
    @ColorInt
    private final int backgroundColor;
    @ColorRes
    private final int backgroundDark;
    @ColorInt
    private final int backgroundDarkColor;
    private final boolean canGoForward;
    private final boolean canGoBackward;
    private String[] permissions;
    private int permissionsRequestCode;
    private CharSequence buttonCtaLabel = null;
    @StringRes
    private int buttonCtaLabelRes = 0;
    private View.OnClickListener buttonCtaClickListener = null;

    protected SimpleSlide(Builder builder) {
        fragment = SimpleSlideFragment.newInstance(builder.title, builder.titleRes,
                builder.description, builder.descriptionRes, builder.imageRes,
                builder.background, builder.backgroundColor, builder.layoutRes, builder.permissionsRequestCode);
        background = builder.background;
        backgroundColor = builder.backgroundColor;
        backgroundDarkColor = builder.backgroundDarkColor;
        backgroundDark = builder.backgroundDarkRes;
        canGoForward = builder.canGoForward;
        canGoBackward = builder.canGoBackward;
        permissions = builder.permissions;
        permissionsRequestCode = builder.permissionsRequestCode;
        buttonCtaLabel = builder.buttonCtaLabel;
        buttonCtaLabelRes = builder.buttonCtaLabelRes;
        buttonCtaClickListener = builder.buttonCtaClickListener;
        updatePermissions();
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void setFragment(Fragment fragment) {
        if (fragment instanceof SimpleSlideFragment)
            this.fragment = (SimpleSlideFragment) fragment;
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
        updatePermissions();
        return canGoForward && permissions == null;

    }

    @Override
    public boolean canGoBackward() {
        return canGoBackward;
    }

    @Override
    public View.OnClickListener getButtonCtaClickListener() {
        updatePermissions();
        if (permissions == null) {
            return buttonCtaClickListener;
        }
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getActivity() != null)
                    ActivityCompat.requestPermissions(fragment.getActivity(), permissions,
                            permissionsRequestCode);
            }
        };
    }

    @Override
    public CharSequence getButtonCtaLabel() {
        updatePermissions();
        if (permissions == null) {
            return buttonCtaLabel;
        }
        Context context = fragment.getContext();
        if (context != null)
            return context.getResources().getQuantityText(
                    R.plurals.mi_label_grant_permission, permissions.length);
        return null;
    }

    @Override
    public int getButtonCtaLabelRes() {
        updatePermissions();
        if (permissions == null) {
            return buttonCtaLabelRes;
        }
        return 0;
    }

    private synchronized void updatePermissions() {
        if (permissions != null) {
            final List<String> permissionsNotGranted = new ArrayList<>();
            for (String permission : permissions) {
                if (fragment.getContext() == null ||
                        ContextCompat.checkSelfPermission(fragment.getContext(), permission) !=
                                PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission);
                }
            }

            if (permissionsNotGranted.size() > 0) {
                permissions = permissionsNotGranted.toArray(
                        new String[permissionsNotGranted.size()]);
            } else {
                permissions = null;
            }
        } else {
            permissions = null;
        }
    }

    public static class Builder {
        @ColorRes
        private int background = 0;
        @ColorInt
        private int backgroundColor;
        @ColorRes
        private int backgroundDarkRes = 0;
        @ColorInt
        private int backgroundDarkColor;
        private CharSequence title = null;
        @StringRes
        private int titleRes = 0;
        private CharSequence description = null;
        @StringRes
        private int descriptionRes = 0;
        @DrawableRes
        private int imageRes = 0;
        @LayoutRes
        private int layoutRes = R.layout.fragment_simple_slide;
        private boolean canGoForward = true;
        private boolean canGoBackward = true;
        private String[] permissions = null;
        private CharSequence buttonCtaLabel = null;
        @StringRes
        private int buttonCtaLabelRes = 0;
        private View.OnClickListener buttonCtaClickListener = null;

        private int permissionsRequestCode = DEFAULT_PERMISSIONS_REQUEST_CODE;

        public Builder background(@ColorRes int backgroundRes) {
            this.background = backgroundRes;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDarkRes) {
            this.backgroundDarkRes = backgroundDarkRes;
            return this;
        }

        public Builder title(CharSequence title) {
            this.title = title;
            this.titleRes = 0;
            return this;
        }

        public Builder titleHtml(String titleHtml) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.title = Html.fromHtml(titleHtml, Html.FROM_HTML_MODE_LEGACY);
            }
            else {
                //noinspection deprecation
                this.title = Html.fromHtml(titleHtml);
            }
            this.titleRes = 0;
            return this;
        }

        public Builder title(@StringRes int titleRes) {
            this.titleRes = titleRes;
            this.title = null;
            return this;
        }

        public Builder description(CharSequence description) {
            this.description = description;
            this.descriptionRes = 0;
            return this;
        }

        public Builder descriptionHtml(String descriptionHtml) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.description = Html.fromHtml(descriptionHtml, Html.FROM_HTML_MODE_LEGACY);
            }
            else {
                //noinspection deprecation
                this.description = Html.fromHtml(descriptionHtml);
            }
            this.descriptionRes = 0;
            return this;
        }

        public Builder description(@StringRes int descriptionRes) {
            this.descriptionRes = descriptionRes;
            this.description = null;
            return this;
        }

        public Builder image(@DrawableRes int imageRes) {
            this.imageRes = imageRes;
            return this;
        }

        public Builder layout(@LayoutRes int layoutRes) {
            this.layoutRes = layoutRes;
            return this;
        }

        public Builder scrollable(boolean scrollable) {
            this.layoutRes = scrollable ? R.layout.fragment_simple_slide_scrollable :
                    R.layout.fragment_simple_slide;
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

        public Builder permissions(String[] permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder permission(String permission) {
            this.permissions = new String[]{permission};
            return this;
        }

        public Builder permissionsRequestCode(int permissionsRequestCode) {
            this.permissionsRequestCode = permissionsRequestCode;
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

        public Builder backgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundDarcColor(@ColorInt int backgroundDarcColor) {
            this.backgroundDarkColor = backgroundDarcColor;
            return this;
        }

        public SimpleSlide build() {
            if (background == 0 && backgroundColor == 0)
                throw new IllegalArgumentException("You must set a background.");
            return new SimpleSlide(this);
        }
    }

    public static class SimpleSlideFragment extends ParallaxSlideFragment {
        private static final String ARGUMENT_TITLE =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_TITLE";
        private static final String ARGUMENT_TITLE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_TITLE_RES";
        private static final String ARGUMENT_DESCRIPTION =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_DESCRIPTION";
        private static final String ARGUMENT_DESCRIPTION_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_DESCRIPTION_RES";
        private static final String ARGUMENT_IMAGE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_IMAGE_RES";
        private static final String ARGUMENT_BACKGROUND_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_RES";
        private static final String ARGUMENT_BACKGROUND_COLOR =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_COLOR";
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";

        private static final String ARGUMENT_PERMISSIONS_REQUEST_CODE =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_PERMISSIONS_REQUEST_CODE";
        /**
         * For access from child
         */
        private TextView titleView;
        private TextView descriptionView;
        private ImageView imageView;

        public SimpleSlideFragment() {
        }

        public static SimpleSlideFragment newInstance(CharSequence title, @StringRes int titleRes,
                                                      CharSequence description, @StringRes int descriptionRes,
                                                      @DrawableRes int imageRes, @ColorRes int backgroundRes,
                                                      @ColorInt int backgroundColor,
                                                      @LayoutRes int layout, int permissionsRequestCode) {
            SimpleSlideFragment fragment = new SimpleSlideFragment();
            Bundle arguments = new Bundle();
            arguments.putCharSequence(ARGUMENT_TITLE, title);
            arguments.putInt(ARGUMENT_TITLE_RES, titleRes);
            arguments.putCharSequence(ARGUMENT_DESCRIPTION, description);
            arguments.putInt(ARGUMENT_DESCRIPTION_RES, descriptionRes);
            arguments.putInt(ARGUMENT_IMAGE_RES, imageRes);
            arguments.putInt(ARGUMENT_BACKGROUND_RES, backgroundRes);
            arguments.putInt(ARGUMENT_BACKGROUND_COLOR, backgroundColor);
            arguments.putInt(ARGUMENT_LAYOUT_RES, layout);
            arguments.putInt(ARGUMENT_PERMISSIONS_REQUEST_CODE, permissionsRequestCode);
            fragment.setArguments(arguments);

            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            updateNavigation();
        }

        @Override
        public void onResume() {
            super.onResume();
            //Lock scroll for the case that users revoke accepted permission settings while in the intro
            updateNavigation();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();

            View fragment = inflater.inflate(arguments.getInt(ARGUMENT_LAYOUT_RES,
                    R.layout.fragment_simple_slide), container, false);

            titleView = (TextView) fragment.findViewById(R.id.mi_title);
            descriptionView = (TextView) fragment.findViewById(R.id.mi_description);
            imageView = (ImageView) fragment.findViewById(R.id.mi_image);

            CharSequence title = arguments.getCharSequence(ARGUMENT_TITLE);
            int titleRes = arguments.getInt(ARGUMENT_TITLE_RES);
            CharSequence description = arguments.getCharSequence(ARGUMENT_DESCRIPTION);
            int descriptionRes = arguments.getInt(ARGUMENT_DESCRIPTION_RES);
            int imageRes = arguments.getInt(ARGUMENT_IMAGE_RES);
            int backgroundRes = arguments.getInt(ARGUMENT_BACKGROUND_RES);
            int backgroundColor = arguments.getInt(ARGUMENT_BACKGROUND_COLOR);

            //Title
            if (titleView != null) {
                if (title != null) {
                    titleView.setText(title);
                    titleView.setVisibility(View.VISIBLE);
                } else if (titleRes != 0) {
                    titleView.setText(titleRes);
                    titleView.setVisibility(View.VISIBLE);
                } else {
                    titleView.setVisibility(View.GONE);
                }
            }

            //Description
            if (descriptionView != null) {
                if (description != null) {
                    descriptionView.setText(description);
                    descriptionView.setVisibility(View.VISIBLE);
                } else if (descriptionRes != 0) {
                    descriptionView.setText(descriptionRes);
                    descriptionView.setVisibility(View.VISIBLE);
                } else {
                    descriptionView.setVisibility(View.GONE);
                }
            }

            //Image
            if (imageView != null) {
                if (imageRes != 0) {
                    imageView.setImageResource(imageRes);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            
            @ColorInt
            int textColorPrimary;
            @ColorInt
            int textColorSecondary;


            @ColorInt int color = backgroundRes != 0 ? ContextCompat.getColor(getContext(), backgroundRes) : backgroundColor;

            if (color != 0 && ColorUtils.calculateLuminance(color) < 0.6) {
                //Use light text color
                textColorPrimary = ContextCompat.getColor(getContext(), R.color.mi_text_color_primary_dark);
                textColorSecondary = ContextCompat.getColor(getContext(), R.color.mi_text_color_secondary_dark);
            } else {
                //Use dark text color
                textColorPrimary = ContextCompat.getColor(getContext(), R.color.mi_text_color_primary_light);
                textColorSecondary = ContextCompat.getColor(getContext(), R.color.mi_text_color_secondary_light);
            }
            
            if (titleView != null) {
                titleView.setTextColor(textColorPrimary);
            }
            if (descriptionView != null) {
                descriptionView.setTextColor(textColorSecondary);
            }

            return fragment;
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescriptionView() {
            return descriptionView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            int permissionsRequestCode = getArguments() == null ? DEFAULT_PERMISSIONS_REQUEST_CODE :
                    getArguments().getInt(ARGUMENT_PERMISSIONS_REQUEST_CODE,
                            DEFAULT_PERMISSIONS_REQUEST_CODE);
            if (requestCode == permissionsRequestCode) {
                updateNavigation();
            }
        }
    }
}
