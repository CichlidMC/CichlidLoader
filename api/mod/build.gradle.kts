plugins {
    id("java-library")
}

base.archivesName = "CichlidModApi"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    api(project(":shared-api"))
}
