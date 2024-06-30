package io.github.cichlidmc.cichlid_loader.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CichlidPaths {
	public static final Path ROOT = Paths.get("").resolve("cichlid");
	public static final Path LOGS = ROOT.resolve("logs");
	public static final Path MODS = ROOT.resolve("mods");
}