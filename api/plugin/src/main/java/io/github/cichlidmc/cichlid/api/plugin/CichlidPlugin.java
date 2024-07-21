package io.github.cichlidmc.cichlid.api.plugin;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Entrypoint for plugins.
 */
public interface CichlidPlugin {
	/**
	 * Attempt to load the given path as a mod.
	 * This path may be a file or a directory.
	 * If this plugin cannot load the given path, return null.
	 */
	default LoadableMod loadMod(Path path) {
		return null;
	}

	/**
	 * Provide additional mods for Cichlid to load, outside the standard "mods" folder.
	 */
	default void loadAdditionalMods(Consumer<LoadableMod> consumer) {
	}
}
