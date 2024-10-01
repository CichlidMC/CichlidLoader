package io.github.cichlidmc.cichlid.impl.loadable.mod;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid.api.Entrypoints;
import io.github.cichlidmc.cichlid.impl.util.JsonUtils;

public class EntrypointsImpl implements Entrypoints {
	public static final Entrypoints EMPTY = new EntrypointsImpl(Collections.emptyMap());

	private final Map<String, List<String>> map;

	public EntrypointsImpl(Map<String, List<String>> map) {
		this.map = map;
	}

	@Override
	public List<String> get(String key) {
		return this.map.get(key);
	}

	@Override
	public boolean has(String key) {
		return this.map.containsKey(key);
	}

	public static Entrypoints fromJson(JsonElement element) throws JsonSyntaxException {
		if (element == null)
			return EMPTY;

		if (!(element instanceof JsonObject))
			throw new JsonSyntaxException("Not an object: " + element);

		JsonObject json = element.getAsJsonObject();
		Map<String, List<String>> map = new HashMap<>();
		json.keySet().forEach(key -> map.put(key, JsonUtils.getStringList(json, key)));
		return new EntrypointsImpl(map);
	}
}
