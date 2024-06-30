package io.github.cichlidmc.cichlid_loader.impl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.github.cichlidmc.cichlid_loader.api.CichlidLoader;
import io.github.cichlidmc.cichlid_loader.impl.logging.CichlidLogger;
import io.github.cichlidmc.cichlid_loader.api.mod.Mod;

@SuppressWarnings("unused") // Constructed with reflection in CichlidLoader
public class CichlidLoaderImpl implements CichlidLoader {
	public static final CichlidLoaderImpl INSTANCE = (CichlidLoaderImpl) CichlidLoader.INSTANCE;

	private static final CichlidLogger logger = CichlidLogger.get(CichlidLoader.class);

	private final Map<String, Mod> mods = Map.of();
	private final Set<Mod> modSet = Set.of();

	@Override
	public Set<Mod> allMods() {
		return this.modSet;
	}

	@Override
	public boolean isModLoaded(String id) {
		return this.mods.containsKey(id);
	}

	@Override
	public Optional<Mod> getMod(String id) {
		return Optional.ofNullable(this.mods.get(id));
	}
}
