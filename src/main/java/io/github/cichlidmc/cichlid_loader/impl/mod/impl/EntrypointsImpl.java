package io.github.cichlidmc.cichlid_loader.impl.mod.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid_loader.api.mod.Entrypoints;
import io.github.cichlidmc.cichlid_loader.impl.JsonUtils;

public record EntrypointsImpl(Map<String, List<String>> map) implements Entrypoints {
	@Override
	public List<String> get(String key) {
		return this.map.get(key);
	}

	@Override
	public boolean has(String key) {
		return this.map.containsKey(key);
	}

	public static Entrypoints fromJson(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonObject json))
			throw new JsonSyntaxException("Not an object: " + element);

		Map<String, List<String>> map = new HashMap<>();
		json.keySet().forEach(key -> map.put(key, JsonUtils.getStringList(json, key)));
		return new EntrypointsImpl(map);
	}
}
