plugins {
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.maven) apply false
    alias(libs.plugins.dokka)
}

allprojects {
    group = "dev.makth"
    version = "1.3.2"

    repositories {
        mavenCentral()
    }
}
