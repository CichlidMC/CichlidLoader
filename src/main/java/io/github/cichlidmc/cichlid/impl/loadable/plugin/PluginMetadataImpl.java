package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import io.github.cichlidmc.cichlid.impl.loadable.MetadataImpl;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.tinyjson.TinyJson;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

import java.nio.file.Path;

public class PluginMetadataImpl extends MetadataImpl implements PluginMetadata {
	private final String pluginClass;

	public PluginMetadataImpl(String id, String name, String version, String description, Credits credits, String pluginClass) {
		super(id, name, version, description, credits);
		this.pluginClass = pluginClass;
	}

	public PluginMetadataImpl(Metadata metadata, String pluginClass) {
		this(metadata.id(), metadata.name(), metadata.version(), metadata.description(), metadata.credits(), pluginClass);
	}

	@Override
	public String pluginClass() {
		return this.pluginClass;
	}

	public static PluginMetadata fromJsonFile(Path file) {
		JsonObject json = TinyJson.parseOrThrow(file).asObject();

		Metadata metadata = MetadataImpl.fromJson(json);
		String pluginClass = json.get("plugin_class").asString().value();

		return new PluginMetadataImpl(metadata, pluginClass);
	}
}
