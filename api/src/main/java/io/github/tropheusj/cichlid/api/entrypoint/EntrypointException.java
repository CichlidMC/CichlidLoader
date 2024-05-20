package io.github.tropheusj.cichlid.api.entrypoint;

import io.github.tropheusj.cichlid.api.mod.Mod;

/**
 * An exception thrown during entrypoint invocation.
 */
public class EntrypointException extends RuntimeException {
	public EntrypointException(Mod mod, String key, Throwable cause) {
		super(makeMessage(mod, key), cause);
	}

	private static String makeMessage(Mod mod, String key) {
		return "Error invoking entrypoint '" + key + "' on mod " + mod.blame();
	}
}
