package io.github.cichlidmc.cichlid.impl.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IdentifiedSet<T> {
	private final Map<String, T> map;
	private final Set<T> set;

	public IdentifiedSet(Set<T> set, Function<T, String> idFunction) {
		this.set = Collections.unmodifiableSet(set);
		this.map = new LinkedHashMap<>();
		for (T t : set) {
			this.map.put(idFunction.apply(t), t);
		}
	}

	public Set<T> set() {
		return set;
	}

	public T get(String id) {
		return this.map.get(id);
	}

	public boolean contains(String id) {
		return this.map.containsKey(id);
	}

	public static <A, B> IdentifiedSet<B> from(List<A> list, Function<A, B> function, Function<B, String> idFunction) {
		Set<B> set = list.stream().map(function).collect(Collectors.toSet());
		return new IdentifiedSet<>(set, idFunction);
	}
}
