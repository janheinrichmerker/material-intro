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
        fragment = Fragment.newInstance(builder.title, builder.titleRes, builder.description,
                builder.descriptionRes, builder.imageRes, builder.backgroundRes,
                builder.backgroundDarkRes, builder.layoutRes, builder.permissions,
                builder.isPermissionRequired);
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

    public static class Builder {
        @ColorRes
        private int backgroundRes = 0;

        @ColorRes
        private int backgroundDarkRes = 0;

        private String title = null;

        @StringRes
        private int titleRes = 0;

        private String description = null;

        @StringRes
        private int descriptionRes = 0;

        @DrawableRes
        private int imageRes = 0;

        @LayoutRes
        private int layoutRes = R.layout.fragment_simple_slide;

        private boolean canGoForward = true;

        private boolean canGoBackward = true;

        private String[] permissions = null;

        private boolean isPermissionRequired = true;

        public Builder background(@ColorRes int backgroundRes) {
            this.backgroundRes = backgroundRes;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDarkRes) {
            this.backgroundDarkRes = backgroundDarkRes;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            this.titleRes = 0;
            return this;
        }

        public Builder title(@StringRes int titleRes) {
            this.titleRes = titleRes;
            this.title = null;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
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

        public Builder permissionRequired(boolean isRequired) {
            this.isPermissionRequired = isRequired;
            return this;
        }

        public SimpleSlide build() {
            return new SimpleSlide(this);
        }
    }

    public static class Fragment extends SlideFragment {
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

        private static final String ARGUMENT_BACKGROUND_DARK_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_DARK_RES";

        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";

        private static final String ARGUMENT_PERMISSIONS =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_PERMISSIONS";

        private Button buttonGrantPermissions;

        private boolean permissionsGranted = true;

        public Fragment() {
        }

        public static Fragment newInstance(String title, @StringRes int titleRes,
                                           String description, @StringRes int descriptionRes,
                                           @DrawableRes int imageRes, @ColorRes int background,
                                           @ColorRes int backgroundDark, @LayoutRes int layout,
                                           String[] permissions, boolean isPermissionRequired) {
            Fragment fragment = new Fragment();

            Bundle arguments = new Bundle();
            arguments.putString(ARGUMENT_TITLE, title);
            arguments.putInt(ARGUMENT_TITLE_RES, titleRes);
            arguments.putString(ARGUMENT_DESCRIPTION, description);
            arguments.putInt(ARGUMENT_DESCRIPTION_RES, descriptionRes);
            arguments.putInt(ARGUMENT_IMAGE_RES, imageRes);
            arguments.putInt(ARGUMENT_BACKGROUND_RES, background);
            arguments.putInt(ARGUMENT_BACKGROUND_DARK_RES, backgroundDark);
            arguments.putInt(ARGUMENT_LAYOUT_RES, layout);
            arguments.putStringArray(ARGUMENT_PERMISSIONS, permissions);
            fragment.setArguments(arguments);

            fragment.permissionsGranted = permissions == null || permissions.length <= 0
                    || !isPermissionRequired;

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();

            View fragment = inflater.inflate(arguments.getInt(ARGUMENT_LAYOUT_RES,
                    R.layout.fragment_simple_slide), container, false);

            TextView titleView = (TextView) fragment.findViewById(R.id.mi_title);
            TextView descriptionView = (TextView) fragment.findViewById(R.id.mi_description);
            buttonGrantPermissions = (Button) fragment.findViewById(R.id.mi_button_grant_permissions);
            ImageView imageView = (ImageView) fragment.findViewById(R.id.mi_image);

            String title = arguments.getString(ARGUMENT_TITLE, null);
            int titleRes = arguments.getInt(ARGUMENT_TITLE_RES, 0);
            String description = arguments.getString(ARGUMENT_DESCRIPTION, null);
            int descriptionRes = arguments.getInt(ARGUMENT_DESCRIPTION_RES, 0);
            int imageRes = arguments.getInt(ARGUMENT_IMAGE_RES, 0);
            int backgroundRes = arguments.getInt(ARGUMENT_BACKGROUND_RES, 0);
            int backgroundDarkRes = arguments.getInt(ARGUMENT_BACKGROUND_DARK_RES, 0);
            String[] permissions = arguments.getStringArray(ARGUMENT_PERMISSIONS);

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

            if (backgroundDarkRes != 0 && buttonGrantPermissions != null) {
                ViewCompat.setBackgroundTintList(buttonGrantPermissions, ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), backgroundDarkRes)));
            }

            if (backgroundRes != 0) {
                if (ColorUtils.calculateLuminance(ContextCompat.getColor(getContext(), backgroundRes)) > 0.6) {
                    //Use dark text color
                    if (titleView != null) {
                        titleView.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_primary_light));
                    }
                    if (descriptionView != null) {
                        descriptionView.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_secondary_light));
                    }
                    if (buttonGrantPermissions != null) {
                        buttonGrantPermissions.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_primary_light));
                    }
                } else {
                    //Use light text color
                    if (titleView != null) {
                        titleView.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_primary_dark));
                    }
                    if (descriptionView != null) {
                        descriptionView.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_secondary_dark));
                    }
                    if (buttonGrantPermissions != null) {
                        buttonGrantPermissions.setTextColor(ContextCompat.getColor(getContext(),
                                R.color.mi_text_color_primary_dark));
                    }
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
                    if (buttonGrantPermissions != null) {
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
                    }
                } else {
                    if (buttonGrantPermissions != null) {
                        buttonGrantPermissions.setVisibility(View.GONE);
                    }
                    permissionsGranted = true;
                    updateNavigation();
                }
            } else {
                if (buttonGrantPermissions != null) {
                    buttonGrantPermissions.setVisibility(View.GONE);
                }
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
