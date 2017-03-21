package com.heinrichreimersoftware.materialintro.app;

interface IntroNavigation {
    /**
     * Tries to go to the given position and will stop when {@code canGoForward()} or
     * {@code canGoBackward()} returns {@code false}.
     *
     * @param position The position the pager should go to.
     * @return {@code true} if the pager was able to go the complete way to the given position,
     * {@code false} otherwise.
     */
    boolean goToSlide(int position);

    /**
     * Tries to go to the next slide if {@code canGoForward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the next slide, {@code false} otherwise.
     */
    boolean nextSlide();


    /**
     * Tries to go to the previous slide if {@code canGoForward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the previous slide, {@code false}
     * otherwise.
     */
    boolean previousSlide();

    /**
     * Tries to go to the last slide and will stop when {@code canGoForward()} returns
     * {@code false}.
     *
     * @return {@code true} if the pager was able to go the complete way to the last slide,
     * {@code false} otherwise.
     */
    boolean goToLastSlide();

    /**
     * Tries to go to the first slide and will stop when {@code canGoBackward()} returns
     * {@code false}.
     *
     * @return {@code true} if the pager was able to go the complete way to the first slide,
     * {@code false} otherwise.
     */
    boolean goToFirstSlide();
}
