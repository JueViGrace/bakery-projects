plugins {
    id("bakerybuild.library-convention")
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        androidMain.dependencies {
            // Sqldelight
            implementation(libs.sqldelight.android.driver)
        }

        commonMain.dependencies {
            /*
             * Projects
             */

            // Util
            implementation(projects.modules.core.util)

            /*
             * Dependencies
             */

            // Sqldelight
            api(libs.sqldelight.async.extensions)
            api(libs.sqldelight.coroutines.extensions)
        }

        iosMain.dependencies {
            // Sqldelight
            implementation(libs.sqldelight.native.driver)
        }

        jvmMain.dependencies {
            // Sqldelight
            implementation(libs.sqldelight.sqlite.driver)

            // Sqlite
            api(libs.sqlite)
        }
    }
}

android {
    namespace = "com.bakery.database"
}

sqldelight {
    databases {
        create("BakeryDB") {
            packageName.set("com.bakery.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}
