package io.github.tropheusj.cichlid.api.mod;

import java.util.function.BiConsumer;

import io.github.tropheusj.cichlid.api.CichlidLoader;
import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;

/**
 * Map of entrypoint implementations contained within a mod.
 * An entrypoint is an interface that mods can provide implementations for in their metadata.
 * Each entrypoint is handled uniquely, but typically the class will be instantiated and a method will be invoked.
 * @see PreLaunchEntrypoint
 * @see CichlidLoader#invokeEntrypoint(Class, String, BiConsumer)
 */
public interface Entrypoints {
	/**
	 * Get the entrypoint for the given key.
	 * <br>
	 * Example: {@code get("pre-launch") -> "com.example.mymod.MyModPreLaunch"}
	 */
	String get(String key);
}
