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

package com.heinrichreimersoftware.materialintro.app;

interface IntroNavigation {
    /**
     * Tries to go to the given position and will obey {@code canGoForward()} or
     * {@code canGoBackward()} if {@code forceScroll} is {@code false} returns {@code false}.
     *
     * @param position The position the pager should go to.
     * @param forceScroll Forces scroll to the specified position
     * @return {@code true} if the pager was able to go the complete way to the given position,
     * {@code false} otherwise.
     */
    boolean goToSlide(int position, boolean forceScroll);

    /**
     * Tries to go to the next slide if {@code canGoForward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the next slide, {@code false} otherwise.
     */
    boolean nextSlide();

    /**
     * Goes to the next slide even {@code canGoForward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the next slide, {@code false} otherwise.
     */
    boolean forceNextSlide();


    /**
     * Tries to go to the previous slide if {@code canGoBackward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the previous slide, {@code false}
     * otherwise.
     */
    boolean previousSlide();

    /**
     * Goes to the previous slide even {@code canGoBackward()} returns {@code true}.
     *
     * @return {@code true} if the pager was able to go to the previous slide, {@code false} otherwise.
     */
    boolean forcePreviousSlide();

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
