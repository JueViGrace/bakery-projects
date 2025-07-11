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
            implementation(projects.modules.core.network)
        }
    }
}

android {
    namespace = "com.bakery.product.network"
}
