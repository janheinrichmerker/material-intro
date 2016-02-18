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
import com.heinrichreimersoftware.materialintro.util.CheatSheet;
import com.heinrichreimersoftware.materialintro.view.FadeableViewPager;
import com.heinrichreimersoftware.materialintro.view.InkPageIndicator;

import java.util.Collection;
import java.util.List;

@SuppressLint("Registered")
public class IntroActivity extends AppCompatActivity {

    private FrameLayout frame;
    private ViewPager pager;
    private InkPageIndicator pagerIndicator;
    private View buttonNext;
    private View buttonSkip;

    private SlideAdapter adapter;

    private boolean fullscreen = true;

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

    protected void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }

    private void findViews(){
        frame = (FrameLayout) findViewById(R.id.mi_frame);
        pager = (ViewPager) findViewById(R.id.mi_pager);
        pagerIndicator = (InkPageIndicator) findViewById(R.id.mi_pager_indicator);
        buttonNext = findViewById(R.id.mi_button_next);
        buttonSkip = findViewById(R.id.mi_button_skip);

        adapter = new SlideAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ButtonListener());
        pager.addOnPageChangeListener(new BackgroundListener());
        pager.addOnPageChangeListener(new TaskDescriptionListener());
        pager.addOnPageChangeListener(new FinishListener());
        pager.addOnPageChangeListener(new FullscreenListener());
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
                finishIntro();
            }
        });
        CheatSheet.setup(buttonNext);
        CheatSheet.setup(buttonSkip);
    }

    private void nextSlide(){
        int currentItem = pager.getCurrentItem();
        pager.setCurrentItem(++currentItem, true);
    }
    private void finishIntro(){
        int count = getCount();
        pager.setCurrentItem(count);
    }

    private void updateTaskDescription(int position){
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

    private void updateTaskDescription(){
        updateTaskDescription(0);
    }



    @SuppressWarnings("deprecation")
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        pager.setOnPageChangeListener(listener);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        pager.addOnPageChangeListener(listener);
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
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

    private class ButtonListener extends FadeableViewPager.SimpleOnOverscrollPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(buttonNext != null && buttonNext instanceof ImageButton){
                ImageButton button = (ImageButton) buttonNext;
                if(button.getDrawable() != null && button.getDrawable() instanceof LayerDrawable){
                    LayerDrawable drawable = (LayerDrawable) button.getDrawable();
                    if(position + positionOffset < adapter.getCount() - 2){
                        drawable.getDrawable(0).setAlpha(255);
                        drawable.getDrawable(1).setAlpha(0);

                        buttonSkip.setTranslationX(0);
                        buttonNext.setTranslationY(0);
                        pagerIndicator.setTranslationY(0);
                    }
                    else if(position + positionOffset < adapter.getCount() - 1){
                        float offset = position + positionOffset - adapter.getCount() + 2;
                        drawable.getDrawable(0).setAlpha((int) (255 * (1 - offset)));
                        drawable.getDrawable(1).setAlpha((int) (255 * offset));

                        boolean rtl = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getResources().getConfiguration().getLayoutDirection() ==
                                View.LAYOUT_DIRECTION_RTL;
                        buttonSkip.setTranslationX(offset * (rtl ? 1 : -1) * pager.getWidth());
                        buttonNext.setTranslationY(0);
                        pagerIndicator.setTranslationY(0);
                    } else{
                        float offset = position + positionOffset - adapter.getCount() + 1;

                        buttonSkip.setTranslationX(-pager.getWidth());
                        buttonNext.setTranslationY(offset * 2 * buttonSkip.getWidth());
                        pagerIndicator.setTranslationY(offset * 2 * buttonSkip.getWidth());
                    }
                }
            }
        }
    }

    private class BackgroundListener extends FadeableViewPager.SimpleOnOverscrollPageChangeListener{
        private final ArgbEvaluator evaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int background;
            int backgroundNext;

            if(position == getCount()){
                background = Color.TRANSPARENT;
                backgroundNext = Color.TRANSPARENT;
            }
            else{
                background = ContextCompat.getColor(IntroActivity.this,
                        getBackground(position));
                backgroundNext = ContextCompat.getColor(IntroActivity.this,
                        getBackground(Math.min(position + 1, getCount() - 1)));

                background = ColorUtils.setAlphaComponent(background, 0xFF);
                backgroundNext = ColorUtils.setAlphaComponent(backgroundNext, 0xFF);
            }

            if (position + positionOffset >= adapter.getCount() - 1){
                backgroundNext = ColorUtils.setAlphaComponent(background, 0x00);
            }

            frame.setBackgroundColor((Integer) evaluator.evaluate(positionOffset, background, backgroundNext));

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int backgroundDark;
                int backgroundDarkNext;

                if(position == getCount()){
                    backgroundDark = Color.TRANSPARENT;
                    backgroundDarkNext = Color.TRANSPARENT;
                }
                else{
                    try {
                        backgroundDark = ContextCompat.getColor(IntroActivity.this,
                                getBackgroundDark(position));
                    } catch (Resources.NotFoundException e){
                        backgroundDark = ContextCompat.getColor(IntroActivity.this,
                                R.color.mi_status_bar_background);
                    }
                    try {
                        backgroundDarkNext = ContextCompat.getColor(IntroActivity.this,
                                getBackgroundDark(Math.min(position + 1, getCount() - 1)));
                    } catch (Resources.NotFoundException e){
                        backgroundDarkNext = ContextCompat.getColor(IntroActivity.this,
                                R.color.mi_status_bar_background);
                    }
                }
                if (position + positionOffset >= adapter.getCount() - 1){
                    backgroundDarkNext = Color.TRANSPARENT;
                }

                backgroundDark = (Integer) evaluator.evaluate(positionOffset, backgroundDark, backgroundDarkNext);
                getWindow().setStatusBarColor(backgroundDark);

                if(position == adapter.getCount()){
                    getWindow().setNavigationBarColor(Color.TRANSPARENT);
                }
                else if(position + positionOffset >= adapter.getCount() - 1){
                    TypedValue typedValue = new TypedValue();
                    TypedArray a = obtainStyledAttributes(typedValue.data, new int[] { android.R.attr.navigationBarColor });

                    int defaultNavigationBarColor = a.getColor(0, Color.BLACK);

                    a.recycle();

                    int navigationBarColor = (Integer) evaluator.evaluate(positionOffset, defaultNavigationBarColor, Color.TRANSPARENT);
                    getWindow().setNavigationBarColor(navigationBarColor);
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                    int flagLightStatusBar = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    if (ColorUtils.calculateLuminance(backgroundDark) > 0.4) {
                        systemUiVisibility |= flagLightStatusBar;
                    }
                    else {
                        systemUiVisibility &= ~flagLightStatusBar;
                    }
                    getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
                }
            }
        }
    }

    private class FinishListener extends FadeableViewPager.SimpleOnOverscrollPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position == adapter.getCount()){
                finish();
                overridePendingTransition(0, 0);
            }
        }
    }

    private class FullscreenListener extends FadeableViewPager.SimpleOnOverscrollPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position + positionOffset > adapter.getCount() - 1){
                setFullscreenFlags(false);
            }
            else{
                setFullscreenFlags(fullscreen);
            }
        }
    }

    private class TaskDescriptionListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            updateTaskDescription(position);
        }
    }
}
