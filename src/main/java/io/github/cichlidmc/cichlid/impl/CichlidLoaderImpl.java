package io.github.cichlidmc.cichlid.impl;

import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.github.cichlidmc.cichlid.api.plugin.LoadableMod;
import io.github.cichlidmc.cichlid.impl.loadable.mod.ModDiscovery;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.api.mod.entrypoint.EntrypointHelper;
import io.github.cichlidmc.cichlid.api.mod.entrypoint.PreLaunchEntrypoint;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.LoadablePlugin;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.PluginHolder;
import io.github.cichlidmc.cichlid.impl.logging.CichlidLogger;
import io.github.cichlidmc.cichlid.api.Mod;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.PluginDiscovery;
import io.github.cichlidmc.cichlid.impl.util.IdentifiedSet;
import io.github.cichlidmc.tinyjson.TinyJson;

public class CichlidLoaderImpl {
	private static final CichlidLogger logger = CichlidLogger.get("Cichlid");

	private static boolean initialized;
	private static Metadata metadata;
	private static IdentifiedSet<Metadata> plugins;
	private static IdentifiedSet<Mod> mods;
	private static InstanceMeta instanceMeta;

	public static Set<Mod> mods() {
		Objects.requireNonNull(mods, "Mods are not loaded yet");
		return mods.set();
	}

	public static boolean isModLoaded(String id) {
		Objects.requireNonNull(mods, "Mods are not loaded yet");
		return mods.contains(id);
	}

	public static Optional<Mod> getMod(String id) {
		Objects.requireNonNull(mods, "Mods are not loaded yet");
		return Optional.ofNullable(mods.get(id));
	}

	public static Set<Metadata> plugins() {
		Objects.requireNonNull(plugins, "Plugins are not loaded yet");
		return plugins.set();
	}

	public static Optional<Metadata> getPlugin(String id) {
		Objects.requireNonNull(plugins, "Plugins are not loaded yet");
		return Optional.ofNullable(plugins.get(id));
	}

	public static String environment() {
		return Objects.requireNonNull(instanceMeta, "Instance meta is not loaded yet").environment;
	}

	public static Metadata metadata() {
		return Objects.requireNonNull(metadata, "Cichlid metadata is not loaded yet");
	}

	public static void load(Instrumentation instrumentation) {
		if (initialized) {
			throw new IllegalStateException("Cichlid is already initialized");
		}

		logger.info("Cichlid initializing!");
		metadata = loadMetadata();
		logger.info("Cichlid version: " + metadata.version());

		instanceMeta = InstanceMeta.read();
		instrumentation.addTransformer(CichlidTransformerManager.INSTANCE);

		// find plugins to load
		List<LoadablePlugin> pluginList = PluginDiscovery.find();
		logLoadedList(pluginList, "plugin", LoadablePlugin::metadata);
		plugins = IdentifiedSet.from(pluginList, LoadablePlugin::metadata, Metadata::id);
		// do the loading
		List<PluginHolder> loadedPlugins = pluginList.stream()
				.map(loadable -> loadable.load(instrumentation))
				.collect(Collectors.toList());

		// find mods to load
		List<LoadableMod> modList = ModDiscovery.find(loadedPlugins);
		logLoadedList(modList, "mod", mod -> mod.mod.metadata());
		mods = IdentifiedSet.from(modList, mod -> mod.mod, mod -> mod.metadata().id());
		modList.forEach(mod -> mod.load(instrumentation));

		initialized = true;
		logger.info("Cichlid initialized!");

		logger.info("Invoking pre-launch...");
		EntrypointHelper.invoke(PreLaunchEntrypoint.class, PreLaunchEntrypoint.KEY, PreLaunchEntrypoint::preLaunch);
		logger.info("Pre-launch done. Continuing...");
	}

	private static <T> void logLoadedList(List<T> list, String name, Function<T, Metadata> metadata) {
		if (list.isEmpty()) {
			logger.info("Loading 0 " + name + "s.");
		} else if (list.size() == 1) {
			String id = metadata.apply(list.get(0)).id();
			logger.info("Loading 1 " + name + ": " + id);
		} else {
			logger.info("Loading " + list.size() + ' ' + name + "s:");
			for (T t : list) {
				String id = metadata.apply(t).id();
				logger.info("\t- " + id);
			}
		}
	}

	private static Metadata loadMetadata() {
		InputStream stream = CichlidLoaderImpl.class.getClassLoader().getResourceAsStream("cichlid.json");
		if (stream == null) {
			throw new IllegalStateException("Cichlid metadata is missing");
		}

		String version = TinyJson.parseOrThrow(stream).asObject().get("version").asString().value();
		return new CichlidMetadata(version);
	}
}
