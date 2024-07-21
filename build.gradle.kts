plugins {
    id("com.github.johnrengelman.shadow") version("8.1.1")
    id("java-library")
}

base.archivesName = "Cichlid"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

val fat: Configuration by configurations.creating {
    isCanBeConsumed = true
}

dependencies {
    implementation(project(":mod-api"))
    implementation(project(":plugin-api"))
    implementation("org.ow2.asm:asm-tree:9.7")
    compileOnly("org.apache.logging.log4j:log4j-api:2.23.1")
}

tasks.named("processResources", ProcessResources::class) {
    val properties = mapOf(
        "version" to version
    )

    properties.forEach(inputs::property)

    filesMatching("cichlid.json") {
        expand(properties)
    }
}

tasks.named("jar", Jar::class).configure {
    archiveClassifier = "slim"
}

tasks.named("shadowJar", com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    archiveClassifier = ""
    manifest.attributes["Premain-Class"] = "io.github.cichlidmc.cichlid.impl.CichlidAgent"

    relocate("com.google", "io.github.cichlidmc.cichlid.impl.shadow.google")
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}

artifacts {
    add("fat", tasks.named("shadowJar"))
}
