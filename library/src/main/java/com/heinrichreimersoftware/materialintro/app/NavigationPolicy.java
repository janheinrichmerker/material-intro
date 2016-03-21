package com.heinrichreimersoftware.materialintro.app;

public interface NavigationPolicy {

    /**
     * Return true if going forwards is allowed.
     */
    boolean canGoForward(int position);

    /**
     * Return true if going backwards is allowed.
     */
    boolean canGoBackward(int position);
}
