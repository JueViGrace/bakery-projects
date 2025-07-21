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

            // Network
            implementation(projects.modules.order.networkOrder)

            // Product
            implementation(projects.modules.product)
        }
    }
}

android {
    namespace = "com.bakery.order"
}
