
import bakerybuild.internal.libs

plugins {
    id("bakerybuild.common-convention")
    id("com.android.library")
}

android {
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles(
            file("$rootDir/build-logic/src/main/resources/consumer-rules.pro"),
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
