package io.github.tropheusj.cichlid.api.entrypoint;

import io.github.tropheusj.cichlid.api.mod.Mod;

/**
 * Invoked during Cichlid initialization, before the primary application has started.
 * <br>
 * Be careful with what classes you access; loading things at the wrong time can have consequences.
 * Rule of thumb: Stick with CichlidLoader API and standard Java classes.
 * <br>
 * Implementations are identified by the "pre-launch" key. They should implement this interface
 * and have a no-arg constructor.
 */
public interface PreLaunchEntrypoint {
	String KEY = "pre-launch";

	void preLaunch(Mod mod);
}
