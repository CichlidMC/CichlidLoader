package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid.impl.loadable.MetadataImpl;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.impl.util.JsonUtils;

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

	public static PluginMetadata fromJson(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonObject))
			throw new JsonSyntaxException("Not an object");

		JsonObject json = element.getAsJsonObject();
		Metadata metadata = MetadataImpl.fromJson(json);
		String pluginClass = JsonUtils.getString(json, "plugin_class");

		return new PluginMetadataImpl(metadata, pluginClass);
	}
}
