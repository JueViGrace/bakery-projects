enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "bakery-client"

include(
    ":apps:composeApp",
)

// core library
include(
    ":modules:core",
    ":modules:core:network",
    ":modules:core:database",
    ":modules:core:types",
    ":modules:core:util",
)

// modules library
include(
    ":modules:ui",
    ":modules:resources",
)

// library

// auth library
include(
    ":modules:auth",
    ":modules:auth:database-auth",
    ":modules:auth:features:forgot",
    ":modules:auth:features:onboarding",
    ":modules:auth:features:sign-in",
    ":modules:auth:features:sign-up",
    ":modules:auth:network-auth",
)

// dashboard library
include(
    ":modules:dashboard",
)

// home library
include(
    ":modules:home",
)

// order library
include(
    ":modules:order",
    ":modules:order:network-order",
)

// product library
include(
    ":modules:product",
    ":modules:product:network-product",
)

// user library
include(
    ":modules:user",
    ":modules:user:network-user",
)
