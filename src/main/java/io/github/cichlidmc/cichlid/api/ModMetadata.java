package io.github.cichlidmc.cichlid.api;

import io.github.cichlidmc.cichlid.impl.loadable.mod.ModMetadataImpl;
import io.github.cichlidmc.tinyjson.value.JsonValue;

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
	 * Try to parse mod metadata from the given JSON.
	 */
	static ModMetadata fromJson(JsonValue value) {
		return ModMetadataImpl.fromJson(value);
	}
}
