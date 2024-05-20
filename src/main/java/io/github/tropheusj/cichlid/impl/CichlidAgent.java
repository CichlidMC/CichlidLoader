package io.github.tropheusj.cichlid.impl;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.tropheusj.cichlid.api.CichlidLoader;
import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;

public class CichlidAgent {
	/*
	steps:
	1. Basic initialization. Logger, class transformation
	2. Load plugins
	3. Use plugins to load mods
	4. Invoke preLaunch
	5. continue to primary application
	 */
	public static void premain(String args, Instrumentation instrumentation) {
		System.out.println("Cichlid initializing!");
		System.out.println("Args: " + args);
		instrumentation.addTransformer(new CichlidTransformer());

		CichlidPaths paths = CichlidPaths.get();
		Path log = paths.logs().resolve("latest.txt");
		try {
			Files.createDirectories(log.getParent());
			Files.writeString(log, "logged");
		} catch (IOException e) {
			System.out.println("error: " + e.getMessage());
		}

		CichlidLoader.INSTANCE.invokeEntrypoint(PreLaunchEntrypoint.class, PreLaunchEntrypoint.KEY, PreLaunchEntrypoint::preLaunch);
	}
}
