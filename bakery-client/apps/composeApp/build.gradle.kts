import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("bakerybuild.application-convention")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "BakeryApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            /*
             * Projects
             */

            // Auth
            implementation(projects.modules.auth)

            // Home
            implementation(projects.modules.home)

            // Order
            implementation(projects.modules.order)

            // Product
            implementation(projects.modules.product)

            // User
            implementation(projects.modules.user)
        }

        desktopMain.dependencies {
            // Compose
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)

            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)

            // Logs
            implementation(libs.logback.classic)
        }
    }
}

android {
    namespace = "com.bakery.app"

    defaultConfig {
        applicationId = "com.bakery.app"
        versionCode =
            libs.versions.appVersionCode
                .get()
                .toInt()
        versionName =
            libs.versions.appVersion
                .get()
    }
}

compose {
    desktop {
        application {
            mainClass = "com.bakery.app.Mainkt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "com.bakery.app"
                packageVersion =
                    libs.versions.appVersion
                        .get()
            }
        }
    }
}
