plugins {
    id("bakerybuild.ui-library-convention")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()
}

android {
    namespace = "com.bakery.resources"
}

compose.resources {
    generateResClass = always
    publicResClass = true
}
