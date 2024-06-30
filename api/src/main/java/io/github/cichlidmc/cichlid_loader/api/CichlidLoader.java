package io.github.cichlidmc.cichlid_loader.api;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;

import io.github.cichlidmc.cichlid_loader.api.mod.Mod;

public interface CichlidLoader {
	CichlidLoader INSTANCE = CichlidUtils.make(() -> {
		try {
			Class<?> implClass = Class.forName("io.github.cichlidmc.cichlid_loader.impl.CichlidLoaderImpl");
			Constructor<?> constructor = implClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			Object impl = constructor.newInstance();
			return (CichlidLoader) impl;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing CichlidLoader", e);
		}
	});

	/**
	 * Set of all loaded mods. This will never change.
	 */
	Set<Mod> allMods();

	/**
	 * @return true if a mod with the given ID is loaded.
	 */
	boolean isModLoaded(String id);

	/**
	 * get the Mod associated wth the given ID, or empty if it's not loaded.
	 */
	Optional<Mod> getMod(String id);
}
