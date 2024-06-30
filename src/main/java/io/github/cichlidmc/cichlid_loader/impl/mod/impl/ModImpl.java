package io.github.cichlidmc.cichlid_loader.impl.mod.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid_loader.api.mod.Credits;
import io.github.cichlidmc.cichlid_loader.api.mod.Entrypoints;
import io.github.cichlidmc.cichlid_loader.api.mod.Mod;
import io.github.cichlidmc.cichlid_loader.impl.JsonUtils;

public record ModImpl(String id, String name, String version, String description,
					  Credits credits, Entrypoints entrypoints) implements Mod {
	public static ModImpl fromJson(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonObject json))
			throw new JsonSyntaxException("Not an object");

		String id = JsonUtils.getString(json, "id");
		String name = JsonUtils.getString(json, "name");
		String version = JsonUtils.getString(json, "version");
		String description = JsonUtils.getString(json, "description");
		Credits credits = CreditsImpl.fromJson(json.get("credits"));
		Entrypoints entrypoints = EntrypointsImpl.fromJson(json.get("entrypoints"));

		return new ModImpl(id, name, version, description, credits, entrypoints);
	}
}
