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

            implementation(projects.modules.core.database)
        }
    }
}

android {
    namespace = "com.bakery.auth.database"
}
