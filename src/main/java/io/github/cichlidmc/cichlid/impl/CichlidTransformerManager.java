package io.github.cichlidmc.cichlid.impl;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import io.github.cichlidmc.cichlid.api.mod.transformer.CichlidTransformer;
import io.github.cichlidmc.cichlid.impl.logging.CichlidLogger;
import io.github.cichlidmc.cichlid.impl.transformer.EnvironmentStripper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public enum CichlidTransformerManager implements ClassFileTransformer {
	INSTANCE;

	private static final CichlidLogger logger = CichlidLogger.get(CichlidTransformerManager.class);
	private static final List<CichlidTransformer> transformers = new ArrayList<>();

	static {
		registerTransformer(new EnvironmentStripper());
	}

	public static void registerTransformer(CichlidTransformer transformer) {
		transformers.add(transformer);
	}

	@Override
	public byte[] transform(ClassLoader loader, String name, Class<?> clazz, ProtectionDomain domain, byte[] bytes) {
		ClassReader reader = new ClassReader(bytes);
		ClassNode node = new ClassNode();
		reader.accept(node, 0);

		for (CichlidTransformer transformer : transformers) {
			transformer.transform(node);
		}

		ClassWriter writer = new ClassWriter(reader, 0);
		node.accept(writer);
		return writer.toByteArray();
	}
}
