plugins {
    id("com.github.johnrengelman.shadow") version("8.1.1")
    id("java-library")
}

group = "io.github.tropheusj"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
}

tasks.named("jar", Jar::class).configure {
    archiveClassifier = "slim"
}

tasks.named("shadowJar", Jar::class) {
    archiveClassifier = ""
    manifest.attributes["Premain-Class"] = "io.github.tropheusj.cichlid.impl.CichlidAgent"
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
