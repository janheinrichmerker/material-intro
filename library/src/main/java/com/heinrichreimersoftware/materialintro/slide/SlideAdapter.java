package com.heinrichreimersoftware.materialintro.slide;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SlideAdapter extends FragmentStatePagerAdapter {
    private List<Slide> data = new ArrayList<>();

    public SlideAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        data = new ArrayList<>();
    }

    public SlideAdapter(FragmentManager fragmentManager, @NonNull Collection<? extends Slide> collection) {
        super(fragmentManager);
        data = new ArrayList<>(collection);
    }

    public void addSlide(int location, Slide object) {
        data.add(location, object);
        notifyDataSetChanged();
    }

    public boolean addSlide(Slide object) {
        boolean modified = data.add(object);
        if (modified) notifyDataSetChanged();
        return modified;
    }

    public boolean addSlides(int location, @NonNull Collection<? extends Slide> collection) {
        boolean modified = data.addAll(location, collection);
        if (modified) notifyDataSetChanged();
        return modified;
    }

    public boolean addSlides(@NonNull Collection<? extends Slide> collection) {
        boolean modified = data.addAll(collection);
        if (modified) notifyDataSetChanged();
        return modified;
    }

    public void clearSlides() {
        if (!data.isEmpty()) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsSlide(Object object) {
        return data.contains(object);
    }

    public boolean containsSlides(@NonNull Collection<?> collection) {
        return data.containsAll(collection);
    }

    public Slide getSlide(int location) {
        return data.get(location);
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position).getFragment();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment instantiatedFragment = (Fragment) super.instantiateItem(container, position);
        Slide slide = data.get(position);
        if (slide instanceof RestorableSlide) {
            //Load old fragment from fragment manager
            ((RestorableSlide) slide).setFragment(instantiatedFragment);
            data.set(position, slide);
            if (instantiatedFragment instanceof SlideFragment)
                ((SlideFragment) instantiatedFragment).updateNavigation();
        }
        return instantiatedFragment;
    }

    @ColorRes
    public int getBackground(int position) {
        return data.get(position).getBackground();
    }

    @ColorRes
    public int getBackgroundDark(int position) {
        return data.get(position).getBackgroundDark();
    }

    public List<Slide> getSlides() {
        return data;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public int indexOfSlide(Object object) {
        return data.indexOf(object);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public int lastIndexOfSlide(Object object) {
        return data.lastIndexOf(object);
    }

    public Slide removeSlide(int location) {
        Slide object = data.remove(location);
        notifyDataSetChanged();
        return object;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeSlide(Object object) {
        int locationToRemove = data.indexOf(object);
        if (locationToRemove >= 0) {
            data.remove(locationToRemove);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeSlides(@NonNull Collection<?> collection) {
        boolean modified = false;
        for (Object object : collection) {
            int locationToRemove = data.indexOf(object);
            if (locationToRemove >= 0) {
                data.remove(locationToRemove);
                modified = true;
            }
        }
        if (modified) notifyDataSetChanged();
        return modified;
    }

    public boolean retainSlides(@NonNull Collection<?> collection) {
        boolean modified = false;
        for (int i = data.size() - 1; i >= 0; i--) {
            if(!collection.contains(data.get(i))){
                data.remove(i);
                modified = true;
                i--;
            }
        }
        if (modified) notifyDataSetChanged();
        return modified;
    }

    public Slide setSlide(int location, Slide object) {
        Slide oldObject = data.set(location, object);
        notifyDataSetChanged();
        return oldObject;
    }

    public List<Slide> setSlides(List<? extends Slide> list) {
        List<Slide> oldList = new ArrayList<>(data);
        data = new ArrayList<>(list);
        notifyDataSetChanged();
        return oldList;
    }
}