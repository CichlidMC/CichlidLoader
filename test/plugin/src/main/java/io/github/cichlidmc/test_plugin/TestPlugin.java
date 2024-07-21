package io.github.cichlidmc.test_plugin;

import java.nio.file.Path;

import io.github.cichlidmc.cichlid.api.plugin.CichlidPlugin;
import io.github.cichlidmc.cichlid.api.plugin.LoadableMod;

public class TestPlugin implements CichlidPlugin {
	@Override
	public LoadableMod loadMod(Path path) {
		System.out.println("Test plugin trying to load mod at " + path);
		return null;
	}
}
