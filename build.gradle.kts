import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.compose") version "1.6.11"
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "io.github.t45k"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
    implementation("dev.snipme:highlights:0.9.0")

    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "slidekt"
            packageVersion = "1.0.0"
        }
    }
}

kotlin.compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")
