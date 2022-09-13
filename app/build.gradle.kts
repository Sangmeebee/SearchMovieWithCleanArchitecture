plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sangmeebee.searchmovie"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
//
//    AndroidConfig.run {
//        implementation(APPCOMPAT)
//        implementation(MATERIAL)
//    }

    NavigationConfig.run {
        implementation(FRAGMENT_KTX)
        implementation(UI_KTX)
    }

    RoomConfig.run {
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
        implementation(ROOM_KTX)
    }

    NetworkConfig.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
        implementation(platform(OKHTTP_BOM))
        implementation(OKHTTP)
        implementation(OKHTTP_LOGGING_INTERCEPTOR)
    }


    HiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }
}