package io.github.cichlidmc.cichlid.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;
import io.github.cichlidmc.cichlid.impl.loadable.CreditsImpl;
import io.github.cichlidmc.cichlid.impl.util.Utils;

public final class CichlidMetadata implements Metadata {
	public static final Credits CREDITS = new CreditsImpl(Utils.make(() -> {
		Map<String, String> map = new HashMap<>();
		map.put("CichlidMC", "Author");
		return map;
	}));

	private final String version;

	public CichlidMetadata(String version) {
		this.version = version;
	}

	@Override
	public String id() {
		return "cichlid";
	}

	@Override
	public String name() {
		return "Cichlid";
	}

	@Override
	public String version() {
		return this.version;
	}

	@Override
	public String description() {
		return "Light-weight Java mod loader targeting Minecraft.";
	}

	@Override
	public Credits credits() {
		return CREDITS;
	}
}
