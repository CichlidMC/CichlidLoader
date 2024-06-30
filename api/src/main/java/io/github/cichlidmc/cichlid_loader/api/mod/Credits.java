package io.github.cichlidmc.cichlid_loader.api.mod;

import java.util.function.BiConsumer;

/**
 * Map of people to their credit.
 * <br>
 * Common credits: Author, Contributor, Artist, Inspiration
 */
public interface Credits {
	/**
	 * Get the credit for the given person.
	 * <br>
	 * Example: {@code get("TropheusJ") -> "Author", get("QuiltMC") -> "Inspiration"}
	 */
	String get(String name);

	/**
	 * Invoke a consumer for every credit.
	 * First argument is the name, second is their credit.
	 */
	void forEach(BiConsumer<String, String> consumer);
}
