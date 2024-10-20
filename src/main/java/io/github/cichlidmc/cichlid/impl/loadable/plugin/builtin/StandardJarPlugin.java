package io.github.cichlidmc.cichlid.impl.loadable.plugin.builtin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.api.Mod;
import io.github.cichlidmc.cichlid.api.ModMetadata;
import io.github.cichlidmc.cichlid.api.mod.CichlidLoader;
import io.github.cichlidmc.cichlid.api.plugin.CichlidPlugin;
import io.github.cichlidmc.cichlid.api.plugin.LoadableMod;
import io.github.cichlidmc.cichlid.api.plugin.PluginMetadata;
import io.github.cichlidmc.cichlid.impl.CichlidMetadata;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.LoadablePlugin;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.PluginHolder;
import io.github.cichlidmc.cichlid.impl.loadable.plugin.PluginMetadataImpl;
import io.github.cichlidmc.cichlid.impl.util.FileUtils;
import io.github.cichlidmc.cichlid.impl.util.Lazy;
import io.github.cichlidmc.tinyjson.TinyJson;
import io.github.cichlidmc.tinyjson.value.JsonValue;

public class StandardJarPlugin implements CichlidPlugin {
	public static final String EXTENSION = ".cld";
	public static final String MOD_METADATA = "cichlid.mod.json";

	public static final PluginMetadata METADATA = new PluginMetadataImpl(
			"standard",
			"Standard " + EXTENSION + " Plugin",
			CichlidLoader.metadata().version(),
			"Built-in plugin for loading standard " + EXTENSION + " Cichlid mods",
			CichlidMetadata.CREDITS,
			StandardJarPlugin.class.getName()
	);

	public static final CichlidPlugin INSTANCE = new StandardJarPlugin();
	public static final PluginHolder HOLDER = new PluginHolder(INSTANCE, METADATA);
	public static final LoadablePlugin LOADABLE = new LoadablePlugin.BuiltIn(HOLDER);

	@Override
	public LoadableMod loadMod(Path path) {
		if (!FileUtils.nameEndsWith(path, EXTENSION))
			return null;

		// TODO: JiJ
		File file = FileUtils.toFileOrNull(path);
		if (file == null)
			return null;

		try {
			JarFile jar = new JarFile(file);
			JarEntry metadataEntry = jar.getJarEntry(MOD_METADATA);
			if (metadataEntry == null)
				return null;

			InputStream stream = jar.getInputStream(metadataEntry);
			JsonValue json = TinyJson.parse(stream);
			ModMetadata metadata = ModMetadata.fromJson(json);

			Mod mod = new JarModImpl(metadata, path);
			return new LoadableMod(mod, path.toString(), jar);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static class JarModImpl implements Mod {
		private final ModMetadata metadata;
		private final Lazy<Optional<Path>> root;

		public JarModImpl(ModMetadata metadata, Path file) {
			this.metadata = metadata;
			this.root = new Lazy<>(() -> {
				try {
					//noinspection resource, RedundantCast - intentionally left open, javac fails without cast
					FileSystem fs = FileSystems.newFileSystem(file, (ClassLoader) null);
					// jar should always have exactly 1 root
					Path root = fs.getRootDirectories().iterator().next();
					return Optional.of(root);
				} catch (IOException e) {
					throw new RuntimeException("Error opening jar filesystem at " + file, e);
				}
			});
		}

		@Override
		public ModMetadata metadata() {
			return this.metadata;
		}

		@Override
		public Metadata pluginMetadata() {
			return METADATA;
		}

		@Override
		public Optional<Path> resources() {
			return this.root.get();
		}
	}
}
