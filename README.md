material-intro
=======================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-material--intro-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3206)
[![jitpack.io][18]][4]
[![Build Status][19]][20]
[![Methods & Size](https://img.shields.io/badge/Methods & Size-Core: 609, Deps: 15054, 104 KB-e91e63.svg)](http://www.methodscount.com/?lib=com.heinrichreimersoftware%3Amaterial-intro%3A1.4)
[![Apache License 2.0][21]][22]

A simple material design app intro with cool animations and a simple API.

_Very inspired by Google's app intros._

Demo video on YouTube: https://youtu.be/eKnCHw0UTrk

Demo
----
A demo app is available on Google Play:

<a href="https://play.google.com/store/apps/details?id=com.heinrichreimersoftware.materialintro.demo">
	<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="60" />
</a>

Screenshots
-----------

| Simple slide | Custom slide | Fade effect | Permission request |
|:-:|:-:|:-:|:-:|
| ![Simple slide][12] | ![Custom slide][13] | ![Fade effect][14] | ![Permission request][15] |
| [_SimpleSlide.java_][1] | [_FragmentSlide.java_][2] | [_IntroActivity.java_][3] | [_SimpleSlide.java_][1] |

Dependency
----------

*material-intro* is available on [**jitpack.io**][4]

**Gradle dependency:**
````gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
````
````gradle
dependencies {
    compile 'com.heinrichreimersoftware:material-intro:1.5'
}
````

How-To-Use
-----

**Step 1:** Your `Activity` must extend [`IntroActivity`][3] and be in your *AndroidManifest.java*:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}
````

````xml
<manifest ...>
    <application ...>
        <activity android:name=".MainIntroActivity"
            android:theme="@style/Theme.Intro"/>
    </application>
</manifest>
````

**Step 2:** Add Slides:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
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
````
Slide types:

- [`SimpleSlide`][2]: Standard slide featuring a title, short description and image like Google's intros.
- [`FragmentSlide`][1]: Custom slide containing a `Fragment` or a layout resource.
- [`Slide`][1]: Base slide. If you want to modify what's shown in your slide this is the way to go.
- Feel free to submit an [issue][10] or [pull request][11] if you think any slide types are missing

**Step 3:** Enable features:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
    
        /* Enable/disable fullscreen */
        setFullscreen(true);
        
        super.onCreate(savedInstanceState);
    
        /* Enable/disable skip button */
        setSkipEnabled(true);
    
        /* Enable/disable finish button */
        setFinishEnabled(true);

        /* Add a navigation policy to define when users can go forward/backward */
        setNavigationPolicy(new NavigationPolicy() {
            @Override
            public boolean canGoForward(int position) {
                return true;
            }

            @Override
            public boolean canGoBackward(int position) {
                return false;
            }
        });

        /* Add a listener to detect ehen users try to go to a page they can't go to */
        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int position, int direction) {
            }
        });
        
        /* Add your own page change listeners */
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
````

Changes
-------

* **Version 1.2.1:**
    * Fixed annotation bug
* **Version 1.2:**
    * Validation feature (#7, 55d2d08)
    * Permission requests (55d2d08)
    * Bug fixes
* **Version 1.1:**
    * Bug fixes (#1, #4, #5, #6, #21)
    * Option to disable skip button / finish button (#3)
    * Added demo on how to open a new activity after completing the inro (#14)
    * Added scrollable slides (#22)
* **Version 1.0:**
    * Initial release

Open source libraries
-------

*material-intro* uses the following open source files:

* [CheatSheet.java][5] by [@Roman Nurik][6] (Apache License 2.0)
* [AnimUtils.java][7] by [@Nick Butcher][8] (Apache License 2.0)
* [InkPageIndicator.java][9] by [@Nick Butcher][8] (Apache License 2.0)

License
-------

    Copyright 2016 Heinrich Reimer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
    
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
