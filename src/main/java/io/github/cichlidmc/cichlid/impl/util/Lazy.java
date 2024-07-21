package io.github.cichlidmc.cichlid.impl.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
	private Supplier<T> supplier;
	private T value;

	public Lazy(Supplier<T> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
	}

	@Override
	public T get() {
		if (this.supplier != null) {
			this.value = this.supplier.get();
			this.supplier = null;
		}
		return this.value;
	}
}
