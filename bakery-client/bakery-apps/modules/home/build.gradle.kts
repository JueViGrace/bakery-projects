plugins {
    id("bakerybuild.feature-library")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()
}

android {
    namespace = "com.bakery.home"
}
