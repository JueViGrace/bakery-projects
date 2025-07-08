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
            implementation(projects.core.database)
            api(projects.feature.auth.databaseAuth)

            // Network
            api(projects.feature.auth.networkAuth)

            // Types
            api(projects.core.types)
        }
    }
}

android {
    namespace = "com.bakery.auth.types"
}
