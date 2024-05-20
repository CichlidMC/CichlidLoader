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
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.named("jar", Jar::class).configure {
    archiveClassifier = "slim"
}

tasks.named("shadowJar", com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    archiveClassifier = ""
    manifest.attributes["Premain-Class"] = "io.github.tropheusj.cichlid.impl.CichlidAgent"

    relocate("com.google", "io.github.tropheusj.cichlid.impl.shadow.google")
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
