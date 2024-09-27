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
    implementation(project(":"))
}

tasks.named("jar", Jar::class).configure {
    archiveExtension = "cld"
}
