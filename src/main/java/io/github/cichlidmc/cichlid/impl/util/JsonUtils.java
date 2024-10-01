package io.github.cichlidmc.cichlid.impl.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
	public static String assertString(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonPrimitive) || !element.getAsJsonPrimitive().isString())
			throw new JsonSyntaxException("Not a string: " + element);
		return element.getAsJsonPrimitive().getAsString();
	}

	public static String getString(JsonObject json, String key) throws JsonSyntaxException {
		return assertString(json.get(key));
	}

	public static List<String> getStringList(JsonObject json, String key) throws JsonSyntaxException {
		try {
			// convert single string to list
			return Collections.singletonList(getString(json, key));
		} catch (JsonSyntaxException ignored) {}

		JsonElement element = json.get(key);
		if (!(element instanceof JsonArray))
			throw new JsonSyntaxException("Not a list: " + key);

		return element.getAsJsonArray().asList().stream()
				.map(JsonUtils::assertString)
				.collect(Collectors.toList());
	}
}
