plugins {
    id("bakerybuild.library-convention")
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

            // Network
            api(projects.core.network)

            // Util
            api(projects.core.util)
        }
    }
}

android {
    namespace = "com.bakery.types"
}
