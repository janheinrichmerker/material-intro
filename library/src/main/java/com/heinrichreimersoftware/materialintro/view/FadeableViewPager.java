package com.heinrichreimersoftware.materialintro.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FadeableViewPager extends SwipeBlockableViewPager {

    public FadeableViewPager(Context context) {
        super(context);
    }

    public FadeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new OnPageChangeListenerWrapper(listener));
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(new OnPageChangeListenerWrapper(listener));
    }

    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        super.removeOnPageChangeListener(new OnPageChangeListenerWrapper(listener));
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, new PageTransformerWrapper(transformer, getAdapter()));
    }


    private class OnPageChangeListenerWrapper implements OnPageChangeListener{
        private final OnPageChangeListener listener;

        private OnPageChangeListenerWrapper(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int count = listener instanceof OnOverscrollPageChangeListener ?
                    FadeableViewPager.super.getAdapter().getCount() : getAdapter().getCount();
            listener.onPageScrolled(Math.min(position, count),
                    position < count ? positionOffset : 0,
                    position < count ? positionOffsetPixels : 0);
        }

        @Override
        public void onPageSelected(int position) {
            int count = listener instanceof OnOverscrollPageChangeListener ?
                    FadeableViewPager.super.getAdapter().getCount() : getAdapter().getCount();
            listener.onPageSelected(Math.min(position, count));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            listener.onPageScrollStateChanged(state);
        }
    }


    private class PageTransformerWrapper implements PageTransformer{
        private final PageTransformer pageTransformer;
        private final PagerAdapter adapter;

        private PageTransformerWrapper(PageTransformer pageTransformer, PagerAdapter adapter) {
            this.pageTransformer = pageTransformer;
            this.adapter = adapter;
        }

        @Override
        public void transformPage(View page, float position) {
            pageTransformer.transformPage(page, Math.min(position, adapter.getCount() - 1));
        }
    }


    public interface OnOverscrollPageChangeListener extends OnPageChangeListener {}

    public static class SimpleOnOverscrollPageChangeListener implements OnOverscrollPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
