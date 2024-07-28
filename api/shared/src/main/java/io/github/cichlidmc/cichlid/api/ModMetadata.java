package io.github.cichlidmc.cichlid.api;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Mod-specific metadata.
 */
public interface ModMetadata extends Metadata {
	/**
	 * The entrypoints of this mod.
	 * @see Entrypoints
	 */
	Entrypoints entrypoints();

	/**
	 * Try to parse mod metadata from json read from the given stream.
	 */
	static ModMetadata fromJson(InputStream stream) {
		return CichlidInternals.INSTANCE.readModMetadata(stream);
	}

	/**
	 * Try to parse mod metadata from json in the provided file.
	 */
	static ModMetadata fromJson(Path file) {
		return CichlidInternals.INSTANCE.readModMetadata(file);
	}
}
