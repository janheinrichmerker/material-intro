![Icon](http://i.imgur.com/biiZxND.png)

# material-intro

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-material--intro-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3206)
[![jitpack.io][18]][4]
[![Build Status][19]][20]
[![License](https://img.shields.io/github/license/HeinrichReimer/material-intro.svg)](https://github.com/HeinrichReimer/material-intro/blob/master/LICENSE.txt)

A simple material design app intro with cool animations and a simple API.

_Very inspired by Google's app intros._

### Demo:

A demo app is available on Google Play:

<a href="https://play.google.com/store/apps/details?id=com.heinrichreimersoftware.materialintro.demo">
	<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="60" />
</a>

### Screenshots:

| Simple slide | Custom slide | Fade effect | Permission request |
|:-:|:-:|:-:|:-:|
| ![Simple slide][12] | ![Custom slide][13] | ![Fade effect][14] | ![Permission request][15] |
| [_SimpleSlide.java_][2] | [_FragmentSlide.java_][1] | [_IntroActivity.java_][3] | [_SimpleSlide.java_][2] |

### Features:

* Material design ([check the docs][30])
* Easy customization
* Predefined slide layouts
* Custom slides
* Parallax slides
* Fluent API

## Dependency:

*material-intro* is available on [**jitpack.io**][4]

### Gradle:
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```gradle
dependencies {
    compile 'com.heinrichreimersoftware:material-intro:1.6.2'
}
```

## Quick Setup:

### Requirements:
The activity must extend [`IntroActivity`][3] and have a theme extending `@style/Theme.Intro`:
```java
public class MainIntroActivity extends IntroActivity{
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
	// Add slides, edit configuration...
    }
}
```

```xml
<manifest ...>
    <application ...>
        <activity android:name=".MainIntroActivity"
            android:theme="@style/Theme.Intro"/>
    </application>
</manifest>
```

### Slides:

_material-intro_ has fluent style builders for both a simple text/image slide, as seen in Google's apps, and for slides featuring a custom `Fragment` or layout resource.

```java
public class MainIntroActivity extends IntroActivity{
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_1)
                .description(R.string.description_1)
                .image(R.drawable.image_1)
                .background(R.color.background_1)
                .backgroundDark(R.color.background_dark_1)
                .permission(Manifest.permission.CAMERA)
                .build());

        /**
         * Custom fragment slide
         */
        addSlide(new FragmentSlide.Builder()
                .background(R.color.background_2)
                .backgroundDark(R.color.background_dark_2)
                .fragment(R.layout.fragment_2, R.style.FragmentTheme)
                .build());
    }
}
```

**Types:**

- [`SimpleSlide`][2]: Standard slide featuring a title, short description and image like Google's intros.
- [`FragmentSlide`][1]: Custom slide containing a `Fragment` or a layout resource.
- [`Slide`][1]: Base slide. If you want to modify what's shown in your slide this is the way to go.
- Feel free to submit an [issue][10] or [pull request][11] if you think any slide types are missing

### Additional features:

Enable addional features like fullscreen, control the button behaviors or provide a policy to control, which slides users are allowed to pass.

```java
public class MainIntroActivity extends IntroActivity{
    @Override protected void onCreate(Bundle savedInstanceState){
        /* Enable/disable fullscreen */
        setFullscreen(true);

        super.onCreate(savedInstanceState);

        /* Enable skip button */
        setButtonBackFunction(BUTTON_BACK_FUNCTION_SKIP);
	
        /* Enable back button */
        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
	
        /* Enable next and finish button */
        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);
	
        /* Enable next button */
        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT);
	
        /* Show/hide back button */
        setButtonBackVisible(true);
	
        /* Show/hide next button */
        setButtonNextVisible(true);
	
        /* Show/hide CTA button */
        setButtonCtaVisible(getStartedEnabled);
	
        /* Tint CTA button text/background */
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        /* Add a navigation policy to define when users can go forward/backward */
        setNavigationPolicy(new NavigationPolicy() {
            @Override public boolean canGoForward(int position) {
                return true;
            }

            @Override public boolean canGoBackward(int position) {
                return false;
            }
        });

        /* Add a listener to detect when users try to go to a page they can't go to */
        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override public void onNavigationBlocked(int position, int direction) { ... }
        });

        /* Add your own page change listeners */
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { ... }
            @Override public void onPageSelected(int position) { ... }
            @Override public void onPageScrollStateChanged(int state) { ... }
        });
	
        /* Set how long a slide takes, featuring dynamic durations from the Material design docs */
        setPageScrollDuration(500);
    }
}
```

### Navigation:

_material-intro_ has methods to navigate through the intro e.g. from a listeners callback.
Each of them will respect `NavigationPolicy` and the slide's `canGoForward()`/`canGoBackward()` settings.

```java
public class MainIntroActivity extends IntroActivity{
    @Override protected void onCreate(Bundle savedInstanceState){
        /* Scroll to any position */
        goToSlide(5);

        /* Scroll to the next slide in the intro */
        nextSlide();

        /* Scroll to the previous slide in the intro */
        previousSlide();

        /* Scroll to the last slide of the intro */
        goToLastSlide();

        /* Scroll to the first slide of the intro */
        goToFirstSlide();
	
	/* Start an auto slideshow that stops when user interacts with the intro */
        autoplay(2500, INFINITE);
    }
}
```

## Tips & Tricks:

### Activity result:

You can check if the user canceled the intro (by pressing back) or finished it including all slides.
Just call up the activity using `startActivityForResult(intent, REQUEST_CODE_INTRO);` and then listen for the result:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_INTRO) {
        if (resultCode == RESULT_OK) {
            // Finished the intro
        } else {
            // Cancelled the intro. You can then e.g. finish this activity too.
            finish();
	}
    }
}
```

### Parallax slides:

You can easily acheive a nice looking parallax effect for any slide by using either [_ParallaxFrameLayout.java_][31], [_ParallaxLinearLayout.java_][32] or [_ParallaxRelativeLayout.java_][33] and defining `layout_parallaxFactor` for its direct childrens.  
A higher factor means a stronger parallax effect, `0` means no parallax effect at all.

```xml
<com.heinrichreimersoftware.materialintro.view.parallax.ParallaxLinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    ... >

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_parallaxFactor="0"
        ... />

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_parallaxFactor="1.25"
        ... />

