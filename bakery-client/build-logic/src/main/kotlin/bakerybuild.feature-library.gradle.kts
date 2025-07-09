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

            // Database
            implementation(project(":core:database"))

            // Network
            implementation(project(":core:network"))

            // Resources
            implementation(project(":core:resources"))

            // Types
            implementation(project(":core:types"))

            // UI
            implementation(project(":core:ui"))

            // Util
            implementation(project(":core:util"))

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
