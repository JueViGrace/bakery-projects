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

            // Forgot
            implementation(projects.feature.auth.forgot)

            // Onboarding
            implementation(projects.feature.auth.onboarding)

            // Sign in
            implementation(projects.feature.auth.signIn)

            // Sign up
            implementation(projects.feature.auth.signUp)

            // Types
            api(projects.feature.auth.typesAuth)
        }
    }
}

android {
    namespace = "com.bakery.auth"
}
