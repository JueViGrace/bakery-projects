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
        androidMain.dependencies {
            // Ktor: engine
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            // Projects
            api(projects.core.util)

            // Dependencies

            // Konnection
            implementation(libs.konnection)

            // Ktor
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlin.reflect)

            // Serialization
            api(libs.kotlinx.serialization.json)
        }

        iosMain.dependencies {
            // Ktor: engine
            implementation(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            // Ktor: engine
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.bakery.network"
}
