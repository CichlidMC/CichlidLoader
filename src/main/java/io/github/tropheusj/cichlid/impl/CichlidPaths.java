package io.github.tropheusj.cichlid.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.tropheusj.cichlid.api.CichlidUtils;

public class CichlidPaths {
	public static final Path ROOT = Paths.get("").resolve("cichlid");
	public static final Path LOGS = ROOT.resolve("logs");
	public static final Path MODS = ROOT.resolve("mods");
}
