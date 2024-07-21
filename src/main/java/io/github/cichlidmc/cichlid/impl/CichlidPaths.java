package io.github.cichlidmc.cichlid.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CichlidPaths {
	public static final Path ROOT = Paths.get("").resolve("cichlid");
	public static final Path MODS = ROOT.resolve("mods");
	public static final Path PLUGINS = ROOT.resolve("plugins");
}
