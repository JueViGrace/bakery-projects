import bakerybuild.internal.libs
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

plugins {
    id("bakerybuild.compose-convention")
    id("com.android.application")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // App update
            implementation(libs.androidx.app.update)
            implementation(libs.androidx.app.update.ktx)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.androidx.compose.navigation)
        }

        commonMain.dependencies {
            /*
             * Projects
             */

            // Core
            implementation(project(":modules:core"))

            // Resources
            implementation(project(":modules:resources"))

            // Types
            implementation(project(":modules:core:types"))

            // UI
            implementation(project(":modules:ui"))

            /*
             * Dependencies
             */

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
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
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources.excludes += "DebugProbesKt.bin"
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            buildConfigField("Boolean", "IS_DEBUG", "false")
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            buildConfigField("Boolean", "IS_DEBUG", "true")
        }

        all {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("$rootDir/build-logic/src/main/resources/proguard-rules.pro"),
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}
