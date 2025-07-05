plugins {
    id("bakerybuild.library-convention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network)
        }
    }
}

android {
    namespace = "com.bakery.user.network"
}
