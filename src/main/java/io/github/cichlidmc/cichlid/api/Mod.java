package io.github.cichlidmc.cichlid.api;

import java.nio.file.Path;
import java.util.Optional;

/**
 * A mod loaded by Cichlid.
 */
public interface Mod {
	ModMetadata metadata();

	/**
	 * Metadata of the plugin that loaded this mod.
	 */
	Metadata pluginMetadata();

	/**
	 * Root path to resources provided by this mod. May be empty if none exist.
	 * For a jar file, this would be the root.
	 */
	Optional<Path> resources();
}
