plugins {
    id("java")
}

base.archivesName = "CichlidTestMod"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":mod-api"))
}

tasks.named("jar", Jar::class).configure {
    archiveExtension = "clm"
}
