package com.heinrichreimersoftware.materialintro.app;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressLint("Registered")
public class IntroActivity extends AppCompatActivity implements SetNavigationStateInterface {

    public static final int DIRECTION_NEXT = 1;
    public static final int DIRECTION_PREVIOUS = -1;

    private NavigationInterface navigationInterface;

    private final ArgbEvaluator evaluator = new ArgbEvaluator();
    private FrameLayout frame;
    private FadeableViewPager pager;
    private InkPageIndicator pagerIndicator;
    private View buttonNext;
    private View buttonSkip;
    private SlideAdapter adapter;
    private IntroPageChangeListener listener = new IntroPageChangeListener();
    private boolean fullscreen = false;
    private boolean skipEnabled = true;
    private boolean finishEnabled = true;
    private int position = 0;
    private float positionOffset = 0;

    private int positionTmp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (fullscreen && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSystemUiFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, true);
            setFullscreenFlags(fullscreen);
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
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
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
        buttonNext = findViewById(R.id.mi_button_next);
        buttonSkip = findViewById(R.id.mi_button_skip);

        adapter = new SlideAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(listener);
        pagerIndicator.setViewPager(pager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSlide(false);
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

    public void nextSlide(boolean force){
        int currentItem = pager.getCurrentItem();

        if (navigationInterface == null || force) {
            pager.setCurrentItem(++currentItem, true);
        }
        else {
            if (navigationInterface.onNextClick(currentItem)) {
                pager.setCurrentItem(++currentItem, true);
            }
            else {
                AnimUtils.applyShakeAnimation(getApplicationContext(), buttonNext);
                navigationInterface.onImpossibleToNavigate(currentItem, DIRECTION_NEXT);
            }
        }
    }

    public void previousSlide(boolean force) {
        int currentItem = pager.getCurrentItem();

        if (navigationInterface == null || force) {
            pager.setCurrentItem(--currentItem, true);
        }
        else {
            if (navigationInterface.onPreviousClick(currentItem)) {
                pager.setCurrentItem(--currentItem, true);
            }
            else {
                AnimUtils.applyShakeAnimation(getApplicationContext(), buttonSkip);
                navigationInterface.onImpossibleToNavigate(currentItem, DIRECTION_PREVIOUS);
            }
        }
    }

    private void skipIfEnabled() {
        if (skipEnabled) {
            int count = getCount();
            pager.setCurrentItem(count);
        } else {
            previousSlide(false);
        }
    }

    private void finishIfNeeded() {
        if (positionOffset == 0 && position == adapter.getCount()) {
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

        if (position == getCount()) {
            background = Color.TRANSPARENT;
            backgroundNext = Color.TRANSPARENT;
        } else {
            background = ContextCompat.getColor(IntroActivity.this,
                    getBackground(position));
            backgroundNext = ContextCompat.getColor(IntroActivity.this,
                    getBackground(Math.min(position + 1, getCount() - 1)));

            background = ColorUtils.setAlphaComponent(background, 0xFF);
            backgroundNext = ColorUtils.setAlphaComponent(backgroundNext, 0xFF);
        }

        if (position + positionOffset >= adapter.getCount() - 1) {
            backgroundNext = ColorUtils.setAlphaComponent(background, 0x00);
        }

        frame.setBackgroundColor((Integer) evaluator.evaluate(positionOffset, background, backgroundNext));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int backgroundDark;
            int backgroundDarkNext;

            if (position == getCount()) {
                backgroundDark = Color.TRANSPARENT;
                backgroundDarkNext = Color.TRANSPARENT;
            } else {
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
                backgroundDarkNext = Color.TRANSPARENT;
            }

            backgroundDark = (Integer) evaluator.evaluate(positionOffset, backgroundDark, backgroundDarkNext);
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
                    systemUiVisibility |= flagLightStatusBar;
                } else {
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
        if (buttonNext != null && buttonNext instanceof ImageButton) {
            ImageButton button = (ImageButton) buttonNext;
            float offset = 0;
            if (finishEnabled && position + positionOffset >= adapter.getCount() - 2) {
                offset = Math.min(position + positionOffset - adapter.getCount() + 2, 1);
            }

            if (offset <= 0) {
                button.setImageResource(R.drawable.ic_next);
                button.getDrawable().setAlpha(0xFF);
            } else {
                button.setImageResource(R.drawable.ic_next_finish);
                if (button.getDrawable() != null && button.getDrawable() instanceof LayerDrawable) {
                    LayerDrawable drawable = (LayerDrawable) button.getDrawable();
                    drawable.getDrawable(0).setAlpha((int) (0xFF * (1 - offset)));
                    drawable.getDrawable(1).setAlpha((int) (0xFF * offset));
                } else {
                    button.setImageResource(offset > 0 ? R.drawable.ic_finish : R.drawable.ic_next);
                }
            }
        }
    }

    private void updateButtonSkipDrawable() {
        if (buttonSkip != null && buttonSkip instanceof ImageButton) {
            ImageButton button = (ImageButton) buttonSkip;
            if (skipEnabled) {
                button.setImageResource(R.drawable.ic_skip);
            } else {
                button.setImageResource(R.drawable.ic_previous);
            }
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
        object.setPosition(++positionTmp);
        adapter.addSlide(location, object);
    }

    protected boolean addSlide(Slide object) {
        object.setPosition(++positionTmp);
        return adapter.addSlide(object);
    }

    protected boolean addSlides(int location, @NonNull Collection<? extends Slide> collection) {
        Iterator<? extends Slide> iterator = collection.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPosition(++positionTmp);
        }

        return adapter.addSlides(location, collection);
    }

    protected boolean addSlides(@NonNull Collection<? extends Slide> collection) {
        Iterator<? extends Slide> iterator = collection.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPosition(++positionTmp);
        }

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
        return adapter.getCount();
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

            updateBackground();
            updateViewPositions();
            updateFullscreen();

            finishIfNeeded();
        }

        @Override
        public void onPageSelected(int position) {
            IntroActivity.this.position = position;
            updateTaskDescription();
            lockOrUnlockSwipeableIfNeeded(position);
        }
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    public void setAllowNextForSlideByPosition(int position) {
        getSlide(position).setAllowSlideNext(true);
    }

    public void setAllowNextForSlideByFragmentTag(String tag) {
        pager.setSwipeable(true);

        for (int i = 0; i < this.adapter.getCount(); i++) {
            if (getSlide(i).getFragment() != null && getSlide(i).getFragment().getTag().equals(tag)) {
                setAllowNextForSlideByPosition(i);
                break;
            }
        }
    }

    public void setAllowPreviousForSlideByPosition(int position) {
        getSlide(position).setAllowSlidePrevious(true);
    }

    public void setAllowPreviousForSlideByFragmentTag(String tag) {
        pager.setSwipeable(true);

        for (int i = 0; i < this.adapter.getCount(); i++) {
            if (getSlide(i).getFragment() != null && getSlide(i).getFragment().getTag().equals(tag)) {
                setAllowPreviousForSlideByPosition(i);
                break;
            }
        }
    }

    private void lockOrUnlockSwipeableIfNeeded(int position) {
        if (position < getCount()) {
            if (!getSlide(position).isAllowSlideNext() || !getSlide(position).isAllowSlidePrevious()) {
                pager.setSwipeable(false);
            } else {
                pager.setSwipeable(true);
            }
        }
    }

}
