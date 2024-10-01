package io.github.cichlidmc.cichlid.api;

import io.github.cichlidmc.cichlid.impl.loadable.mod.ModMetadataImpl;

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
		return ModMetadataImpl.fromJson(stream);
	}

	/**
	 * Try to parse mod metadata from json in the provided file.
	 */
	static ModMetadata fromJson(Path file) {
		return ModMetadataImpl.fromJson(file);
	}
}
