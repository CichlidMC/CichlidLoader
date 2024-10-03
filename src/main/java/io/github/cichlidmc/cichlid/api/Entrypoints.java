package io.github.cichlidmc.cichlid.api;

import io.github.cichlidmc.cichlid.impl.loadable.mod.EntrypointsImpl;

import java.util.Collections;
import java.util.List;

/**
 * Map of entrypoint implementations contained within a mod.
 * An entrypoint is an interface that mods can provide implementations for in their metadata.
 * One mod may provide multiple implementations of a single entrypoint.
 * Each entrypoint is handled uniquely, but typically the class will be instantiated and a method will be invoked.
 */
public interface Entrypoints {
	Entrypoints EMPTY = new EntrypointsImpl(Collections.emptyMap());

	/**
	 * Get the entrypoints for the given key.
	 * <br>
	 * Example: {@code get("pre_launch") -> ["com.example.mymod.MyModPreLaunch"]}
	 */
	List<String> get(String key);

	/**
	 * Returns true if there are any entrypoints associated with the given key.
	 */
	boolean has(String key);
}
