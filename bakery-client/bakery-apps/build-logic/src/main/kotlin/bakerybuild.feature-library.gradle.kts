import bakerybuild.internal.libs

plugins {
    id("bakerybuild.ui-library-convention")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
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
