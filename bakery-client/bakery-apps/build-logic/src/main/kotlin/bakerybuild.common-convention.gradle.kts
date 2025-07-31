
import bakerybuild.internal.libs
import bakerybuild.targets.hasTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_2_2
        languageVersion = KotlinVersion.KOTLIN_2_2
        progressiveMode = languageVersion.map { it >= KotlinVersion.DEFAULT }
        freeCompilerArgs.addAll("-Xexpect-actual-classes")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.activity.compose)

            api(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)

            api(libs.kotlinx.datetime)

            implementation(libs.kotlin.reflect)
        }

        iosMain.dependencies {}

        hasTarget("jvm") {
            jvmMain.dependencies {
                api(libs.kotlinx.coroutines.swing)

                api(libs.logback.classic)
            }
        }

        hasTarget("js") {
            jsMain.dependencies {
                api(libs.kotlinx.coroutines.core.js)
            }
        }
    }
}
