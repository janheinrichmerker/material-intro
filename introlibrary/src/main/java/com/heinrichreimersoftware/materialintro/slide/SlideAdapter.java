/*
 * MIT License
 *
 * Copyright (c) 2017 Jan Heinrich Reimer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.heinrichreimersoftware.materialintro.slide;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SlideAdapter extends FragmentPagerAdapter {
    private List<Slide> data = new ArrayList<>();
    private FragmentManager fragmentManager;

    public SlideAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        data = new ArrayList<>();
    }

    public SlideAdapter(FragmentManager fragmentManager, @NonNull Collection<? extends Slide> collection) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        data = new ArrayList<>(collection);
    }

    public void addSlide(int location, Slide object) {
        if (!data.contains(object)) {
            data.add(location, object);
        }
    }

    public boolean addSlide(Slide object) {
        if (data.contains(object)) {
            return false;
        }
        boolean modified = data.add(object);
        if (modified) {
            notifyDataSetChanged();
        }
        return modified;
    }

    public boolean addSlides(int location, @NonNull Collection<? extends Slide> collection) {
        boolean modified = false;
        int i = 0;
        for (Slide slide : collection) {
            if (!data.contains(slide)) {
                data.add(location + i, slide);
                i++;
                modified = true;
            }
        }
        if (modified) {
            notifyDataSetChanged();
        }
        return modified;
    }

    public boolean addSlides(@NonNull Collection<? extends Slide> collection) {
        boolean modified = false;
        for (Slide slide : collection) {
            if (!data.contains(slide)) {
                data.add(slide);
                modified = true;
            }
        }
        if (modified) {
            notifyDataSetChanged();
        }
        return modified;
    }

    public boolean clearSlides() {
        if (!data.isEmpty()) {
            data.clear();
            return true;
        }
        return false;
    }

    public boolean containsSlide(Object object) {
        return object instanceof Slide && data.contains(object);
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
    public int getItemPosition(Object object) {
        if (object instanceof Fragment) {
            fragmentManager.beginTransaction()
                    .detach((Fragment) object)
                    .attach((Fragment) object)
                    .commit();
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        if (fragment.isAdded()) {
            return fragment;
        }

        Fragment instantiatedFragment = (Fragment) super.instantiateItem(container, position);
        Slide slide = data.get(position);
        if (slide instanceof RestorableSlide) {
            //Load old fragment from fragment manager
            ((RestorableSlide) slide).setFragment(instantiatedFragment);
            data.set(position, slide);
            if (instantiatedFragment instanceof SlideFragment && instantiatedFragment.isAdded()) {
                ((SlideFragment) instantiatedFragment).updateNavigation();
            }
        }
        return instantiatedFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment == null)
            return;
        super.destroyItem(container, position, object);
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
        return data.remove(location);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeSlide(Object object) {
        int locationToRemove = data.indexOf(object);
        if (locationToRemove >= 0) {
            data.remove(locationToRemove);
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
        return modified;
    }

    public boolean retainSlides(@NonNull Collection<?> collection) {
        boolean modified = false;
        for (int i = data.size() - 1; i >= 0; i--) {
            if (!collection.contains(data.get(i))) {
                data.remove(i);
                modified = true;
                i--;
            }
        }
        return modified;
    }

    public Slide setSlide(int location, Slide object) {
        if (!data.contains(object)) {
            return data.set(location, object);
        }
        return data.set(location, object);
    }

    public List<Slide> setSlides(List<? extends Slide> list) {
        List<Slide> oldList = new ArrayList<>(data);
        data = new ArrayList<>(list);
        return oldList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}