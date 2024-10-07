package io.github.cichlidmc.cichlid.impl.transformer;

import io.github.cichlidmc.cichlid.api.mod.CichlidLoader;
import io.github.cichlidmc.cichlid.api.mod.env.ClientOnly;
import io.github.cichlidmc.cichlid.api.mod.env.DedicatedServerOnly;
import io.github.cichlidmc.cichlid.api.mod.env.EnvOnly;
import io.github.cichlidmc.cichlid.api.mod.env.Environments;
import io.github.cichlidmc.cichlid.api.mod.transformer.CichlidTransformer;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.util.List;
import java.util.function.Function;

public class EnvironmentStripper implements CichlidTransformer {
	public static final String ENV_ONLY_DESC = Type.getDescriptor(EnvOnly.class);
	public static final String CLIENT_ONLY_DESC = Type.getDescriptor(ClientOnly.class);
	public static final String SERVER_ONLY_DESC = Type.getDescriptor(DedicatedServerOnly.class);

	private static final String env = CichlidLoader.environment();

	@Override
	public void transform(ClassNode node) {
		filter(node.fields, field -> field.visibleAnnotations);
		filter(node.methods, method -> method.visibleAnnotations);
	}

	private static <T> void filter(List<T> list, Function<T, @Nullable List<AnnotationNode>> function) {
		list.removeIf(member -> {
			List<AnnotationNode> annotations = function.apply(member);
			String environment = extractEnvironment(annotations);
			return environment != null && !environment.equals(env);
		});
	}

	private static String extractEnvironment(@Nullable List<AnnotationNode> annotations) {
		if (annotations == null || annotations.isEmpty())
			return null;

		for (AnnotationNode annotation : annotations) {
			if (CLIENT_ONLY_DESC.equals(annotation.desc)) {
				return Environments.CLIENT;
			} else if (SERVER_ONLY_DESC.equals(annotation.desc)) {
				return Environments.DEDICATED_SERVER;
			} else if (ENV_ONLY_DESC.equals(annotation.desc)) {
				// [0] should be "value", the field name
				// [1] should be the actual value, a string
				Object value = annotation.values.get(1);
				if (value instanceof String) {
					return (String) value;
				} else {
					throw new IllegalStateException("Expected string value for EnvOnly annotation, got " + value);
				}
			}
		}

		return null;
	}
}
