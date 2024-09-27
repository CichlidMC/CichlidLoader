package io.github.cichlidmc.cichlid.api.plugin;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

import io.github.cichlidmc.cichlid.api.Mod;

/**
 * A mod ready for loading by Cichlid.
 * @param source description of where this mod comes from, likely a file path
 * @param jar All mods must end as a jar file for loading. This could be the mod file directly, or it could be generated from another format.
 */
public record LoadableMod(Mod mod, String source, JarFile jar) {
	public void load(Instrumentation instrumentation) {
		instrumentation.appendToSystemClassLoaderSearch(this.jar);
	}
}
