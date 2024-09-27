package io.github.cichlidmc.cichlid.api;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Ordered map of names to arbitrary roles. Common credits: Author, Contributor, Artist, Inspiration
 */
public interface Credits {
	/**
	 * Get the credit for the given person.
	 * <br>
	 * Example: {@code get("TropheusJ") -> "Author", get("QuiltMC") -> "Inspiration"}
	 */
	String get(String name);

	/**
	 * Get an ordered set of all credited names.
	 */
	Set<String> names();

	/**
	 * Invoke a consumer for every credit entry.
	 * First argument is the name, second is their role.
	 */
	void forEach(BiConsumer<String, String> consumer);

	boolean isEmpty();
}
