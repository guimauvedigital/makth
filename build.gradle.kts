plugins {
    kotlin("multiplatform") version "2.1.10"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.google.devtools.ksp") version "2.1.10-1.0.30"
    id("com.vanniktech.maven.publish") version "0.30.0"
    id("dev.petuska.npm.publish") version "3.4.1"
    id("me.nathanfallet.kotlinjsfix") version "1.0.1"
}

//kotlinjsfix { flattenCjsExports = true }

group = "dev.makth"
version = "1.3.1"

repositories {
    mavenCentral()
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    pom {
        name.set("makth")
        description.set("A Kotlin library for algebra")
        url.set("https://github.com/guimauvedigital/makth")

        licenses {
            license {
                name.set("GPL-3.0")
                url.set("https://opensource.org/licenses/GPL-3.0")
            }
        }
        developers {
            developer {
                id.set("NathanFallet")
                name.set("Nathan Fallet")
                email.set("contact@nathanfallet.me")
                url.set("https://www.nathanfallet.me")
            }
        }
        scm {
            url.set("https://github.com/guimauvedigital/makth.git")
        }
    }
}

kotlin {
    // Tiers are in accordance with <https://kotlinlang.org/docs/native-target-support.html>
    // Tier 1
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    // Tier 2
    linuxX64()
    linuxArm64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()
    iosArm64()

    // Tier 3
    mingwX64()
    watchosDeviceArm64()

    // jvm & js
    jvmToolchain(21)
    jvm {
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        useEsModules()
        generateTypeScriptDefinitions()
        binaries.library()
        nodejs()
        browser()
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        val commonMain by getting {
            dependencies {
                api("dev.kaccelero:core:0.5.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

npmPublish {
    readme.set(file("README.md"))
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
        }
    }
    packages {
        named("js") {
            dependencies {
                normal("@kaccelero/core", "0.5.1")
            }
        }
    }
}
