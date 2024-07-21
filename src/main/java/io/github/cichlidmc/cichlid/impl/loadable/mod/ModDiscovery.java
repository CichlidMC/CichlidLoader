package io.github.cichlidmc.cichlid.impl.loadable.mod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import io.github.cichlidmc.cichlid.api.plugin.LoadableMod;
import io.github.cichlidmc.cichlid.impl.CichlidPaths;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.PluginHolder;
import io.github.cichlidmc.cichlid.impl.util.FileUtils;

public class ModDiscovery {
	public static List<LoadableMod> find(List<PluginHolder> plugins) {
		List<LoadableMod> mods = new ArrayList<>();
		// first load mods from mods dir
		try {
			Files.createDirectories(CichlidPaths.MODS);
			searchDir(plugins, mods, CichlidPaths.MODS.toAbsolutePath()); // make absolute for clearer errors
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// load additional mods from plugins
		for (PluginHolder holder : plugins) {
			try {
				holder.plugin().loadAdditionalMods(mods::add);
			} catch (Throwable t) {
				throw ModLoadingException.errorAdding(holder, t);
			}
		}
		// mod load order is undefined - shuffle it intentionally
		Collections.shuffle(mods);
		return mods;
	}

	private static void searchDir(List<PluginHolder> plugins, List<LoadableMod> mods, Path dir) throws IOException {
		try (Stream<Path> stream = Files.list(dir)) {
			for (Iterator<Path> itr = stream.iterator(); itr.hasNext();) {
				Path path = itr.next();
				if (FileUtils.isDisabled(path))
					continue;

				if (!Files.isDirectory(path)) {
					loadMod(path, plugins, mods);
				} else {
					DirectoryBehavior behavior = DirectoryBehavior.of(path);
					if (behavior == DirectoryBehavior.SCAN) {
						searchDir(plugins, mods, path);
					} else if (behavior == DirectoryBehavior.MOD) {
						loadMod(path, plugins, mods);
					}
				}
			}
		}
	}

	private static void loadMod(Path path, List<PluginHolder> plugins, List<LoadableMod> mods) {
		Map<PluginHolder, LoadableMod> loaded = new HashMap<>();
		for (PluginHolder holder : plugins) {
			try {
				LoadableMod mod = holder.plugin().loadMod(path);
				if (mod != null) {
					loaded.put(holder, mod);
				}
			} catch (Throwable t) {
				throw ModLoadingException.errorLoading(holder, path, t);
			}
		}

		if (loaded.isEmpty()) {
			throw ModLoadingException.notLoaded(path);
		} else if (loaded.size() != 1) {
			throw ModLoadingException.multipleLoaded(loaded, path);
		}

		// size == 1
		mods.add(loaded.values().iterator().next());
	}

	public enum DirectoryBehavior {
		MOD,
		SCAN,
		IGNORE;

		public static DirectoryBehavior of(Path dir) throws IOException {
			Path propertiesFile = dir.resolve("cichlid.properties");
			if (!Files.exists(propertiesFile))
				return MOD;

			Properties properties = new Properties();
			properties.load(Files.newInputStream(propertiesFile));
			String behavior = properties.getProperty("load_behavior");
			if (behavior == null)
				return MOD;

			return switch (behavior) {
				case "mod" -> MOD;
				case "scan" -> SCAN;
				case "ignore" -> IGNORE;
				default -> throw new IllegalStateException("Directory has an invalid load_behavior of '" + behavior + "': " + dir);
			};
		}
	}

	public static class ModLoadingException extends RuntimeException {
		private ModLoadingException(String message, Throwable cause) {
			super(message, cause);
		}

		private ModLoadingException(String message) {
			super(message);
		}

		public static ModLoadingException errorLoading(PluginHolder holder, Path path, Throwable cause) {
			return new ModLoadingException("Plugin '" + holder.metadata().id() + "' errored while loading mod at " + path, cause);
		}

		public static ModLoadingException notLoaded(Path path) {
			return new ModLoadingException("No plugin could load mod at " + path);
		}

		public static ModLoadingException multipleLoaded(Map<PluginHolder, LoadableMod> map, Path path) {
			StringBuilder builder = new StringBuilder("Multiple plugins loaded mod at ").append(path);
			map.forEach(
					(holder, mod) -> builder.append("\n\t- ").append(holder.metadata().id()).append(": ").append(mod)
			);
			return new ModLoadingException(builder.toString());
		}

		public static ModLoadingException errorAdding(PluginHolder holder, Throwable cause) {
			return new ModLoadingException("Error while adding additional mods from plugin " + holder.metadata().id(), cause);
		}
	}
}
