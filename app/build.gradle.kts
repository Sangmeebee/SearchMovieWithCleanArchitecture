plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "camp.nextstep.edu.github"
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

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(MATERIAL)
        implementation(CONSTRAINT_LAYOUT)
        implementation(FRAGMENT_KTX)
    }

    HiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
    }

    UITestConfig.run {
        androidTestImplementation(JUNIT_JUPITER_API)
        androidTestImplementation(JUNIT)
        androidTestImplementation(ESPRESSO_CORE)
        androidTestImplementation(JUNIT5_CORE)
        androidTestRuntimeOnly(JUNIT5_RUNNER)
    }

}