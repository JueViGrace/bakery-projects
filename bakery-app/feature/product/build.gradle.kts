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
            implementation(projects.feature.product.networkProduct)
        }
    }
}

android {
    namespace = "com.bakery.product"
}
