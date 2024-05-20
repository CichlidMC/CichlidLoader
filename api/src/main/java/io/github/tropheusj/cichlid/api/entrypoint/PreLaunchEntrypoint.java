package io.github.tropheusj.cichlid.api.entrypoint;

import io.github.tropheusj.cichlid.api.mod.Mod;

/**
 * Invoked during Cichlid initialization, before the primary application has started.
 * Implementations are identified by the "pre-launch" key, and are expected to have a no-arg public constructor.
 */
public interface PreLaunchEntrypoint {
	String KEY = "pre-launch";

	void preLaunch(Mod mod);
}
