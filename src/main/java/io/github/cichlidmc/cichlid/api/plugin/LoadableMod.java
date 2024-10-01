package io.github.cichlidmc.cichlid.api.plugin;

import java.lang.instrument.Instrumentation;
import java.util.Objects;
import java.util.jar.JarFile;

import io.github.cichlidmc.cichlid.api.Mod;

/**
 * A mod ready for loading by Cichlid.
 */
public final class LoadableMod {
	public final Mod mod;
	public final String source;
	public final JarFile jar;

	/**
	 * @param source description of where this mod comes from, likely a file path
	 * @param jar All mods must end as a jar file for loading.
	 *               This could be the mod file directly, or it could be generated from another format.
	 */
	public LoadableMod(Mod mod, String source, JarFile jar) {
		this.mod = mod;
		this.source = source;
		this.jar = jar;
	}

	public void load(Instrumentation instrumentation) {
		instrumentation.appendToSystemClassLoaderSearch(this.jar);
	}
}
