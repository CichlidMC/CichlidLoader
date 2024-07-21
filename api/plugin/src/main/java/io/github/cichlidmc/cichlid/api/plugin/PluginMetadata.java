package io.github.cichlidmc.cichlid.api.plugin;

import io.github.cichlidmc.cichlid.api.Metadata;

public interface PluginMetadata extends Metadata {
	/**
	 * Fully qualified name of this plugin's main class.
	 */
	String pluginClass();
}
