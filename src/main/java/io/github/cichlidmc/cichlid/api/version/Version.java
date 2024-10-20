package io.github.cichlidmc.cichlid.api.version;

import io.github.cichlidmc.cichlid.impl.version.VersionImpl;

/**
 * A Version of a mod, plugin, or Cichlid itself.
 * <p>
 * Cichlid versions use FlexVer (specifically version 1.1.1), a SemVer-compatible version format.
 * For more information, see <a href="https://github.com/unascribed/FlexVer">here</a>.
 */
public interface Version extends Comparable<Version> {
	/**
	 * Returns the same string that this version was parsed from.
	 */
	@Override
	String toString();

	/**
	 * Parse a version from the given string.
	 * Parsing will never fail, and will always return a valid Version.
	 */
	static Version parse(String string) {
		return VersionImpl.parse(string);
	}
}
