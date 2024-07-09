package io.github.cichlidmc.cichlid_loader.impl;

import java.lang.instrument.Instrumentation;
import java.util.List;

import io.github.cichlidmc.cichlid_loader.api.entrypoint.EntrypointHelper;
import io.github.cichlidmc.cichlid_loader.api.entrypoint.PreLaunchEntrypoint;
import io.github.cichlidmc.cichlid_loader.api.mod.Mod;
import io.github.cichlidmc.cichlid_loader.impl.logging.CichlidLogger;
import io.github.cichlidmc.cichlid_loader.impl.mod.ModLoading;

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

		List<Mod> mods = ModLoading.run();
		if (!mods.isEmpty()) {
			logger.info("Loading " + mods.size() + " mods:");
			for (Mod mod : mods) {
				logger.info("\t- " + mod.metadata().id());
			}
			mods.forEach(mod -> instrumentation.appendToSystemClassLoaderSearch(mod.file()));
		}

		EntrypointHelper.invoke(PreLaunchEntrypoint.class, PreLaunchEntrypoint.KEY, PreLaunchEntrypoint::preLaunch);
		logger.info("Cichlid initialized! Continuing to primary application...");
	}
}
