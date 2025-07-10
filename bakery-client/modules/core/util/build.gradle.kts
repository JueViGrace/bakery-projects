plugins {
    id("bakerybuild.library-convention")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    js(IR) {
        nodejs {}
        binaries.library()
    }
}

android {
    namespace = "com.bakery.util"
}
