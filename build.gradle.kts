import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("org.jetbrains.compose.hot-reload") version "1.2.0-alpha02"
}

group = "io.github.t45k"
version = "1.0-SNAPSHOT"

sourceSets.main {
    kotlin.srcDirs("src/main/kotlin", "src/main/sample")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(21)
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
    }
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.androidx.navigation3:navigation3-ui:1.1.1")

    implementation("dev.snipme:highlights:1.1.0")
    implementation("org.apache.pdfbox:pdfbox:2.0.32")
    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "slidekt"
            packageVersion = "1.0.0"
        }
        mainClass = "MainKt"
    }
}
