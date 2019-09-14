![Icon](http://i.imgur.com/biiZxND.png)

# material-intro

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-material--intro-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3206)
[![JitPack](https://jitpack.io/v/com.heinrichreimersoftware/material-intro.svg)](https://jitpack.io/#com.heinrichreimersoftware/material-intro)
[![Downloads](https://jitpack.io/v/com.heinrichreimersoftware/material-intro/month.svg)](https://jitpack.io/#com.heinrichreimersoftware/material-intro)
[![Build Status](https://travis-ci.org/heinrichreimer/material-intro.svg?branch=master)](https://travis-ci.org/heinrichreimer/material-intro)
[![License](https://img.shields.io/github/license/heinrichreimer/material-intro.svg)](https://github.com/heinrichreimer/material-intro/blob/master/LICENSE.txt)

A simple material design app intro with cool animations and a fluent API.

_Very inspired by Google's app intros._

### Demo:

A demo app is available on Google Play:

<a href="https://play.google.com/store/apps/details?id=com.heinrichreimersoftware.materialintro.demo">
	<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="60" />
</a>

### Screenshots:

| Simple slide | Custom slide | Fade effect | Permission request |
|:-:|:-:|:-:|:-:|
| ![Simple slide](http://i.imgur.com/cLWW5qm.png) | ![Custom slide](http://i.imgur.com/hmhnzUb.png) | ![Fade effect](http://i.imgur.com/7ujB0S4.png) | ![Permission request](http://i.imgur.com/EDNLGy8.png) |
| [`SimpleSlide.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/SimpleSlide.java) | [`FragmentSlide.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/FragmentSlide.java) | [`IntroActivity.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/IntroActivity.java) | [`SimpleSlide.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/SimpleSlide.java) |

### Features:

* Material design ([check the docs](https://material.io/guidelines/growth-communications/onboarding.html#onboarding-top-user-benefits))
* Easy customization
* Predefined slide layouts
* Custom slides
* Autoplay
* Parallax slides
* Fluent API

## Table of Contents

1. [Setup](#setup)
    1. [Gradle Dependency](#gradle-dependency)
    1. [Requirements](#requirements)
1. [Slides](#slides)
    1. [Standard slide](#standard-slide-simpleslide)
    1. [Fragment slide](#fragment-slide-fragmentslide)
    1. [Custom slide](#custom-slide-slide)
1. [Customization](#customization)
    1. [Back button](#left-back-button)
    1. [Next button](#right-next-button)
    1. [CTA button](#cta-button)
    1. [Fullscreen](#fullscreen)
    1. [Scroll duration](#scroll-duration)
1. [Navigation](#navigation)
    1. [Block navigation](#block-navigation)
    1. [Navigate to slides](#navigate-to-slides)
    1. [Autoplay](#autoplay)
    1. [Callbacks](#callbacks)
1. [Tips & Tricks](#tips--tricks)
    1. [Activity result](#activity-result)
    1. [Parallax slides](#parallax-slides)
    1. [Splash screens](#splash-screens)
1. [Apps using this library](#apps-using-this-library)
1. [Changes](#changes)
1. [Open-source libraries](#open-source-libraries)
1. [License](#license)

## Setup:

### Gradle Dependency:

*material-intro* is available on [**jitpack.io**](https://jitpack.io/#com.heinrichreimersoftware/material-intro).

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add this in your module `build.gradle` file:
```gradle
dependencies {
    implementation 'com.heinrichreimersoftware:material-intro:1.6.2'
}
```

### Requirements:
The activity must extend [`IntroActivity`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/IntroActivity.java) and have a theme extending `@style/Theme.Intro`:
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

_(Unless mentioned otherwise all of the following method calls should go in the activity's `onCreate()`.)_

## Slides:

_material-intro_ has fluent style builders for both a simple text/image slide, as seen in Google's apps, and for slides featuring a custom `Fragment` or layout resource.

Feel free to submit an [issue](https://github.com/heinrichreimer/material-intro/issues/new) or [pull request](https://github.com/HeinrichReimer/material-intro/compare) if you think any slide types are missing.

### Standard slide ([`SimpleSlide`](https://github.com/HeinrichReimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/SimpleSlide.java)):

Standard slide featuring a title, short description and image like Google's intros.

```java
addSlide(new SimpleSlide.Builder()
        .title(R.string.title_1)
        .description(R.string.description_1)
        .image(R.drawable.image_1)
        .background(R.color.background_1)
        .backgroundDark(R.color.background_dark_1)
        .scrollable(false)
        .permission(Manifest.permission.CAMERA)
        .build());
```

### Fragment slide ([`FragmentSlide`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/FragmentSlide.java)):

Custom slide containing a `Fragment` or a layout resource.

```java
addSlide(new FragmentSlide.Builder()
        .background(R.color.background_2)
        .backgroundDark(R.color.background_dark_2)
        .fragment(R.layout.fragment_2, R.style.FragmentTheme)
        .build());
```

(When using `FragmentSlide` with a `Fragment`, you should extend [`SlideFragment`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/SlideFragment.java) to proxy navigation calls to the intro activity.)

### Custom slide ([`Slide`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/Slide.java)):

Base slide. If you want to modify what's shown in your slide this is the way to go.

## Customization:

### Left ("back") button:

Control left button behavior or hide/show it.

```java
/* Show/hide button */
setButtonBackVisible(true);
/* Use skip button behavior */
setButtonBackFunction(BUTTON_BACK_FUNCTION_SKIP);
/* Use back button behavior */
setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
```

### Right ("next") button:

Control right button behavior or hide/show it.

```java
/* Show/hide button */
setButtonNextVisible(true);
/* Use next and finish button behavior */
setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);
/* Use next button behavior */
setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT);
```

### CTA button:

Change the appearance of the "call to action" button:

```java
/* Show/hide button */
setButtonCtaVisible(getStartedEnabled);
/* Tint button text */
setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);
/* Tint button background */
setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);
/* Set custom CTA label */
setButtonCtaLabel(R.string.start)
/**/
setButtonCtaClickListener(new View.OnClickListener() {
        
});
```

### Fullscreen:

Display the intro activity at fullscreen. This hides the status bar.

```java
public class MainIntroActivity extends IntroActivity{
    @Override protected void onCreate(Bundle savedInstanceState){
        setFullscreen(true);
        super.onCreate(savedInstanceState);
	...
    }
}
```

Make sure to call `setFullscreen()` **before** calling `super.onCreate()`

### Scroll duration:

Adjust how long a single slide scroll takes.

```java
setPageScrollDuration(500);
```

(The page scroll duration is dynamically adapted when scrolling more than one position to reflect [**dynamic durations**](https://material.io/guidelines/motion/duration-easing.html#duration-easing-dynamic-durations) from the [Material design docs](https://material.io/guidelines/motion/duration-easing.html#duration-easing-dynamic-durations).  
The exact formula for calculating the scroll duration is `duration * (distance + sqrt(distance)) / 2`.)

## Navigation:

### Block navigation:

There are three ways to block navigation for a slide:

1. In a [`SlideFragment`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/SlideFragment.java) (using a [`FragmentSlide`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/slide/FragmentSlide.java)) by overriding `canGoForward()`/`canGoBackward()` methods.
2. For a `SimpleSlide` by setting `SimpleSlide.Builder#canGoForward(boolean)`/`SimpleSlide.Builder#canGoBackward(boolean)`.  
    (If at least one permission is set to the `SimpleSlide`, navigation is automatically blocked until every permission is granted.)
3. From your `IntroActivity` by setting a [`NavigationPolicy`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/app/NavigationPolicy.java):
    ```java
    setNavigationPolicy(new NavigationPolicy() {
        @Override public boolean canGoForward(int position) {
            return true;
        }
    
        @Override public boolean canGoBackward(int position) {
            return false;
        }
    });
    ```

### Navigate to slides

Navigate through the intro manually using one of the following methods e.g. from a listeners callback.
Each of them will respect [blocked navigation](#block-navigation) settings.

```java
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
```

### Autoplay

Automatically scroll through the intro until user interacts with the intro.

```java
/* Start an auto slideshow with 2500ms delay between scrolls and infinite repeats */
autoplay(2500, INFINITE);
/* Start an auto slideshow with default configuration  */
autoplay();
/* Cancel the slideshow */
cancelAutoplay()
```

### Callbacks

If any of the above returns `false` when a user tries to navigate to a slide, a `OnNavigationBlockedListener` callback is fired that you can use to display a message. Additionally you can add plain-old `ViewPager.OnPageChangeListener`s to listen for page scrolls:
```java
addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
    @Override public void onNavigationBlocked(int position, int direction) { ... }
});

addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
    @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { ... }
    @Override public void onPageSelected(int position) { ... }
    @Override public void onPageScrollStateChanged(int state) { ... }
});
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

You can easily achieve a nice looking parallax effect for any slide by using either [`ParallaxFrameLayout.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxFrameLayout.java), [`ParallaxLinearLayout.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxLinearLayout.java) or [`ParallaxRelativeLayout.java`](https://github.com/heinrichreimer/material-intro/blob/master/library/src/main/java/com/heinrichreimersoftware/materialintro/view/parallax/ParallaxRelativeLayout.java) and defining `layout_parallaxFactor` for its direct childrens.  
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

Check the ["Canteen"-demo](https://github.com/heinrichreimer/material-intro/blob/master/app/src/main/res/layout/slide_canteen.xml) for a layout example.

### Splash screens:

If you want to show the intro only at the first app start you can use `onActivityResult()` to store a shared preference when the user finished the intro.

See the demo app for a sample splash screen implementation:
- **[SplashActivity.java](https://github.com/heinrichreimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/SplashActivity.java)**: A fake splash screen
- **[SplashIntroActivity.java](https://github.com/heinrichreimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/SplashIntroActivity.java)**: The intro started from FinishActivity
- **[FinishActivity.java](https://github.com/heinrichreimer/material-intro/blob/master/app/src/main/java/com/heinrichreimersoftware/materialintro/demo/FinishActivity.java)**: The activity started after the splash screen

## Apps using this library:

- [**Simply Unroot**](https://play.google.com/store/apps/details?id=de.pinpong.simplyunroot) by @pinpong
- [**Prodigal Music Player**](https://play.google.com/store/apps/details?id=bob.sun.prodigal) by @SpongeBobSun 
- [**Puff Password Utility**](https://play.google.com/store/apps/details?id=sun.bob.leela) by @SpongeBobSun  
- [**QualityGate**](https://play.google.com/store/apps/details?id=com.frontendart.qualitygate.android) by @FrontEndART 
- [**Monitor**](https://play.google.com/store/apps/details?id=com.danielstone.energyhive) by @daniel-stoneuk 
- [**Contact Lenses Time**](https://play.google.com/store/apps/details?id=com.brando.lenti) by @ste23droid 
- [**Antidote Danmark**](https://play.google.com/store/apps/details?id=dk.antidotedanmark) by @AntidoteDanmark
- [**Speed Boost Lite**](https://play.google.com/store/apps/details?id=com.TMillerApps.SpeedBoostLite) by @tjmille2

## Changes:

See the [releases section](https://github.com/heinrichreimer/material-intro/releases) for changelogs.

## Open-source libraries:

*material-intro* uses the following open-source files:

* [CheatSheet.java](https://gist.github.com/romannurik/3982005) by [@Roman Nurik](https://github.com/romannurik/) (Apache License 2.0)
* [AnimUtils.java](https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/util/AnimUtils.java) by [@Nick Butcher](https://github.com/nickbutcher/) (Apache License 2.0), _modified version_
* [InkPageIndicator.java](https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/ui/widget/InkPageIndicator.java) by [@Nick Butcher](https://github.com/nickbutcher/) (Apache License 2.0)

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
