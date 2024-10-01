package io.github.cichlidmc.cichlid.impl.util;

import java.util.function.Supplier;

public class Utils {
	public static <T> T make(Supplier<T> supplier) {
		return supplier.get();
	}
}
