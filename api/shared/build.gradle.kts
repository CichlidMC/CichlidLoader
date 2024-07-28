plugins {
    id("java-library")
}

base.archivesName = "CichlidSharedApi"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi("org.jetbrains:annotations:24.1.0")
}
