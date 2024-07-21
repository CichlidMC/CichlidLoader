plugins {
    id("java")
    id("application")
}

base.archivesName = "CichlidTestApp"
group = "io.github.cichlidmc"
version = properties["version"]!!

repositories {
    mavenCentral()
}

val agent: Configuration by configurations.creating { isTransitive = false }
val plugin: Configuration by configurations.creating { isTransitive = false }
val mod: Configuration by configurations.creating { isTransitive = false }

dependencies {
    agent(implementation(project(":", configuration = "fat"))!!)
    plugin(implementation(project(":test-plugin"))!!)
    mod(implementation(project(":test-mod"))!!)
}

tasks.register("setupAgents", Copy::class) {
    from(agent)
    into("run/agents")
}

tasks.register("setupPlugins", Copy::class) {
    from(plugin)
    into("run/cichlid/plugins")
}

tasks.register("setupMods", Copy::class) {
    from(mod)
    into("run/cichlid/mods")
}

tasks.named("run", JavaExec::class) {
    // depend on 3 setup tasks
    dependsOn("setupAgents", "setupPlugins", "setupMods")

    // add agents to jvm args
    agent.files.forEach {
        val arg = "-javaagent:\"agents/${it.name}\""
        jvmArgs(arg)
    }

    workingDir = file("run")
}

application {
    mainClass = "io.github.cichlidmc.test_app.Main"
}
