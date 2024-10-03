package io.github.cichlidmc.cichlid.impl.loadable.mod;

import io.github.cichlidmc.cichlid.api.Entrypoints;
import io.github.cichlidmc.cichlid.api.ModMetadata;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.impl.loadable.MetadataImpl;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

public class ModMetadataImpl extends MetadataImpl implements ModMetadata {
	private final Entrypoints entrypoints;

	public ModMetadataImpl(String id, String name, String version, String description, Credits credits, Entrypoints entrypoints) {
		super(id, name, version, description, credits);
		this.entrypoints = entrypoints;
	}

	public ModMetadataImpl(Metadata metadata, Entrypoints entrypoints) {
		this(metadata.id(), metadata.name(), metadata.version(), metadata.description(), metadata.credits(), entrypoints);
	}

	@Override
	public Entrypoints entrypoints() {
		return this.entrypoints;
	}

	public static ModMetadata fromJson(JsonValue value) {
		JsonObject json = value.asObject();
		Metadata metadata = MetadataImpl.fromJson(json);
		Entrypoints entrypoints = EntrypointsImpl.fromJson(json.getNullable("entrypoints"));

		return new ModMetadataImpl(metadata, entrypoints);
	}
}
