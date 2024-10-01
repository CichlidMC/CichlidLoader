package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import io.github.cichlidmc.cichlid.api.plugin.CichlidPlugin;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;

import java.util.Objects;

public final class PluginHolder {
	public final CichlidPlugin plugin;
	public final PluginMetadata metadata;

	public PluginHolder(CichlidPlugin plugin, PluginMetadata metadata) {
		this.plugin = plugin;
		this.metadata = metadata;
	}
}
