plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")

}

val properties = org.jetbrains.kotlin.konan.properties.Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

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
        //room to json
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }

        //add BuildConfig Field
        buildConfigField("String",
            "KAKAO_NATIVE_APP_KEY",
            properties["kakao_native_app_key"].toString())
        buildConfigField("String",
            "GOOGLE_WEB_CLIENT_ID",
            properties["google_web_client_id"].toString())

        //set manifest placeholder
        manifestPlaceholders["kakaoNativeAppKey"] = "kakao${properties["kakao_native_app_key"]}"
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

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":cache"))

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(SPLASH_SCREEN)
        implementation(MATERIAL)
        implementation(CONSTRAINT_LAYOUT)
        implementation(SWIPE_REFRESH_LAYOUT)
        implementation(FRAGMENT_KTX)
    }


    NavigationConfig.run {
        implementation(FRAGMENT_KTX)
        implementation(UI_KTX)
    }


    HiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    implementation(CoilConfig.COIL)

    implementation(PagingConfig.PAGING_RUNTIME)

    implementation(KakaoConfig.KAKAO_LOGIN)

    GoogleConfig.run {
        implementation(PLAY_SERVICE_AUTH)
        implementation(platform(FIREBASE_BOM))
        implementation(FIREBASE_AUTH)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(MOCKK)
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