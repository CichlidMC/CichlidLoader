package io.github.cichlidmc.cichlid.impl.loadable.plugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid.impl.CichlidPaths;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.builtin.StandardJarPlugin;
import io.github.cichlidmc.cichlid.impl.util.FileUtils;

public class PluginDiscovery {
	public static final String EXTENSION = ".clp";
	public static final String METADATA = "cichlid.plugin.json";

	public static List<LoadablePlugin> find() {
		try {
			List<LoadablePlugin> plugins = new ArrayList<>();
			Files.createDirectories(CichlidPaths.PLUGINS);
			Files.walkFileTree(CichlidPaths.PLUGINS, FileUtils.fileWalker(file -> loadPlugin(file, plugins)));
			// add built-in plugins last
			addBuiltInPlugins(plugins);
			return plugins;
		} catch (IOException e) {
			throw new RuntimeException("Error loading plugins", e);
		}
	}

	private static void loadPlugin(Path path, List<LoadablePlugin> plugins) throws IOException {
		if (FileUtils.isDisabled(path))
			return;

		String name = path.getFileName().toString();

		if (!name.endsWith(EXTENSION)) {
			throw new InvalidPluginException(name, "Not a plugin");
		}

		try (FileSystem fs = FileSystems.newFileSystem(path, null)) {
			Path metadataFile = fs.getPath(METADATA);
			if (!Files.exists(metadataFile)) {
				throw new InvalidPluginException(name, "Metadata not found");
			}

			InputStreamReader reader = new InputStreamReader(Files.newInputStream(metadataFile));
			JsonElement json = JsonParser.parseReader(reader);
			PluginMetadata metadata = PluginMetadataImpl.fromJson(json);
			JarFile jar = new JarFile(path.toFile());
			plugins.add(new LoadablePlugin.Jar(metadata, jar));
		} catch (JsonSyntaxException e) {
			throw new InvalidPluginException(name, "Invalid metadata", e);
		}
	}

	private static void addBuiltInPlugins(List<LoadablePlugin> list) {
		list.add(StandardJarPlugin.LOADABLE);
	}
}
