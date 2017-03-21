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

package com.heinrichreimersoftware.materialintro.demo;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class SplashIntroActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_metaphor)
                .description(R.string.description_material_metaphor)
                .image(R.drawable.art_material_metaphor)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_bold)
                .description(R.string.description_material_bold)
                .image(R.drawable.art_material_bold)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_motion)
                .description(R.string.description_material_motion)
                .image(R.drawable.art_material_motion)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .build());
    }

}
