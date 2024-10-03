package io.github.cichlidmc.cichlid.impl.loadable.mod;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.cichlidmc.cichlid.api.Entrypoints;
import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.composite.JsonArray;
import io.github.cichlidmc.tinyjson.value.primitive.JsonString;
import org.jetbrains.annotations.Nullable;

public class EntrypointsImpl implements Entrypoints {
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

	public static Entrypoints fromJson(@Nullable JsonValue value) {
		if (value == null)
			return EMPTY;

		Map<String, List<String>> map = new LinkedHashMap<>(); // maintain order
		value.asObject().forEach((k, v) -> map.put(k, getStringList(v)));
		return new EntrypointsImpl(map);
	}

	private static List<String> getStringList(JsonValue value) {
		if (value instanceof JsonString) {
			return Collections.singletonList(value.asString().value());
		} else if (value instanceof JsonArray) {
			return value.asArray().stream()
					.map(JsonValue::asString)
					.map(JsonString::value)
					.collect(Collectors.toList());
		} else {
			throw new JsonException("Not a string or array: " + value);
		}
	}
}
