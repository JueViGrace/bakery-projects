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

            // Cart
            implementation(projects.modules.cart)

            // Dashboard
            implementation(projects.modules.dashboard)

            // Notifications
            implementation(projects.modules.notification)

            // Order
            implementation(projects.modules.order)

            // Product
            implementation(projects.modules.product)

            // Profile
            implementation(projects.modules.profile)

            // User
            implementation(projects.modules.user)
        }
    }
}

android {
    namespace = "com.bakery.home"
}
