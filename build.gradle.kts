// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        GradleConfig.run {
            classpath(GRADLE)
            classpath(KTLINT)
            classpath(ANDROID_JUNIT5)
        }

        classpath(KotlinConfig.KOTLIN_GRADLE_PLUGIN)
        classpath(HiltConfig.ANDROID_GRADLE_PLUGIN)
        classpath(NavigationConfig.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
        classpath(GoogleConfig.GOOGLE_SERVICE_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
