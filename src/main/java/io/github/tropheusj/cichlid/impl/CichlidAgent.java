package io.github.tropheusj.cichlid.impl;

import java.lang.instrument.Instrumentation;
import java.util.List;

import io.github.tropheusj.cichlid.api.entrypoint.EntrypointHelper;
import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;
import io.github.tropheusj.cichlid.api.logging.CichlidLogger;
import io.github.tropheusj.cichlid.impl.mod.LoadableMod;
import io.github.tropheusj.cichlid.impl.mod.ModLoading;

public class CichlidAgent {
	private static final CichlidLogger logger = CichlidLogger.get(CichlidAgent.class);

	/*
	steps:
	1. Basic initialization. Logger, class transformation
	2. find and load mods
	4. Invoke preLaunch
	5. continue to primary application
	 */
	public static void premain(String args, Instrumentation instrumentation) {
		logger.info("Cichlid initializing!");
		instrumentation.addTransformer(new CichlidTransformer());

		List<LoadableMod> mods = ModLoading.run();
		if (!mods.isEmpty()) {
			logger.info("Loading " + mods.size() + " mods:");
			for (LoadableMod mod : mods) {
				logger.info("\t- " + mod.mod().id());
			}
			mods.forEach(mod -> instrumentation.appendToSystemClassLoaderSearch(mod.file()));
		}

		EntrypointHelper.invoke(PreLaunchEntrypoint.class, PreLaunchEntrypoint.KEY, PreLaunchEntrypoint::preLaunch);
		logger.info("Cichlid initialized! Continuing to primary application...");
	}
}
