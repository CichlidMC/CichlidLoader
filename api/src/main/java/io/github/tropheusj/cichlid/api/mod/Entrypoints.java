package io.github.tropheusj.cichlid.api.mod;

import java.util.List;
import java.util.function.BiConsumer;

import io.github.tropheusj.cichlid.api.entrypoint.EntrypointHelper;
import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;

/**
 * Map of entrypoint implementations contained within a mod.
 * An entrypoint is an interface that mods can provide implementations for in their metadata.
 * One mod may provide multiple implementations of a single entrypoint.
 * Each entrypoint is handled uniquely, but typically the class will be instantiated and a method will be invoked.
 * @see PreLaunchEntrypoint
 * @see EntrypointHelper#invoke(Class, String, BiConsumer)
 */
public interface Entrypoints {
	/**
	 * Get the entrypoint for the given key.
	 * <br>
	 * Example: {@code get("pre-launch") -> ["com.example.mymod.MyModPreLaunch"]}
	 */
	List<String> get(String key);

	/**
	 * Returns true if there are any entrypoints associated with the given key.
	 */
	boolean has(String key);
}
