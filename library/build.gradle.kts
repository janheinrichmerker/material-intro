plugins {
    id("com.android.library")
    id("com.palantir.git-version")
}

android {
    namespace = "com.heinrichreimersoftware.materialintro"

    compileSdk = 29

    defaultConfig {
        minSdk = 15
        targetSdk = 29
        versionCode = 20000
        versionName = gitVersion()
    }

    lintOptions {
        abortOnError = false
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
}
