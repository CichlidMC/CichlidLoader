package io.github.cichlidmc.cichlid.impl;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import io.github.cichlidmc.cichlid.impl.logging.CichlidLogger;

public class CichlidTransformer implements ClassFileTransformer {
	private static final CichlidLogger logger = CichlidLogger.get(CichlidTransformer.class);
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain, byte[] classfileBuffer) {
//		logger.info("Transforming class " + className + " on classloader " + loader);
		return classfileBuffer;
	}
}