</com.heinrichreimersoftware.materialintro.view.parallax.ParallaxLinearLayout>
```

### Splash screens:

Check out the [*sample*][26] on how to show the intro on first start:

- **[SplashActivity.java][27]**: A fake splash screen
- **[SplashIntroActivity.java][28]**: The intro started from FinishActivity
- **[FinishActivity.java][29]**: The activity started after the splash screen

## Changes:

See the [releases section][25] for changelogs.

## Open-source libraries:

*material-intro* uses the following open source files:

* [CheatSheet.java][5] by [@Roman Nurik][6] (Apache License 2.0)
* [AnimUtils.java][7] by [@Nick Butcher][8] (Apache License 2.0)
* [InkPageIndicator.java][9] by [@Nick Butcher][8] (Apache License 2.0)

## License:

```
MIT License

Copyright (c) 2017 Jan Heinrich Reimer

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


[1]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/FragmentSlide.java
[2]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/SimpleSlide.java
[3]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/IntroActivity.java
[4]: https://jitpack.io/#com.heinrichreimersoftware/material-intro
[5]: https://gist.github.com/romannurik/3982005
[6]: https://github.com/romannurik
[7]: https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/util/AnimUtils.java
[8]: https://github.com/nickbutcher
[9]: https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/ui/widget/InkPageIndicator.java
[10]: issues
[11]: pulls
[12]: http://i.imgur.com/cLWW5qm.png
[13]: http://i.imgur.com/hmhnzUb.png
[14]: http://i.imgur.com/7ujB0S4.png
[15]: http://i.imgur.com/EDNLGy8.png
[18]: https://jitpack.io/v/com.heinrichreimersoftware/material-intro.svg
[19]: https://travis-ci.org/HeinrichReimer/material-intro.svg?branch=master
[20]: https://travis-ci.org/HeinrichReimer/material-intro
[21]: https://img.shields.io/github/license/HeinrichReimer/material-intro.svg
[22]: https://www.apache.org/licenses/LICENSE-2.0.html
[23]: https://img.shields.io/badge/Android%20Arsenal-material--intro-green.svg?style=true
[24]: https://android-arsenal.com/details/1/3206
[25]: https://github.com/HeinrichReimer/material-intro/releases
[26]: https://github.com/HeinrichReimer/material-intro/tree/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo
[27]: https://github.com/HeinrichReimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/SplashActivity.java
[28]: https://github.com/HeinrichReimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/SplashIntroActivity.java
[29]: https://github.com/HeinrichReimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/FinishActivity.java
[30]: https://www.google.com/design/spec/growth-communications/onboarding.html#onboarding-quickstart
[31]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxFrameLayout.java
[32]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxLinearLayout.java
[33]: https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxRelativeLayout.java
