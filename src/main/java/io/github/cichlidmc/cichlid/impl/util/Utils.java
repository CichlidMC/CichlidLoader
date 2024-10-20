package io.github.cichlidmc.cichlid.impl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Utils {
	public static <T> T make(Supplier<T> supplier) {
		return supplier.get();
	}

	@SafeVarargs
	public static <T> List<T> listOf(T... values) {
		ArrayList<T> list = new ArrayList<>();
		Collections.addAll(list, values);
		return list;
	}
}
