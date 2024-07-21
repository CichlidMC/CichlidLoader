package io.github.cichlidmc.cichlid.impl;

import java.util.Map;

import io.github.cichlidmc.cichlid.impl.loadable.CreditsImpl;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.api.Metadata;

public record CichlidMetadata(String version) implements Metadata {
	public static final Credits CREDITS = new CreditsImpl(Map.of("CichlidMC", "Author"));
	@Override
	public String id() {
		return "cichlid";
	}

	@Override
	public String name() {
		return "Cichlid";
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
