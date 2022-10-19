plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.protobuf") version "0.9.1"
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
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
}

dependencies {

    implementation(project(":data"))

    implementation(CoroutineConfig.CORE)

    implementation(PagingConfig.PAGING_COMMON)

    RoomConfig.run {
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
        implementation(ROOM_KTX)
        implementation(ROOM_PAGING)
    }

    implementation(DataStoreConfig.DATASTORE_PROTO)
    implementation(ProtobufConfig.PROTOBUF_JAVALITE)

    implementation(ConverterConfig.GSON)

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
        testImplementation(MOCKK)
        testImplementation(MOCK_WEBSERVER)
    }
}

protobuf {
    protoc {
        artifact = ProtobufConfig.PROTOBUF_PROTOC
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}