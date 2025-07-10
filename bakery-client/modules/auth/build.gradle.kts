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
            api(projects.modules.auth.databaseAuth)

            // Forgot
            implementation(projects.modules.auth.features.forgot)

            // Network
            api(projects.modules.auth.networkAuth)

            // Onboarding
            implementation(projects.modules.auth.features.onboarding)

            // Sign in
            implementation(projects.modules.auth.features.signIn)

            // Sign up
            implementation(projects.modules.auth.features.signUp)
        }
    }
}

android {
    namespace = "com.bakery.auth"
}
