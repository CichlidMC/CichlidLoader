plugins {
    id("java")
}

group = "io.github.tropheusj"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
}

tasks.named("jar", Jar::class).configure {
    manifest.attributes["Premain-Class"] = "io.github.tropheusj.cichlid.impl.CichlidAgent"
}
