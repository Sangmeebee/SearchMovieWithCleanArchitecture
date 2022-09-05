plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(":domain"))

    implementation(CoroutineConfig.CORE)

    implementation(PagingConfig.PAGING_COMMON)

    NetworkConfig.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
        implementation(platform(OKHTTP_BOM))
        implementation(OKHTTP)
        implementation(OKHTTP_LOGGING_INTERCEPTOR)
    }

    implementation(ConverterConfig.GSON)

    HiltConfig.run {
        implementation(CORE)
        kapt(COMPILER)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
        testImplementation(MOCK_WEBSERVER)
    }
}