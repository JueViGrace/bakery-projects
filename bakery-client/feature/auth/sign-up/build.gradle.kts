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

            // Types
            implementation(projects.feature.auth.typesAuth)
        }
    }
}

android {
    namespace = "com.bakery.auth.signup"
}
