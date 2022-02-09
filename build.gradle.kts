plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id ("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.lderic"
version = "1.0-SNAPSHOT"
val bundle:Configuration by configurations.creating

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        name = "ktor-eap"
    }
}


tasks {
    jar {
        manifest {
            attributes (Pair("Main-Class", "com.lderic.OrahKt"))
        }
    }
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:1.6.7")
    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("io.ktor:ktor-client-serialization:1.6.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.2")
    implementation("io.ktor:ktor-client-gson:1.6.7")
    implementation("org.json:json:20211205")

}