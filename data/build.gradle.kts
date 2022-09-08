plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(":domain"))

    implementation(CoroutineConfig.CORE)

    implementation(PagingConfig.PAGING_COMMON)

    RoomConfig.run {
        implementation(ROOM_COMMON)
    }

    NetworkConfig.run {
        implementation(RETROFIT)
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