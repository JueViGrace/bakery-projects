plugins {
    id("bakerybuild.ui-library-convention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        androidMain.dependencies {
            // Camera
            implementation(libs.androidx.camera.core)
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.video)
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.camera.extensions)
            implementation(libs.androidx.camera.compose)
        }

        commonMain.dependencies {
            // Resources
            api(projects.core.resources)

            // Types
            api(projects.core.types)

            // Util
            api(projects.core.util)

            // Dependencies

            // Serialization
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    namespace = "com.bakery.ui"
}
