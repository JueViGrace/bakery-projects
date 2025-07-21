plugins {
    id("bakerybuild.feature-library")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            /*
             * Projects
             */

            // Database: auth
            implementation(projects.modules.auth.databaseAuth)

            // Network: auth
            implementation(projects.modules.auth.networkAuth)

            // User
            implementation(projects.modules.user)
        }
    }
}

android {
    namespace = "com.bakery.profile"
}
