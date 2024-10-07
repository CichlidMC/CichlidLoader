package io.github.cichlidmc.cichlid.impl;

import io.github.cichlidmc.tinyjson.TinyJson;

import java.nio.file.Path;

public final class InstanceMeta {
	public final String environment;

	public InstanceMeta(String environment) {
		this.environment = environment;
	}

	public static InstanceMeta read() {
		Path path = CichlidPaths.META.resolve("instance.json");

		String environment = TinyJson.parseOrThrow(path).asObject()
				.get("environment").asString().value();
		return new InstanceMeta(environment);
	}
}
