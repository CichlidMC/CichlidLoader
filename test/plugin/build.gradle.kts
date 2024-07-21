plugins {
    id("java")
}

base.archivesName = "CichlidTestPlugin"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":plugin-api"))
}

tasks.named("jar", Jar::class).configure {
    archiveExtension = "clp"
}
