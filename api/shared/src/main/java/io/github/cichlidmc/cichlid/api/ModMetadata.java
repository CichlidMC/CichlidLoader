package io.github.cichlidmc.cichlid.api;

import java.nio.file.Path;

import com.google.gson.JsonElement;

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
	 * Try to parse mod metadata from the provided json.
	 */
	static ModMetadata fromJson(JsonElement json) {
		return CichlidInternals.INSTANCE.readModMetadata(json);
	}

	/**
	 * Try to parse mod metadata from json in the provided file.
	 */
	static ModMetadata fromJsonFile(Path file) {
		return CichlidInternals.INSTANCE.readModMetadata(file);
	}
}
