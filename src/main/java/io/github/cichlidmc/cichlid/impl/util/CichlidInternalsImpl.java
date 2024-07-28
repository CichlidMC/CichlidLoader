package io.github.cichlidmc.cichlid.impl.util;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import io.github.cichlidmc.cichlid.api.CichlidInternals;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.api.Mod;
import io.github.cichlidmc.cichlid.api.ModMetadata;
import io.github.cichlidmc.cichlid.impl.CichlidLoaderImpl;
import io.github.cichlidmc.cichlid.impl.loadable.mod.ModMetadataImpl;

@SuppressWarnings("unused") // accessed via reflection
public class CichlidInternalsImpl implements CichlidInternals {
	public static final CichlidInternalsImpl INSTANCE = new CichlidInternalsImpl();

	@Override
	public Set<Mod> mods() {
		return CichlidLoaderImpl.mods();
	}

	@Override
	public boolean isModLoaded(String id) {
		return CichlidLoaderImpl.isModLoaded(id);
	}

	@Override
	public Optional<Mod> getMod(String id) {
		return CichlidLoaderImpl.getMod(id);
	}

	@Override
	public Set<Metadata> plugins() {
		return CichlidLoaderImpl.plugins();
	}

	@Override
	public Optional<Metadata> getPlugin(String id) {
		return CichlidLoaderImpl.getPlugin(id);
	}

	@Override
	public Metadata metadata() {
		return CichlidLoaderImpl.metadata();
	}

	@Override
	public ModMetadata readModMetadata(InputStream stream) {
		return ModMetadataImpl.fromJson(stream);
	}

	@Override
	public ModMetadata readModMetadata(Path file) {
		return ModMetadataImpl.fromJson(file);
	}
}
