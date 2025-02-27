plugins {
    kotlin("jvm") version libs.versions.kotlin
    application
    alias(libs.plugins.ktfmt)
}

dependencies {
    implementation(libs.bundles.tinylog)
    implementation(libs.bundles.openai)
    implementation(libs.langchain4j.embeddings)
}

ktfmt { kotlinLangStyle() }

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}