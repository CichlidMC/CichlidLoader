rootProject.name = "CichlidLoader"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

file("api").listFiles()!!.forEach {
    include("${it.name}-api")
    project(":${it.name}-api").projectDir = it
}

file("test").listFiles()!!.forEach {
    include("test-${it.name}")
    project(":test-${it.name}").projectDir = it
}
