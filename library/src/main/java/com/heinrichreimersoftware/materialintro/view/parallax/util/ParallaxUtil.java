package com.heinrichreimersoftware.materialintro.view.parallax.util;

import android.support.annotation.FloatRange;
import android.view.View;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.view.parallax.Parallaxable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ParallaxUtil {
    private ParallaxUtil() {}

    public static List<Parallaxable> findParallaxableChildren(View root) {
        List<Parallaxable> parallaxableChildrenFound = new LinkedList<>();
        Queue<View> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            View child = queue.remove();
            if (child instanceof Parallaxable) {
                parallaxableChildrenFound.add((Parallaxable) child);
            } else if (child instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) child;
                for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                    queue.add(viewGroup.getChildAt(i));
                }
            }
        }
        return parallaxableChildrenFound;
    }

    /**
     * Set the provided offset to a list of parallaxable items.
     *
     * @param parallaxableChildren The list of parallaxable items to set the offset to.
     * @param offset The offset to assign.
     */
    public static void setOffsetToParallaxableList(
            final List<Parallaxable> parallaxableChildren,
            @FloatRange(from = -1.0, to = 1.0) float offset
    ) {
        if (!parallaxableChildren.isEmpty()) {
            for (Parallaxable parallaxable : parallaxableChildren) {
                parallaxable.setOffset(offset);
            }
        }
    }

}
