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

            // Database
            api(projects.feature.auth.databaseAuth)

            // Network
            implementation(projects.feature.auth.networkAuth)
        }
    }
}

android {
    namespace = "com.bakery.auth"
}
