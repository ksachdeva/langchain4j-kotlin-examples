plugins {
    kotlin("jvm") version "2.1.10"
    application
}

// group = "ksachdeva.github.io"
// version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()      
}

val langchain4jVersion = "1.0.0-beta1"

dependencies {    

    implementation("org.tinylog:tinylog-impl:2.6.2")
    implementation("org.tinylog:slf4j-tinylog:2.6.2")
    

    implementation("dev.langchain4j:langchain4j:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-open-ai:$langchain4jVersion")
}

application {    
    if(hasProperty("mainClass")) {
        mainClass.set("${property("mainClass")}Kt")
    }    
}