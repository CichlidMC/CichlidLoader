package io.github.cichlidmc.cichlid_loader.impl.mod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid_loader.api.CichlidUtils;
import io.github.cichlidmc.cichlid_loader.api.mod.Mod;
import io.github.cichlidmc.cichlid_loader.impl.CichlidPaths;
import io.github.cichlidmc.cichlid_loader.impl.mod.impl.JarModImpl;
import io.github.cichlidmc.cichlid_loader.impl.mod.impl.ModMetadataImpl;

public class ModLoading {
	/**
	 * File name for a mod's metadata, located in the root of the jar.
	 */
	public static final String METADATA = "cichlid.mod.json";

	public static List<Mod> run() {
		List<Mod> mods = new ArrayList<>();
		try {
			Files.walkFileTree(
					CichlidPaths.MODS,
					CichlidUtils.fileWalker(file -> mods.add(loadFromFile(file)))
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return mods;
	}

	private static Mod loadFromFile(Path path) throws IOException {
		if (!path.getFileName().toString().endsWith(".jar"))
			return null;

		try (FileSystem fs = FileSystems.newFileSystem(path)) {
			Path file = fs.getPath(METADATA);
			if (!Files.exists(file))
				return null;
			InputStream stream = Files.newInputStream(file);
			JsonElement json = JsonParser.parseReader(new InputStreamReader(stream));
			try {
				ModMetadataImpl metadata = ModMetadataImpl.fromJson(json);
				return new JarModImpl(metadata);
			} catch (JsonSyntaxException e) {
				return null;
			}
		}
	}
}
