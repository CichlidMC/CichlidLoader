package io.github.cichlidmc.cichlid.api.mod;

import java.util.Optional;
import java.util.Set;

import io.github.cichlidmc.cichlid.api.CichlidInternals;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.api.Mod;

/**
 * Main loader API usable by mods.
 */
public final class CichlidLoader {
	/**
	 * Set of all loaded mods. This will never change.
	 */
	public static Set<Mod> mods() {
		return CichlidInternals.INSTANCE.mods();
	}

	/**
	 * @return true if a mod with the given ID is loaded.
	 */
	public static boolean isModLoaded(String id) {
		return CichlidInternals.INSTANCE.isModLoaded(id);
	}

	/**
	 * get the Mod associated wth the given ID, or empty if it's not loaded.
	 */
	public static Optional<Mod> getMod(String id) {
		return CichlidInternals.INSTANCE.getMod(id);
	}

	/**
	 * Set of metadata for all loaded plugins.
	 */
	public static Set<Metadata> plugins() {
		return CichlidInternals.INSTANCE.plugins();
	}

	/**
	 * Get the metadata for the loaded plugin with the given ID, if present.
	 */
	public static Optional<Metadata> getPlugin(String id) {
		return CichlidInternals.INSTANCE.getPlugin(id);
	}

	/**
	 * Metadata for the currently loaded version of Cichlid.
	 */
	public static Metadata metadata() {
		return CichlidInternals.INSTANCE.metadata();
	}
}
