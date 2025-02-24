plugins {
    kotlin("jvm") version "2.1.10"    
    application
    id("com.ncorti.ktfmt.gradle") version("0.18.0") 
}

repositories {
    mavenCentral()   
    gradlePluginPortal()
    google()
}

val langchain4jVersion = "1.0.0-beta1"

dependencies {    

    implementation("org.tinylog:tinylog-impl:2.6.2")
    implementation("org.tinylog:slf4j-tinylog:2.6.2")
    

    implementation("dev.langchain4j:langchain4j:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-open-ai:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-embeddings-bge-small-en-v15-q:$langchain4jVersion")
}

application {    
    if(hasProperty("mainClass")) {
        mainClass.set("${property("mainClass")}Kt")
    }    
}

ktfmt { kotlinLangStyle() }

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}