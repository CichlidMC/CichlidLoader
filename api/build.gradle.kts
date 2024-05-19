plugins {
    id("java-library")
}

base.archivesName = "Cichlid"
group = "io.github.tropheusj"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
}

tasks.named("jar", Jar::class).configure {
    archiveClassifier = "api"
}
