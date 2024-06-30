package io.github.cichlidmc.cichlid_loader.impl;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
	public static String assertString(JsonElement element) throws JsonSyntaxException {
		if (!(element instanceof JsonPrimitive primitive) || !primitive.isString())
			throw new JsonSyntaxException("Not a string: " + element);
		return primitive.getAsString();
	}

	public static String getString(JsonObject json, String key) throws JsonSyntaxException {
		return assertString(json.get(key));
	}

	public static List<String> getStringList(JsonObject json, String key) throws JsonSyntaxException {
		try {
			// convert single string to list
			return List.of(getString(json, key));
		} catch (JsonSyntaxException ignored) {}

		JsonElement element = json.get(key);
		if (!(element instanceof JsonArray array))
			throw new JsonSyntaxException("Not a list: " + key);

		return array.asList().stream().map(JsonUtils::assertString).toList();
	}
}
