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
            implementation(projects.feature.user.networkUser)
        }
    }
}

android {
    namespace = "com.bakery.user"
}
