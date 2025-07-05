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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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

rootProject.name = "bakery-app"

include(
    ":app",
)

// core library
include(
    ":core:network",
    ":core:database",
    ":core:resources",
    ":core:types",
    ":core:ui",
    ":core:util",
)

// feature library

// auth library
include(
    ":feature:auth",
    ":feature:auth:database-auth",
    ":feature:auth:network-auth",
)

// home library
include(
    ":feature:home"
)

// order library
include(
    ":feature:order",
    ":feature:order:network-order",
)

// product library
include(
    ":feature:product",
    ":feature:product:network-product",
)

// user library
include(
    ":feature:user",
    ":feature:user:network-user",
)
