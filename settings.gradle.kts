pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Plugins
            version("kotlin", "2.1.21")
            plugin("multiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("kover", "org.jetbrains.kotlinx.kover").version("0.8.3")
            plugin("dokka", "org.jetbrains.dokka").version("2.0.0")
            plugin("ksp", "com.google.devtools.ksp").version("2.1.21-2.0.2")
            plugin("maven", "com.vanniktech.maven.publish").version("0.30.0")
            plugin("npm", "dev.petuska.npm.publish").version("3.4.1")

            // Kaccelero
            version("kaccelero", "0.6.0")
            library("kaccelero-core", "dev.kaccelero", "core").versionRef("kaccelero")
        }
    }
}

rootProject.name = "makth"
include(":core")
