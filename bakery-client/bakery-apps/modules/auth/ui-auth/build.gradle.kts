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
        commonMain.dependencies {
           /*
            * Projects
            */

            implementation(projects.modules.ui)
        }
    }
}

android {
    namespace = "com.bakery.ui"
}
