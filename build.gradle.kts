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

val shade: Configuration by configurations.creating

val fat: Configuration by configurations.creating {
    isCanBeConsumed = true
}

dependencies {
    api(project(":mod-api"))
    api(project(":plugin-api"))
    shade(api("com.google.code.gson:gson:2.11.0")!!)
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
    configurations = listOf(shade)
    archiveClassifier = ""
    manifest.attributes["Premain-Class"] = "io.github.cichlidmc.cichlid.impl.CichlidAgent"

    exclude("META-INF/**")

    relocate("com.google", "io.github.cichlidmc.cichlid.impl.shadow.google")

    minimize {
        // api dependencies are automatically excluded
        include(dependency("com.google.code.gson:gson:.*"))
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}

artifacts {
    add("fat", tasks.named("shadowJar"))
}
