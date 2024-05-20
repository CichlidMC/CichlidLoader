package io.github.tropheusj.cichlid.impl;

import java.lang.instrument.Instrumentation;

import io.github.tropheusj.cichlid.api.CichlidLoader;
import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;
import io.github.tropheusj.cichlid.api.logging.CichlidLogger;

public class CichlidAgent {
	private static final CichlidLogger logger = CichlidLogger.get(CichlidAgent.class);

	/*
	steps:
	1. Basic initialization. Logger, class transformation
	2. Load plugins
	3. Use plugins to load mods
	4. Invoke preLaunch
	5. continue to primary application
	 */
	public static void premain(String args, Instrumentation instrumentation) {
		logger.info("Cichlid initializing!");
		logger.info("Args: " + args);
		instrumentation.addTransformer(new CichlidTransformer());
		CichlidLoader.INSTANCE.invokeEntrypoint(PreLaunchEntrypoint.class, PreLaunchEntrypoint.KEY, PreLaunchEntrypoint::preLaunch);
	}
}
