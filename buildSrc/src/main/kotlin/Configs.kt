object GradleConfig {
    const val GRADLE = "com.android.tools.build:gradle:7.2.2"
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:10.0.0"
    const val ANDROID_JUNIT5 = "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1"
}

object KotlinConfig {
    private const val kotlinVersion = "1.7.10"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
}

object CoroutinesConfig {
    const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
}

object AndroidConfig {
    private const val fragmentKTXVersion = "1.5.2"

    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:$fragmentKTXVersion"
    const val CORE_KTX = "androidx.core:core-ktx:1.8.0"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.5.0"
    const val MATERIAL = "com.google.android.material:material:1.6.1"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
}

object HiltConfig {
    private const val hiltVersion = "2.43.2"

    const val ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val ANDROID = "com.google.dagger:hilt-android:$hiltVersion"
    const val COMPILER = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val CORE = "com.google.dagger:hilt-core:$hiltVersion"
}

/**
 * gradle for test
 * */

object UnitTestConfig {

    private const val junit5Version = "5.9.0"

    const val JUNIT = "junit:junit:4.13.2"
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter:$junit5Version"
    const val JUNIT_VINTAGE_ENGINE = "org.junit.vintage:junit-vintage-engine:$junit5Version"
    const val TRUTH = "com.google.truth:truth:1.1.3"
    const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
}

object UITestConfig {
    private const val junit5Version = "5.9.0"
    private const val junit5AndroidTestVersion = "1.2.2"

    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api:$junit5Version"
    const val JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    const val JUNIT5_CORE = "de.mannodermaus.junit5:android-test-core:$junit5AndroidTestVersion"
    const val JUNIT5_RUNNER = "de.mannodermaus.junit5:android-test-runner:$junit5AndroidTestVersion"
}
