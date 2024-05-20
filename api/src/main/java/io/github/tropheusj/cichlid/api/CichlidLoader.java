package io.github.tropheusj.cichlid.api;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.tropheusj.cichlid.api.entrypoint.EntrypointException;
import io.github.tropheusj.cichlid.api.mod.Mod;

public interface CichlidLoader {
	CichlidLoader INSTANCE = CichlidUtils.make(() -> {
		try {
			Class<?> implClass = Class.forName("io.github.tropheusj.cichlid.impl.CichlidLoaderImpl");
			Constructor<?> constructor = implClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			Object impl = constructor.newInstance();
			return (CichlidLoader) impl;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing CichlidLoader", e);
		}
	});

	/**
	 * Set of all loaded mods. This will never change.
	 */
	Set<Mod> allMods();

	/**
	 * @return true if a mod with the given ID is loaded.
	 */
	boolean isModLoaded(String id);

	/**
	 * get the Mod associated wth the given ID, or empty if it's not loaded.
	 */
	Optional<Mod> getMod(String id);

	/**
	 * Invoke the given entrypoint on all mods.
	 * <br>
	 * The entrypoint will be queried on all mods, and if present, the class will be instantiated.
	 * It will be cast to the given class, and the given consumer will be invoked.
	 * <br>
	 * Classes are expected to have a public, no-arg constructor, which will be used to create
	 * the entrypoint instance.
	 * <br>
	 * Each time the consumer is invoked, it will be wrapped in a try-catch, and any
	 * error will be re-thrown as a {@link EntrypointException} which may optionally be caught.
	 * This will stop invoking entrypoints early; if you want to always invoke all of them, use
	 * {@link #invokeEntrypointSafe(Class, String, BiConsumer, Consumer)} (Class, String, Consumer, Consumer)}.
	 * @param clazz the expected class of implementations
	 * @param key the key for the entrypoint
	 * @param consumer a consumer that will be invoked for every instance
	 * @throws EntrypointException in the case of any exception during the consumer, or a type mismatch
	 */
	<T> void invokeEntrypoint(Class<T> clazz, String key, BiConsumer<T, Mod> consumer);

	/**
	 * Identical to {@link #invokeEntrypoint(Class, String, BiConsumer)} (Class, String, Consumer)}, but errors during invocation
	 * are non-fatal. Instead of stopping and throwing the exception, the error consumer will be invoked.
	 * It's recommended to add logging here.
	 */
	<T> void invokeEntrypointSafe(Class<T> clazz, String key, BiConsumer<T, Mod> consumer, Consumer<EntrypointException> errorConsumer);
}
