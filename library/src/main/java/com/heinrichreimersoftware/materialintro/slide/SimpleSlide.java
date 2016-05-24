package com.heinrichreimersoftware.materialintro.slide;

import android.content.Context;
import android.content.pm.PackageManager;
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
import android.support.v4.util.Pair;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.R;
import com.heinrichreimersoftware.materialintro.app.ButtonCtaFragment;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlide implements Slide, RestorableSlide, ButtonCtaSlide {

    private static final int DEFAULT_PERMISSIONS_REQUEST_CODE = 34; //Random number
    private SimpleSlideFragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;
    private final boolean canGoForward;
    private final boolean canGoBackward;

    private Pair<Integer, View.OnClickListener> button = null;

    private SimpleSlide(Builder builder) {
        fragment = SimpleSlideFragment.newInstance(builder.title, builder.titleRes,
                builder.description, builder.descriptionRes, builder.imageRes,
                builder.backgroundRes, builder.layoutRes, builder.permissions,
                builder.permissionsRequestCode);
        background = builder.backgroundRes;
        backgroundDark = builder.backgroundDarkRes;
        canGoForward = builder.canGoForward;
        canGoBackward = builder.canGoBackward;
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
    public int getBackgroundDark() {
        return backgroundDark;
    }

    @Override
    public boolean canGoForward() {
        return canGoForward && fragment.canGoForward();

    }

    @Override
    public boolean canGoBackward() {
        return canGoBackward;
    }

    @Override
    public View.OnClickListener getButtonCtaClickListener() {
        return fragment.getButtonCtaClickListener();
    }

    @Override
    public String getButtonCtaLabel() {
        return fragment.getButtonCtaLabel();
    }

    @Override
    public int getButtonCtaLabelRes() {
        return fragment.getButtonCtaLabelRes();
    }

    public static class Builder {
        @ColorRes
        private int backgroundRes = 0;
        @ColorRes
        private int backgroundDarkRes = 0;

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

        private int permissionsRequestCode = DEFAULT_PERMISSIONS_REQUEST_CODE;

        public Builder background(@ColorRes int backgroundRes) {
            this.backgroundRes = backgroundRes;
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
            this.title = Html.fromHtml(titleHtml);
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
            this.description = Html.fromHtml(descriptionHtml);
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

        public SimpleSlide build() {
            return new SimpleSlide(this);
        }
    }

    public static class SimpleSlideFragment extends SlideFragment implements ButtonCtaFragment {
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
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";
        private static final String ARGUMENT_PERMISSIONS =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_PERMISSIONS";

        private static final String ARGUMENT_PERMISSIONS_REQUEST_CODE =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_PERMISSIONS_REQUEST_CODE";

        private String[] permissions = null;

        private View.OnClickListener buttonClickListener = null;
        private String buttonLabel;

        public SimpleSlideFragment() {
        }

        public static SimpleSlideFragment newInstance(CharSequence title, @StringRes int titleRes,
                                                      CharSequence description, @StringRes int descriptionRes,
                                                      @DrawableRes int imageRes, @ColorRes int backgroundRes,
                                                      @LayoutRes int layout, String[] permissions,
                                                      int permissionsRequestCode) {
            SimpleSlideFragment fragment = new SimpleSlideFragment();

            Bundle arguments = new Bundle();
            arguments.putCharSequence(ARGUMENT_TITLE, title);
            arguments.putInt(ARGUMENT_TITLE_RES, titleRes);
            arguments.putCharSequence(ARGUMENT_DESCRIPTION, description);
            arguments.putInt(ARGUMENT_DESCRIPTION_RES, descriptionRes);
            arguments.putInt(ARGUMENT_IMAGE_RES, imageRes);
            arguments.putInt(ARGUMENT_BACKGROUND_RES, backgroundRes);
            arguments.putInt(ARGUMENT_LAYOUT_RES, layout);
            arguments.putStringArray(ARGUMENT_PERMISSIONS, permissions);
            arguments.putInt(ARGUMENT_PERMISSIONS_REQUEST_CODE, permissionsRequestCode);
            fragment.setArguments(arguments);

            return fragment;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Bundle arguments = getArguments();
            String[] newPermissions = arguments.getStringArray(ARGUMENT_PERMISSIONS);
            updatePermissions(newPermissions != null ? newPermissions : permissions);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onResume() {
            super.onResume();
            //Lock scroll for the case that users revoke accepted permission settings while in the intro
            updatePermissions();
            updateNavigation();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();

            View fragment = inflater.inflate(arguments.getInt(ARGUMENT_LAYOUT_RES,
                    R.layout.fragment_simple_slide), container, false);

            TextView titleView = (TextView) fragment.findViewById(R.id.mi_title);
            TextView descriptionView = (TextView) fragment.findViewById(R.id.mi_description);
            ImageView imageView = (ImageView) fragment.findViewById(R.id.mi_image);

            CharSequence title = arguments.getCharSequence(ARGUMENT_TITLE);
            int titleRes = arguments.getInt(ARGUMENT_TITLE_RES);
            CharSequence description = arguments.getCharSequence(ARGUMENT_DESCRIPTION);
            int descriptionRes = arguments.getInt(ARGUMENT_DESCRIPTION_RES);
            int imageRes = arguments.getInt(ARGUMENT_IMAGE_RES);
            int backgroundRes = arguments.getInt(ARGUMENT_BACKGROUND_RES);

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

            if (backgroundRes != 0 &&
                    ColorUtils.calculateLuminance(ContextCompat.getColor(getContext(), backgroundRes)) < 0.6) {
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

        private void updatePermissions() {
            updatePermissions(permissions);
        }

        private synchronized void updatePermissions(@Nullable String[] newPermissions) {
            if (newPermissions != null) {
                final List<String> permissionsNotGranted = new ArrayList<>();
                for (String permission : newPermissions) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            permission) != PackageManager.PERMISSION_GRANTED) {
                        permissionsNotGranted.add(permission);
                    }
                }

                if (permissionsNotGranted.size() > 0) {
                    permissions = permissionsNotGranted.toArray(
                            new String[permissionsNotGranted.size()]);
                    buttonLabel = getResources().getQuantityText(
                            R.plurals.mi_label_grant_permission, permissionsNotGranted.size())
                            .toString();
                    buttonClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int permissionsRequestCode = getArguments() == null ? DEFAULT_PERMISSIONS_REQUEST_CODE :
                                    getArguments().getInt(ARGUMENT_PERMISSIONS_REQUEST_CODE,
                                            DEFAULT_PERMISSIONS_REQUEST_CODE);
                            ActivityCompat.requestPermissions(getActivity(), permissions,
                                    permissionsRequestCode);
                        }
                    };
                } else {
                    permissions = null;
                    buttonLabel = null;
                    buttonClickListener = null;
                }
            } else {
                permissions = null;
                buttonLabel = null;
                buttonClickListener = null;
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            int permissionsRequestCode = getArguments() == null ? DEFAULT_PERMISSIONS_REQUEST_CODE :
                    getArguments().getInt(ARGUMENT_PERMISSIONS_REQUEST_CODE,
                            DEFAULT_PERMISSIONS_REQUEST_CODE);
            if (requestCode == permissionsRequestCode) {
                updatePermissions();
                updateNavigation();
            }
        }

        @Override
        public synchronized boolean canGoForward() {
            final List<String> permissionsNotGranted = new ArrayList<>();
            if (permissions != null) {
                for (String permission : permissions) {
                    if (getActivity() == null || ContextCompat.checkSelfPermission(getActivity(),
                            permission) != PackageManager.PERMISSION_GRANTED) {
                        permissionsNotGranted.add(permission);
                    }
                }
            }
            return permissionsNotGranted.size() <= 0;
        }

        @Override
        public View.OnClickListener getButtonCtaClickListener() {
            return buttonClickListener;
        }

        @Override
        public String getButtonCtaLabel() {
            return buttonLabel;
        }

        @Override
        public int getButtonCtaLabelRes() {
            return 0;
        }
    }
}
