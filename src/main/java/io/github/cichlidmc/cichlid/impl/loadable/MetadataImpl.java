package io.github.cichlidmc.cichlid.impl.loadable;

import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

public class MetadataImpl implements Metadata {
	private final String id;
	private final String name;
	private final String version;
	private final String description;
	private final Credits credits;

	public MetadataImpl(String id, String name, String version, String description, Credits credits) {
		if (!ID_REGEX.matcher(id).matches()) {
			throw new IllegalArgumentException("Invalid ID: '" + id + "', must match " + ID_REGEX.pattern());
		}
		this.id = id;
		this.name = name;
		this.version = version;
		this.description = description;
		this.credits = credits;
	}

	@Override
	public String id() {
		return this.id;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String version() {
		return this.version;
	}

	@Override
	public String description() {
		return this.description;
	}

	@Override
	public Credits credits() {
		return this.credits;
	}

	public static Metadata fromJson(JsonObject json) {
		String id = json.get("id").asString().value();
		String name = json.get("name").asString().value();
		String version = json.get("version").asString().value();
		String description = json.get("description").asString().value();
		Credits credits = CreditsImpl.fromJson(json.getNullable("credits"));
		return new MetadataImpl(id, name, version, description, credits);
	}
}
