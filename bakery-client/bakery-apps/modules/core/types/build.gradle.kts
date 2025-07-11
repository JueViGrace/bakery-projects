plugins {
    id("bakerybuild.library-convention")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    js(IR) {
        nodejs {}
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            /*
             * Projects
             */

            // Util
            implementation(projects.modules.core.util)
        }
    }
}

android {
    namespace = "com.bakery.types"
}
