package io.github.cichlidmc.cichlid.api;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus;

/**
 * Bridge to Cichlid internals from API. This class is not API, and breakage should be expected.
 * Nearly everything in this class is exposed through real APIs elsewhere.
 */
@ApiStatus.Internal
public interface CichlidInternals {
	CichlidInternals INSTANCE = make(() -> {
		try {
			Class<?> implClass = Class.forName("io.github.cichlidmc.cichlid.impl.util.CichlidInternalsImpl");
			Field field = implClass.getDeclaredField("INSTANCE");
			return (CichlidInternals) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing CichlidInternals", e);
		}
	});

	// loader methods

	Set<Mod> mods();
	boolean isModLoaded(String id);
	Optional<Mod> getMod(String id);
	Set<Metadata> plugins();
	Optional<Metadata> getPlugin(String id);
	Metadata metadata();

	// mod metadata

	ModMetadata readModMetadata(InputStream stream);
	ModMetadata readModMetadata(Path file);

	// static utils

	static <T> T make(Supplier<T> supplier) {
		return supplier.get();
	}
}
