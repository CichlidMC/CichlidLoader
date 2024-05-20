package io.github.tropheusj.cichlid.api;

import java.util.function.Supplier;

public class CichlidUtils {
	/**
	 * Return the value of the given supplier.
	 * Useful for initializing static fields.
	 */
	public static <T> T make(Supplier<T> supplier) {
		return supplier.get();
	}
}
