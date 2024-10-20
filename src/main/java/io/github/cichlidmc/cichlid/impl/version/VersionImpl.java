package io.github.cichlidmc.cichlid.impl.version;

import java.util.List;

import io.github.cichlidmc.cichlid.api.version.Version;
import org.jetbrains.annotations.NotNull;

public class VersionImpl implements Version {
	private final String string;
	private final List<FlexVerComparator.VersionComponent> components;

	private VersionImpl(String string, List<FlexVerComparator.VersionComponent> components) {
		this.string = string;
		this.components = components;
	}

	@Override
	public int compareTo(@NotNull Version o) {
		return FlexVerComparator.compare(this.components, ((VersionImpl) o).components);
	}

	@Override
	public String toString() {
		return this.string;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof VersionImpl && ((VersionImpl) obj).string.equals(this.string);
	}

	public static Version parse(String string) {
		List<FlexVerComparator.VersionComponent> components = FlexVerComparator.decompose(string);
		return new VersionImpl(string, components);
	}
}
