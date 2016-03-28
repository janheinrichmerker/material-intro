package com.heinrichreimersoftware.materialintro.slide;

import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.R;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlide extends Slide {

    private static final int PERMISSIONS_REQUEST_CODE = 34;

    private final Fragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;

    private final boolean canGoForward;

    private final boolean canGoBackward;

    private SimpleSlide(Builder builder) {
        fragment = Fragment.newInstance(builder.title, builder.description, builder.image,
                builder.background, builder.backgroundDark, builder.layout, builder.permissions);
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
    public int getBackground() {
        return background;
    }

    @Override
    public int getBackgroundDark() {
        return backgroundDark;
    }

    @Override
    public boolean canGoForward() {
        if (fragment != null) {
            return canGoForward && fragment.canGoForward();
        }
        return canGoForward;
    }

    @Override
    public boolean canGoBackward() {
        return canGoBackward;
    }

    public static class Builder{
        @ColorRes
        private int background = 0;
        @ColorRes
        private int backgroundDark = 0;
        @String
        private String title = "";
        @String
        private String description = "";
        @DrawableRes
        private int image = 0;
        @LayoutRes
        private int layout = R.layout.fragment_simple_slide;

        private boolean canGoForward = true;

        private boolean canGoBackward = true;

        private String[] permissions = null;

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDark) {
            this.backgroundDark = backgroundDark;
            return this;
        }

        public Builder title(@String String title) {
            this.title = title;
            return this;
        }

        public Builder description(@String String description) {
            this.description = description;
            return this;
        }

        public Builder image(@DrawableRes int image) {
            this.image = image;
            return this;
        }

        public Builder layout(@LayoutRes int layout) {
            this.layout = layout;
            return this;
        }

        public Builder scrollable(boolean scrollable) {
            this.layout = scrollable ? R.layout.fragment_simple_slide_scrollable :
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

        public SimpleSlide build(){
            return new SimpleSlide(this);
        }
    }

    public static class Fragment extends SlideFragment {
        private static final String ARGUMENT_TITLE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_TITLE_RES";
        private static final String ARGUMENT_DESCRIPTION_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_DESCRIPTION_RES";
        private static final String ARGUMENT_IMAGE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_IMAGE_RES";
        private static final String ARGUMENT_BACKGROUND_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_RES";

        private static final String ARGUMENT_BACKGROUND_DARK_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_DARK_RES";
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";

        private static final String ARGUMENT_PERMISSIONS =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_PERMISSIONS";

        private Button buttonGrantPermissions;

        private boolean permissionsGranted = false;

        public Fragment() {
        }

        public static Fragment newInstance(@String String title, @String String description,
                                           @DrawableRes int image, @ColorRes int background,
                                           @ColorRes int backgroundDark, @LayoutRes int layout,
                                           String[] permissions) {
            Fragment fragment = new Fragment();

            Bundle arguments = new Bundle();
            arguments.putString(ARGUMENT_TITLE_RES, title);
            arguments.putString(ARGUMENT_DESCRIPTION_RES, description);
            arguments.putInt(ARGUMENT_IMAGE_RES, image);
            arguments.putInt(ARGUMENT_BACKGROUND_RES, background);
            arguments.putInt(ARGUMENT_BACKGROUND_DARK_RES, backgroundDark);
            arguments.putInt(ARGUMENT_LAYOUT_RES, layout);
            arguments.putStringArray(ARGUMENT_PERMISSIONS, permissions);
            fragment.setArguments(arguments);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();

            View fragment = inflater.inflate(arguments.getInt(ARGUMENT_LAYOUT_RES,
                    R.layout.fragment_simple_slide), container, false);

            TextView title = (TextView) fragment.findViewById(R.id.mi_title);
            TextView description = (TextView) fragment.findViewById(R.id.mi_description);
            buttonGrantPermissions = (Button) fragment.findViewById(R.id.mi_button_grant_permissions);
            ImageView image = (ImageView) fragment.findViewById(R.id.mi_image);

            String titleRes = arguments.getString(ARGUMENT_TITLE_RES);
            String descRes = arguments.getString(ARGUMENT_DESCRIPTION_RES);
            int imgRes = arguments.getInt(ARGUMENT_IMAGE_RES);
            int backgroundRes = arguments.getInt(ARGUMENT_BACKGROUND_RES);
            int backgroundDarkRes = arguments.getInt(ARGUMENT_BACKGROUND_DARK_RES);
            String[] permissions = arguments.getStringArray(ARGUMENT_PERMISSIONS);

            if (!titleRes.equals("")) {
                title.setText(titleRes);
            } else {
                title.setVisibility(View.GONE);
            }
            if (!descRes.equals("")) {
                description.setText(descRes);
            } else {
                description.setVisibility(View.GONE);
            }
            if (imgRes != 0) {
                image.setImageResource(imgRes);
            } else {
                image.setVisibility(View.GONE);
            }

            if (backgroundDarkRes != 0) {
                ViewCompat.setBackgroundTintList(buttonGrantPermissions, ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), backgroundDarkRes)));
            }

            if (backgroundRes != 0) {
                if (ColorUtils.calculateLuminance(ContextCompat.getColor(getContext(), backgroundRes)) > 0.6) {
                    //Use dark text color
                    title.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_light));
                    description.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_secondary_light));
                    buttonGrantPermissions.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_light));
                } else {
                    //Use light text color
                    title.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_dark));
                    description.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_secondary_dark));
                    buttonGrantPermissions.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_dark));
                }
            }
            updatePermissions(permissions);
            return fragment;
        }

        private void updatePermissions(String[] permissions) {
            Log.d("SimpleSlide", "Updating permissions...");
            if (permissions != null) {
                final List<String> permissionsNotGranted = new ArrayList<>();
                for (String permission : permissions) {
                    if (ContextCompat.checkSelfPermission(getActivity(), permission) !=
                            PackageManager.PERMISSION_GRANTED) {
                        permissionsNotGranted.add(permission);
                    }
                }

                if (permissionsNotGranted.size() > 0) {
                    buttonGrantPermissions.setVisibility(View.VISIBLE);
                    buttonGrantPermissions.setText(getResources().getQuantityText(
                            R.plurals.mi_label_grant_permission, permissionsNotGranted.size()));
                    buttonGrantPermissions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    permissionsNotGranted.toArray(
                                            new String[permissionsNotGranted.size()]),
                                    PERMISSIONS_REQUEST_CODE);
                        }
                    });
                } else {
                    buttonGrantPermissions.setVisibility(View.GONE);
                    permissionsGranted = true;
                    updateNavigation();
                }
            } else {
                buttonGrantPermissions.setVisibility(View.GONE);
                permissionsGranted = true;
                updateNavigation();
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (requestCode == PERMISSIONS_REQUEST_CODE) {
                updatePermissions(permissions);
            }
        }

        @Override
        public boolean canGoForward() {
            return permissionsGranted;
        }
    }
}
