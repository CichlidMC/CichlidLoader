package io.github.cichlidmc.cichlid.impl.loadable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import io.github.cichlidmc.cichlid.api.Credits;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import org.jetbrains.annotations.Nullable;

public final class CreditsImpl implements Credits {
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

	public static Credits fromJson(@Nullable JsonValue value) {
		if (value == null)
			return EMPTY;

		Map<String, String> map = new LinkedHashMap<>(); // maintain order
		value.asObject().forEach((k, v) -> map.put(k, v.asString().value()));
		return new CreditsImpl(map);
	}
}
