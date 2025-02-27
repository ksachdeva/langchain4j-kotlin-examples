plugins {
    kotlin("jvm") version libs.versions.kotlin
    application
    alias(libs.plugins.ktfmt)
}

// group = "ksachdeva.github.io"
// version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(libs.bundles.tinylog)
    implementation(libs.bundles.openai)
}

ktfmt { kotlinLangStyle() }