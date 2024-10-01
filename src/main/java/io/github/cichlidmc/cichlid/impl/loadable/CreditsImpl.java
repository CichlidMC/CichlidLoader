package io.github.cichlidmc.cichlid.impl.loadable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.cichlid.impl.util.JsonUtils;

public final class CreditsImpl implements Credits {
	public static final Credits EMPTY = new CreditsImpl(Collections.emptyMap());

	private final Map<String, String> map;

	public CreditsImpl(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String get(String name) {
		return this.map.get(name);
	}

	@Override
	public Set<String> names() {
		return Collections.unmodifiableSet(this.map.keySet());
	}

	@Override
	public void forEach(BiConsumer<String, String> consumer) {
		this.map.forEach(consumer);
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public static Credits fromJson(JsonElement element) {
		if (element == null)
			return EMPTY;

		if (!(element instanceof JsonObject))
			throw new JsonSyntaxException("Not an object: " + element);

		JsonObject json = element.getAsJsonObject();
		Map<String, String> map = new LinkedHashMap<>(); // maintain order
		json.keySet().forEach(key -> map.put(key, JsonUtils.getString(json, key)));
		return new CreditsImpl(map);
	}
}
