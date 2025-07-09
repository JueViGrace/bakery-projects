import bakerybuild.internal.libs

plugins {
    id("bakerybuild.common-convention")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose.hot-reload")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.runtimeSaveable)
            implementation(compose.ui)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.ktor3)
            implementation(libs.coil.cache)

            // Lifecycle
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.viewmodel.savedstate)

            // Navigation
            implementation(libs.navigation.compose)
        }
    }
}

compose.resources {
    generateResClass = never
}
