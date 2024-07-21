package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;

public class InvalidPluginException extends RuntimeException {
	public InvalidPluginException(String filename, String message) {
		super("Invalid plugin '" + filename + "': " + message);
	}

	public InvalidPluginException(String filename, String message, Throwable cause) {
		super("Invalid plugin '" + filename + "': " + message, cause);
	}

	public InvalidPluginException(PluginMetadata metadata, String message, Throwable cause) {
		this(metadata.blame(), message, cause);
	}
}
