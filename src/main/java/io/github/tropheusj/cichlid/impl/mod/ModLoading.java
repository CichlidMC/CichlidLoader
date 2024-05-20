package io.github.tropheusj.cichlid.impl.mod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.github.tropheusj.cichlid.api.CichlidUtils;
import io.github.tropheusj.cichlid.api.mod.Mod;
import io.github.tropheusj.cichlid.impl.CichlidPaths;
import io.github.tropheusj.cichlid.impl.mod.impl.ModImpl;

public class ModLoading {
	public static List<LoadableMod> run() {
		List<LoadableMod> mods = new ArrayList<>();
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

	private static LoadableMod loadFromFile(Path path) throws IOException {
		if (!path.getFileName().toString().endsWith(".jar"))
			return null;

		JarFile jar = new JarFile(path.toFile());
		ZipEntry metadataEntry = jar.getEntry(Mod.METADATA);
		if (metadataEntry == null)
			return null;
		InputStream stream = jar.getInputStream(metadataEntry);
		JsonElement json = JsonParser.parseReader(new InputStreamReader(stream));
		try {
			ModImpl mod = ModImpl.fromJson(json);
			return new LoadableMod(mod, jar);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}
}
