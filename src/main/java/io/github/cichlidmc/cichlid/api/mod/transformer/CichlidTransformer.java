package io.github.cichlidmc.cichlid.api.mod.transformer;

import org.objectweb.asm.tree.ClassNode;

public interface CichlidTransformer {
	/**
	 * Arbitrarily transform the given class before loading it.
	 * With great power comes great responsibility.
	 */
	void transform(ClassNode node);
}
