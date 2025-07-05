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
            api(project(":core:database"))

            // Network
            api(project(":core:network"))

            // Resources
            api(project(":core:resources"))

            // Types
            api(project(":core:types"))

            // UI
            api(project(":core:ui"))

            // Util
            api(project(":core:util"))

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
