package io.github.cichlidmc.cichlid.impl.loadable.mod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid.api.Entrypoints;
import io.github.cichlidmc.cichlid.api.ModMetadata;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.impl.loadable.MetadataImpl;

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

	public static ModMetadata fromJson(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonObject json))
			throw new JsonSyntaxException("Not an object");

		Metadata metadata = MetadataImpl.fromJson(json);
		Entrypoints entrypoints = EntrypointsImpl.fromJson(json.get("entrypoints"));

		return new ModMetadataImpl(metadata, entrypoints);
	}

	public static ModMetadata fromJson(Path file) {
		try (InputStream stream = Files.newInputStream(file)) {
			JsonElement json = JsonParser.parseReader(new InputStreamReader(stream));
			return fromJson(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static ModMetadata fromJson(InputStream stream) {
		return fromJson(JsonParser.parseReader(new InputStreamReader(stream)));
	}
}
