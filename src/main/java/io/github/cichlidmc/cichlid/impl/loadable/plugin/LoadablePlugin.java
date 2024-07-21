package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.jar.JarFile;

import io.github.cichlidmc.cichlid.api.plugin.CichlidPlugin;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;

public interface LoadablePlugin {
	PluginMetadata metadata();

	PluginHolder load(Instrumentation instrumentation);

	record BuiltIn(PluginHolder holder) implements LoadablePlugin {
		@Override
		public PluginMetadata metadata() {
			return this.holder.metadata();
		}

		@Override
		public PluginHolder load(Instrumentation instrumentation) {
			return this.holder;
		}
	}

	record Jar(PluginMetadata metadata, JarFile jar) implements LoadablePlugin {
		@Override
		public PluginMetadata metadata() {
			return this.metadata;
		}

		@Override
		public PluginHolder load(Instrumentation instrumentation) {
			instrumentation.appendToSystemClassLoaderSearch(this.jar);

			try {
				Class<?> pluginClass = Class.forName(metadata.pluginClass());
				Constructor<?> constructor = pluginClass.getConstructor();
				CichlidPlugin plugin = (CichlidPlugin) constructor.newInstance();
				return new PluginHolder(plugin, this.metadata);
			} catch (ClassNotFoundException e) {
				throw new InvalidPluginException(metadata, "Class " + metadata.pluginClass() + " does not exist", e);
			} catch (NoSuchMethodException e) {
				throw new InvalidPluginException(metadata, "Plugin does not have a valid constructor", e);
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
				throw new InvalidPluginException(metadata, "Error creating plugin instance", e);
			}
		}
	}
}
