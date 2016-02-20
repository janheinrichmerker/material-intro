material-intro
=======================

[![jitpack.io][18]][4]
[![Build Status][19]][20]
[![Apache License 2.0][21]][22]

A simple material design app intro with cool animations and a simple API.

_Very inspired by Google's app intros._

Demo video on YouTube: https://youtu.be/eKnCHw0UTrk

Demo
----
A demo APK is available in the [releases][16] section. Grab the latest release [here][17]. 

Screenshots
-----------

| Simple slide | Custom slide | Fade effect | Fullscreen |
|:-:|:-:|:-:|:-:|
| ![Simple slide][12] | ![Custom slide][13] | ![Fade effect][14] | ![Fullscreen][15] |
| [_SimpleSlide.java_][1] | [_FragmentSlide.java_][2] | [_IntroActivity.java_][3] | [_IntroActivity.java_][3] |

Dependency
----------

*material-intro* is available on [**jitpack.io**][4]

**Gradle dependency:**
````gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````
````gradle
dependencies {
    compile 'com.heinrichreimersoftware:material-intro:-SNAPSHOT'
}
````

How-To-Use
-----

**Step 1:** Your `Activity` must extend [`IntroActivity`][3]:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}
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

**Step 3 (Optional):** Enable Fullscreen:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setFullscreen(true);
        super.onCreate(savedInstanceState);
    }
}
````

**Step 4 (Optional):** Add your own page change listeners:
````java
public class MainIntroActivity extends IntroActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //TODO
            }
            @Override
            public void onPageSelected(int position) {
                //TODO
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //TODO
            }
        });
    }
}
````

Changes
-------

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
[12]: http://i.imgur.com/1lne2ys.png
[13]: http://i.imgur.com/J2iymrL.png
[14]: http://i.imgur.com/Xyok5qh.png
[15]: http://i.imgur.com/ft1wz2N.png
[16]: https://github.com/HeinrichReimer/material-intro/releases
[17]: https://github.com/HeinrichReimer/material-intro/releases/latest
[18]: https://jitpack.io/v/com.heinrichreimersoftware/material-intro.svg
[19]: https://travis-ci.org/HeinrichReimer/material-intro.svg?branch=master
[20]: https://travis-ci.org/HeinrichReimer/material-intro
[21]: https://img.shields.io/github/license/HeinrichReimer/material-intro.svg
[22]: https://www.apache.org/licenses/LICENSE-2.0.html
