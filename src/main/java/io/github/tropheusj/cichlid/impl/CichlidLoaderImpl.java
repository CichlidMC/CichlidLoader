package io.github.tropheusj.cichlid.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.tropheusj.cichlid.api.CichlidLoader;
import io.github.tropheusj.cichlid.api.entrypoint.EntrypointException;
import io.github.tropheusj.cichlid.api.logging.CichlidLogger;
import io.github.tropheusj.cichlid.api.mod.Mod;

@SuppressWarnings("unused") // Constructed with reflection in CichlidLoader
public class CichlidLoaderImpl implements CichlidLoader {
	public static final CichlidLoaderImpl INSTANCE = (CichlidLoaderImpl) CichlidLoader.INSTANCE;

	private static final CichlidLogger logger = CichlidLogger.get(CichlidLoader.class);

	private final Map<String, Mod> mods = Map.of();
	private final Set<Mod> modSet = Set.of();

	@Override
	public Set<Mod> allMods() {
		return this.modSet;
	}

	@Override
	public boolean isModLoaded(String id) {
		return this.mods.containsKey(id);
	}

	@Override
	public Optional<Mod> getMod(String id) {
		return Optional.ofNullable(this.mods.get(id));
	}

	@Override
	public <T> void invokeEntrypoint(Class<T> clazz, String key, BiConsumer<T, Mod> consumer) {
		this.invokeEntrypointSafe(clazz, key, consumer, exception -> {
			throw exception;
		});
	}

	@Override
	public <T> void invokeEntrypointSafe(Class<T> clazz, String key, BiConsumer<T, Mod> consumer, Consumer<EntrypointException> errorConsumer) {
		List<Mod> list = this.modSet.stream().filter(mod -> mod.entrypoints().has(key)).toList();
		if (list.isEmpty())
			return;

		logger.info("Invoking '" + key + "' entrypoint on " + list.size() + " mod(s)...");
		for (Mod mod : list) {
			List<String> classNames = mod.entrypoints().get(key);
			for (String className : classNames) {
				try {
					Class<?> implClass = Class.forName(className);
					Object impl = implClass.getConstructor().newInstance();
					T entrypoint = clazz.cast(impl);
					consumer.accept(entrypoint, mod);
				} catch (Throwable t) {
					errorConsumer.accept(new EntrypointException(mod, key, t));
				}
			}
		}
	}
}
