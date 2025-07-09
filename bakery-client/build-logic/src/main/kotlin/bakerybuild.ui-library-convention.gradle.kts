import bakerybuild.targets.hasTarget

plugins {
    id("bakerybuild.library-convention")
    id("bakerybuild.compose-convention")
}

kotlin {
    sourceSets {
        hasTarget("jvm") {
            jvmMain.dependencies {
                // Compose
                implementation(compose.desktop.common)
            }
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
