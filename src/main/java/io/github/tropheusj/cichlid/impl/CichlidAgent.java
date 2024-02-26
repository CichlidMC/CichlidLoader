package io.github.tropheusj.cichlid.impl;

import java.lang.instrument.Instrumentation;

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


	}
}
