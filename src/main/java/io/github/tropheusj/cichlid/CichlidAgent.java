package io.github.tropheusj.cichlid;

import java.lang.instrument.Instrumentation;

public class CichlidAgent {
	public static void premain(String args, Instrumentation instrumentation) {
		System.out.println("Cichlid initializing!");
		System.out.println("Args: " + args);
		instrumentation.addTransformer(new CichlidTransformer());
	}
}
