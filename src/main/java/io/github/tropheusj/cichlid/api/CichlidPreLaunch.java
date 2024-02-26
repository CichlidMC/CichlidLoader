package io.github.tropheusj.cichlid.api;

/**
 * Invoked during Cichlid initialization, before the primary application has started.
 */
public interface CichlidPreLaunch {
	void preLaunch();
}
