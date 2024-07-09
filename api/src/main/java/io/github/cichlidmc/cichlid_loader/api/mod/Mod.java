package io.github.cichlidmc.cichlid_loader.api.mod;

import java.nio.file.Path;
import java.util.Optional;

public interface Mod {
	ModMetadata metadata();

	/**
	 * Root path to resources provided by this mod. May be empty if none exist.
	 * For a jar file, this would be the root.
	 * This may be expensive to call, ex. opening a new FileSystem on a jar file.
	 */
	Optional<Path> resources();
}
