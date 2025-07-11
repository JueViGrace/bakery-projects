package bakerybuild.targets

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.hasTarget(name: String, configure: KotlinMultiplatformExtension.() -> Unit) {
    if (targets.findByName(name) != null) {
        configure()
    }
}
