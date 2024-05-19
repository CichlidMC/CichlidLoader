package io.github.tropheusj.cichlid.impl;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class CichlidTransformer implements ClassFileTransformer {
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain, byte[] classfileBuffer) {
		System.out.println("Transforming class " + className + " on classloader " + loader);
		return classfileBuffer;
	}
}
