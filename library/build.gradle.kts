plugins {
    id("com.android.library")
    id("com.palantir.git-version")
}

android {
    namespace = "com.heinrichreimersoftware.materialintro"

    compileSdk = 34

    defaultConfig {
        minSdk = 15
    }

    lint {
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
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
}
