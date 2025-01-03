import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    kotlin("jvm") version "2.1.0-firework.31"
    kotlin("plugin.compose") version "2.1.0-firework.31"
    id("org.jetbrains.compose")
    id("org.jetbrains.compose-hot-reload") version "1.0.0-dev.31.8"
}

group = "io.github.t45k"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://packages.jetbrains.team/maven/p/firework/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")

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
        mainClass = "io.github.t45k.slidekt.MainKt"
    }
}

tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("io.github.t45k.slidekt.MainKt")
}

kotlin.compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
