package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationInterface;
import com.heinrichreimersoftware.materialintro.app.SetNavigationStateInterface;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.heinrichreimersoftware.materialintro.slide.Slide;

import java.util.ArrayList;
import java.util.List;

public class MaterialIntroActivity extends IntroActivity {

    public static final String EXTRA_FULLSCREEN = "com.heinrichreimersoftware.materialintro.demo.EXTRA_FULLSCREEN";

    public static final String EXTRA_SCROLLABLE = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SCROLLABLE";
    public static final String EXTRA_CUSTOM_FRAGMENTS = "com.heinrichreimersoftware.materialintro.demo.EXTRA_CUSTOM_FRAGMENTS";
    public static final String EXTRA_SKIP_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SKIP_ENABLED";
    public static final String EXTRA_FINISH_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_FINISH_ENABLED";

    private List<Slide> slides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        boolean fullscreen = intent.getBooleanExtra(EXTRA_FULLSCREEN, false);
        boolean scrollable = intent.getBooleanExtra(EXTRA_SCROLLABLE, false);
        boolean customFragments = intent.getBooleanExtra(EXTRA_CUSTOM_FRAGMENTS, true);
        boolean skipEnabled = intent.getBooleanExtra(EXTRA_SKIP_ENABLED, true);
        boolean finishEnabled = intent.getBooleanExtra(EXTRA_FINISH_ENABLED, true);

        setFullscreen(fullscreen);

        super.onCreate(savedInstanceState);

        setSkipEnabled(skipEnabled);
        setFinishEnabled(finishEnabled);

        slides = new ArrayList<>();

        slides.add(new SimpleSlide.Builder()
                .title(R.string.title_material_metaphor)
                .description(R.string.description_material_metaphor)
                .image(R.drawable.art_material_metaphor)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .scrollable(scrollable)
                .build());

        slides.add(new SimpleSlide.Builder()
                .title(R.string.title_material_bold)
                .description(R.string.description_material_bold)
                .image(R.drawable.art_material_bold)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .scrollable(scrollable)
                .build());

        slides.add(new SimpleSlide.Builder()
                .title(R.string.title_material_motion)
                .description(R.string.description_material_motion)
                .image(R.drawable.art_material_motion)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .scrollable(scrollable)
                .build());

        slides.add(new SimpleSlide.Builder()
                .title(R.string.title_material_shadow)
                .description(R.string.description_material_shadow)
                .image(R.drawable.art_material_shadow)
                .background(R.color.color_material_shadow)
                .backgroundDark(R.color.color_dark_material_shadow)
                .scrollable(scrollable)
                .build());

        if(customFragments){
            slides.add(new FragmentSlide.Builder()
                    .background(R.color.color_custom_fragment_1)
                    .backgroundDark(R.color.color_dark_custom_fragment_1)
                    .fragment(LoginFragment.newInstance())
                    .allowNext(false)
                    .build());

            slides.add(new FragmentSlide.Builder()
                    .background(R.color.color_custom_fragment_2)
                    .backgroundDark(R.color.color_dark_custom_fragment_2)
                    .fragment(R.layout.fragment_custom, R.style.AppThemeDark)
                    .build());
        }

        addSlides(slides);

        setNavigationInterface(new NavigationInterface() {
            @Override
            public boolean onNextClick(int position) {
                return slides.get(position).isAllowSlideNext();
            }

            @Override
            public boolean onPreviousClick(int position) {
                return slides.get(position).isAllowSlidePrevious();
            }

            @Override
            public void onImpossibleToNavigate(int position, int direction) {
                Toast.makeText(getApplicationContext(), R.string.toast_impossible_to_navigate, Toast.LENGTH_LONG).show();
            }
        });

        //Feel free to add and remove page change listeners to request permissions or such
        /*
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        */
    }

}
