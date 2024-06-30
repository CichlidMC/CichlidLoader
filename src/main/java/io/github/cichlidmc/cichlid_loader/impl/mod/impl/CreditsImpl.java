package io.github.cichlidmc.cichlid_loader.impl.mod.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid_loader.api.mod.Credits;
import io.github.cichlidmc.cichlid_loader.impl.JsonUtils;

public record CreditsImpl(Map<String, String> map) implements Credits {
	@Override
	public String get(String name) {
		return this.map.get(name);
	}

	@Override
	public void forEach(BiConsumer<String, String> consumer) {
		this.map.forEach(consumer);
	}

	public static Credits fromJson(JsonElement element) {
		if (!(element instanceof JsonObject json))
			throw new JsonSyntaxException("Not an object: " + element);

		Map<String, String> map = new HashMap<>();
		json.keySet().forEach(key -> map.put(key, JsonUtils.getString(json, key)));
		return new CreditsImpl(map);
	}
}
