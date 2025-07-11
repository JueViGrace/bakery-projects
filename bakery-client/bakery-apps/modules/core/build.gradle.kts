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

            // Database
            api(projects.modules.core.database)

            // Network
            api(projects.modules.core.network)

            // Types
            api(projects.modules.core.types)

            // Util
            api(projects.modules.core.util)
        }
    }
}

android {
    namespace = "com.bakery.core"
}
