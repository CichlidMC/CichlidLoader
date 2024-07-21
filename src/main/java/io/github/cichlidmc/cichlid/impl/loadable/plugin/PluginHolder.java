package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import io.github.cichlidmc.cichlid.api.plugin.CichlidPlugin;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;

public record PluginHolder(CichlidPlugin plugin, PluginMetadata metadata) {
}
