package com.heinrichreimersoftware.materialintro.app;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.heinrichreimersoftware.materialintro.R;
import com.heinrichreimersoftware.materialintro.slide.Slide;
import com.heinrichreimersoftware.materialintro.slide.SlideAdapter;
import com.heinrichreimersoftware.materialintro.util.AnimUtils;
import com.heinrichreimersoftware.materialintro.util.CheatSheet;
import com.heinrichreimersoftware.materialintro.view.FadeableViewPager;
import com.heinrichreimersoftware.materialintro.view.InkPageIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressLint("Registered")
public class IntroActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_ITEM =
            "com.heinrichreimersoftware.materialintro.app.IntroActivity.KEY_CURRENT_ITEM";

    private static final String KEY_SLIDES =
            "com.heinrichreimersoftware.materialintro.app.IntroActivity.KEY_SLIDES";

    private final ArgbEvaluator evaluator = new ArgbEvaluator();
    private FrameLayout frame;
    private FadeableViewPager pager;
    private InkPageIndicator pagerIndicator;
    private ImageButton buttonNext;
    private ImageButton buttonSkip;
    private SlideAdapter adapter;
    private IntroPageChangeListener listener = new IntroPageChangeListener();
    private boolean fullscreen = false;
    private boolean skipEnabled = true;
    private boolean finishEnabled = true;
    private int position = 0;
    private float positionOffset = 0;

    private NavigationPolicy navigationPolicy = null;

    private List<OnNavigationBlockedListener> navigationBlockedListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (fullscreen && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSystemUiFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, true);
            setFullscreenFlags(fullscreen);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CURRENT_ITEM)) {
            position = savedInstanceState.getInt(KEY_CURRENT_ITEM, position);
        }

        setContentView(R.layout.activity_intro);
        findViews();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        updateTaskDescription();
        updateBackground();
        updateTaskDescription();
        updateButtonNextDrawable();
        updateButtonSkipDrawable();
        frame.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateViewPositions();
                v.removeOnLayoutChangeListener(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFullscreenFlags(fullscreen);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_ITEM, pager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private void setSystemUiFlags(int flags, boolean value){
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
        if (value) {
            systemUiVisibility |= flags;
        } else {
            systemUiVisibility &= ~flags;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    private void setFullscreenFlags(boolean fullscreen){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int fullscreenFlags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN;

            setSystemUiFlags(fullscreenFlags, fullscreen);
        }
    }

    private void findViews(){
        frame = (FrameLayout) findViewById(R.id.mi_frame);
        pager = (FadeableViewPager) findViewById(R.id.mi_pager);
        pagerIndicator = (InkPageIndicator) findViewById(R.id.mi_pager_indicator);
        buttonNext = (ImageButton) findViewById(R.id.mi_button_next);
        buttonSkip = (ImageButton) findViewById(R.id.mi_button_skip);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new SlideAdapter(fragmentManager);

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(listener);
        pager.setCurrentItem(position);

        pagerIndicator.setViewPager(pager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSlide();
            }
        });
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipIfEnabled();
            }
        });
        CheatSheet.setup(buttonNext);
        CheatSheet.setup(buttonSkip);
    }

    public void nextSlide() {
        int currentItem = pager.getCurrentItem();

        if (canGoForward(currentItem, true)) {
            pager.setCurrentItem(++currentItem, true);
        }
        else {
            AnimUtils.applyShakeAnimation(this, buttonNext);

        }
    }

    public void previousSlide() {
        int currentItem = pager.getCurrentItem();

        if (canGoBackward(currentItem, true)) {
            pager.setCurrentItem(--currentItem, true);
        }
        else {
            AnimUtils.applyShakeAnimation(getApplicationContext(), buttonSkip);

        }
    }

    private void skipIfEnabled() {
        if (skipEnabled) {
            int count = getCount();
            int endPosition = pager.getCurrentItem();
            while (endPosition < count && canGoForward(endPosition, true)) {
                endPosition++;
            }
            pager.setCurrentItem(endPosition);
        } else {
            previousSlide();
        }
    }

    private boolean canGoForward(int position, boolean notifyListeners) {
        boolean canGoForward = (navigationPolicy == null || navigationPolicy.canGoForward(position)) &&
                getSlide(position).canGoForward();
        if (!canGoForward && notifyListeners) {
            for (OnNavigationBlockedListener listener : navigationBlockedListeners) {
                listener.onNavigationBlocked(position, OnNavigationBlockedListener.DIRECTION_FORWARD);
            }
        }
        return canGoForward;
    }

    private boolean canGoBackward(int position, boolean notifyListeners) {
        boolean canGoBackward = (navigationPolicy == null || navigationPolicy.canGoBackward(position)) &&
                getSlide(position).canGoBackward();
        if (!canGoBackward && notifyListeners) {
            for (OnNavigationBlockedListener listener : navigationBlockedListeners) {
                listener.onNavigationBlocked(position, OnNavigationBlockedListener.DIRECTION_BACKWARD);
            }
        }
        return canGoBackward;
    }

    private void finishIfNeeded() {
        if (positionOffset == 0 && position == adapter.getCount()) {
            setResult(RESULT_OK);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    private void updateFullscreen() {
        if (position + positionOffset > adapter.getCount() - 1) {
            setFullscreenFlags(false);
        } else {
            setFullscreenFlags(fullscreen);
        }
    }

    private void updateTaskDescription() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            String title = getTitle().toString();
            Drawable iconDrawable = getApplicationInfo().loadIcon(getPackageManager());
            Bitmap icon = iconDrawable instanceof BitmapDrawable ? ((BitmapDrawable) iconDrawable).getBitmap() : null;
            int colorPrimary;
            if (position < getCount()) {
                try {
                    colorPrimary = ContextCompat.getColor(IntroActivity.this, getBackgroundDark(position));
                } catch (Resources.NotFoundException e) {
                    colorPrimary = ContextCompat.getColor(IntroActivity.this, getBackground(position));
                }
            }
            else {
                TypedValue typedValue = new TypedValue();
                TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
                colorPrimary = a.getColor(0, 0);
                a.recycle();
            }
            colorPrimary = ColorUtils.setAlphaComponent(colorPrimary, 0xFF);

            setTaskDescription(new ActivityManager.TaskDescription(title, icon, colorPrimary));
        }
    }

    private void updateBackground() {
        int background;
        int backgroundNext;
        int backgroundDark;
        int backgroundDarkNext;

        if (position == getCount()) {
            background = Color.TRANSPARENT;
            backgroundNext = Color.TRANSPARENT;
            backgroundDark = Color.TRANSPARENT;
            backgroundDarkNext = Color.TRANSPARENT;
        } else {
            background = ContextCompat.getColor(IntroActivity.this,
                    getBackground(position));
            backgroundNext = ContextCompat.getColor(IntroActivity.this,
                    getBackground(Math.min(position + 1, getCount() - 1)));

            background = ColorUtils.setAlphaComponent(background, 0xFF);
            backgroundNext = ColorUtils.setAlphaComponent(backgroundNext, 0xFF);

            try {
                backgroundDark = ContextCompat.getColor(IntroActivity.this,
                        getBackgroundDark(position));
            } catch (Resources.NotFoundException e) {
                backgroundDark = ContextCompat.getColor(IntroActivity.this,
                        R.color.mi_status_bar_background);
            }
            try {
                backgroundDarkNext = ContextCompat.getColor(IntroActivity.this,
                        getBackgroundDark(Math.min(position + 1, getCount() - 1)));
            } catch (Resources.NotFoundException e) {
                backgroundDarkNext = ContextCompat.getColor(IntroActivity.this,
                        R.color.mi_status_bar_background);
            }
        }

        if (position + positionOffset >= adapter.getCount() - 1) {
            backgroundNext = ColorUtils.setAlphaComponent(background, 0x00);
            backgroundDarkNext = Color.TRANSPARENT;
        }

        background = (Integer) evaluator.evaluate(positionOffset, background, backgroundNext);
        backgroundDark = (Integer) evaluator.evaluate(positionOffset, backgroundDark, backgroundDarkNext);

        frame.setBackgroundColor(background);

        float[] backgroundDarkHsv = new float[3];
        Color.colorToHSV(backgroundDark, backgroundDarkHsv);
        //Slightly darken the background color a bit for more contrast
        backgroundDarkHsv[2] *= 0.95;
        int backgroundDarker = Color.HSVToColor(backgroundDarkHsv);
        pagerIndicator.setPageIndicatorColor(backgroundDarker);
        ViewCompat.setBackgroundTintList(buttonNext, ColorStateList.valueOf(backgroundDarker));
        ViewCompat.setBackgroundTintList(buttonSkip, ColorStateList.valueOf(backgroundDarker));

        int iconColor;
        if (ColorUtils.calculateLuminance(backgroundDark) > 0.4) {
            //Light background
            iconColor = ContextCompat.getColor(this, R.color.mi_icon_color_light);
        } else {
            //Dark background
            iconColor = ContextCompat.getColor(this, R.color.mi_icon_color_dark);
        }
        pagerIndicator.setCurrentPageIndicatorColor(iconColor);
        DrawableCompat.setTint(buttonNext.getDrawable(), iconColor);
        DrawableCompat.setTint(buttonSkip.getDrawable(), iconColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(backgroundDark);

            if (position == adapter.getCount()) {
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            } else if (position + positionOffset >= adapter.getCount() - 1) {
                TypedValue typedValue = new TypedValue();
                TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{android.R.attr.navigationBarColor});

                int defaultNavigationBarColor = a.getColor(0, Color.BLACK);

                a.recycle();

                int navigationBarColor = (Integer) evaluator.evaluate(positionOffset, defaultNavigationBarColor, Color.TRANSPARENT);
                getWindow().setNavigationBarColor(navigationBarColor);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                int flagLightStatusBar = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (ColorUtils.calculateLuminance(backgroundDark) > 0.4) {
                    //Light background
                    systemUiVisibility |= flagLightStatusBar;
                } else {
                    //Dark background
                    systemUiVisibility &= ~flagLightStatusBar;
                }
                getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
        }
    }

    private void updateViewPositions() {
        if (position + positionOffset < 1) {
            //Between first and second item
            float offset = position + positionOffset;

            if (skipEnabled) {
                buttonSkip.setTranslationY(0);
            } else {
                buttonSkip.setTranslationY((1 - offset) * 2 * buttonNext.getHeight());
            }
            updateButtonNextDrawable();
        } else if (position + positionOffset >= 1 && position + positionOffset < adapter.getCount() - 2) {
            //Between second and second last item
            //Reset buttons
            buttonSkip.setTranslationY(0);
            buttonSkip.setTranslationX(0);
            buttonNext.setTranslationY(0);
            updateButtonNextDrawable();
        } else if (position + positionOffset >= adapter.getCount() - 2 && position + positionOffset < adapter.getCount() - 1) {
            //Between second last and last item
            float offset = position + positionOffset - adapter.getCount() + 2;

            if (skipEnabled) {
                boolean rtl = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getResources().getConfiguration().getLayoutDirection() ==
                        View.LAYOUT_DIRECTION_RTL;
                buttonSkip.setTranslationX(offset * (rtl ? 1 : -1) * pager.getWidth());
            } else {
                buttonSkip.setTranslationX(0);
            }

            if (finishEnabled) {
                buttonNext.setTranslationY(0);
            } else {
                buttonNext.setTranslationY(offset * 2 * buttonNext.getHeight());
            }
            updateButtonNextDrawable();
        } else if (position + positionOffset >= adapter.getCount() - 1) {
            //Fade
            float offset = position + positionOffset - adapter.getCount() + 1;

            if (skipEnabled) {
                boolean rtl = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getResources().getConfiguration().getLayoutDirection() ==
                        View.LAYOUT_DIRECTION_RTL;
                buttonSkip.setTranslationX((rtl ? 1 : -1) * pager.getWidth());
            } else {
                buttonSkip.setTranslationY(offset * 2 * buttonNext.getHeight());
            }

            if (finishEnabled) {
                buttonNext.setTranslationY(offset * 2 * buttonNext.getHeight());
            } else {
                buttonNext.setTranslationY(-2 * buttonNext.getHeight());
            }
            pagerIndicator.setTranslationY(offset * 2 * buttonNext.getWidth());
            updateButtonNextDrawable();
        }
    }

    private void updateButtonNextDrawable() {
        float offset = 0;
        if (finishEnabled && position + positionOffset >= adapter.getCount() - 2) {
            offset = Math.min(position + positionOffset - adapter.getCount() + 2, 1);
        }

        if (offset <= 0) {
            buttonNext.setImageResource(R.drawable.ic_next);
            buttonNext.getDrawable().setAlpha(0xFF);
        } else {
            buttonNext.setImageResource(R.drawable.ic_next_finish);
            if (buttonNext.getDrawable() != null && buttonNext.getDrawable() instanceof LayerDrawable) {
                LayerDrawable drawable = (LayerDrawable) buttonNext.getDrawable();
                drawable.getDrawable(0).setAlpha((int) (0xFF * (1 - offset)));
                drawable.getDrawable(1).setAlpha((int) (0xFF * offset));
            } else {
                buttonNext.setImageResource(offset > 0 ? R.drawable.ic_finish : R.drawable.ic_next);
            }
        }
    }

    private void updateButtonSkipDrawable() {
        if (skipEnabled) {
            buttonSkip.setImageResource(R.drawable.ic_skip);
        } else {
            buttonSkip.setImageResource(R.drawable.ic_previous);
        }
    }


    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isSkipEnabled() {
        return skipEnabled;
    }

    public void setSkipEnabled(boolean skipEnabled) {
        this.skipEnabled = skipEnabled;
        updateButtonSkipDrawable();
        updateViewPositions();
    }

    public boolean isFinishEnabled() {
        return finishEnabled;
    }

    public void setFinishEnabled(boolean finishEnabled) {
        this.finishEnabled = finishEnabled;
        updateButtonNextDrawable();
        updateViewPositions();
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        pager.setOnPageChangeListener(listener);
        pager.addOnPageChangeListener(this.listener);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        pager.addOnPageChangeListener(listener);
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (listener != this.listener)
            pager.removeOnPageChangeListener(listener);
    }


    protected void addSlide(int location, Slide object) {
        adapter.addSlide(location, object);
    }

    protected boolean addSlide(Slide object) {
        return adapter.addSlide(object);
    }

    protected boolean addSlides(int location, @NonNull Collection<? extends Slide> collection) {
        return adapter.addSlides(location, collection);
    }

    protected boolean addSlides(@NonNull Collection<? extends Slide> collection) {
        return adapter.addSlides(collection);
    }

    protected void clearSlides() {
        adapter.clearSlides();
    }

    protected boolean containsSlide(Object object) {
        return adapter.containsSlide(object);
    }

    protected boolean containsSlides(@NonNull Collection<?> collection) {
        return adapter.containsSlides(collection);
    }

    protected Slide getSlide(int location) {
        return adapter.getSlide(location);
    }

    protected Fragment getItem(int position) {
        return adapter.getItem(position);
    }

    @ColorRes
    protected int getBackground(int position) {
        return adapter.getBackground(position);
    }

    @ColorRes
    protected int getBackgroundDark(int position) {
        return adapter.getBackgroundDark(position);
    }

    protected List<Slide> getSlides() {
        return adapter.getSlides();
    }

    protected int indexOfSlide(Object object) {
        return adapter.indexOfSlide(object);
    }

    protected boolean isEmpty() {
        return adapter.isEmpty();
    }

    protected int getCount() {
        return adapter == null ? 0 : adapter.getCount();
    }

    protected int lastIndexOfSlide(Object object) {
        return adapter.lastIndexOfSlide(object);
    }

    protected Slide removeSlide(int location) {
        return adapter.removeSlide(location);
    }

    protected boolean removeSlide(Object object) {
        return adapter.removeSlide(object);
    }

    protected boolean removeSlides(@NonNull Collection<?> collection) {
        return adapter.removeSlides(collection);
    }

    protected boolean retainSlides(@NonNull Collection<?> collection) {
        return adapter.retainSlides(collection);
    }

    protected Slide setSlide(int location, Slide object) {
        return adapter.setSlide(location, object);
    }

    protected List<Slide> setSlides(List<? extends Slide> list) {
        return adapter.setSlides(list);
    }


    private class IntroPageChangeListener extends FadeableViewPager.SimpleOnOverscrollPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            IntroActivity.this.position = position;
            IntroActivity.this.positionOffset = positionOffset;

            //Lock while scrolling a slide near its edges to lock (uncommon) multiple page swipes
            if (Math.abs(positionOffset) < 0.1f) {
                lockSwipeIfNeeded();
            }

            updateBackground();
            updateViewPositions();
            updateFullscreen();

            finishIfNeeded();
        }

        @Override
        public void onPageSelected(int position) {
            IntroActivity.this.position = position;
            updateTaskDescription();
            lockSwipeIfNeeded();
        }
    }

    public void setNavigationPolicy(NavigationPolicy navigationPolicy) {
        this.navigationPolicy = navigationPolicy;
    }

    public void addOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        navigationBlockedListeners.add(listener);
    }

    public void removeOnNavigationBlockedListener(OnNavigationBlockedListener listener) {
        navigationBlockedListeners.remove(listener);
    }

    public void clearOnNavigationBlockedListeners() {
        navigationBlockedListeners.clear();
    }

    public void lockSwipeIfNeeded() {
        if (position < getCount()) {
            pager.setSwipeLeftEnabled(canGoForward(position, false));
            pager.setSwipeRightEnabled(canGoBackward(position, false));
        }
    }
}
