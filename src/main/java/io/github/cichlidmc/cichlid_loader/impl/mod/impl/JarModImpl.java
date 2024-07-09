package io.github.cichlidmc.cichlid_loader.impl.mod.impl;

import java.nio.file.Path;
import java.util.Optional;

import io.github.cichlidmc.cichlid_loader.api.mod.Mod;
import io.github.cichlidmc.cichlid_loader.api.mod.ModMetadata;

public record JarModImpl(ModMetadata metadata) implements Mod {
	@Override
	public Optional<Path> resources() {
		// TODO
		return Optional.empty();
	}
}
