package io.github.tropheusj.cichlid.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.tropheusj.cichlid.api.CichlidUtils;

public record CichlidPaths(Path root, Path logs, Path plugins, Path mods) {
	public static CichlidPaths INSTANCE = CichlidUtils.make(() -> {
		Path root = Paths.get("").resolve("cichlid");
		Path logs = root.resolve("logs");
		Path plugins = root.resolve("plugins");
		Path mods = root.resolve("mods");
		return new CichlidPaths(root, logs, plugins, mods);
	});
}
