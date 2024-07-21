package io.github.cichlidmc.cichlid.api;

import java.util.regex.Pattern;

/**
 * Common metadata shared between mods, plugins, and Cichlid itself.
 */
public interface Metadata {
	/**
	 * Regex for valid IDs. No length limit, cannot be empty. Valid characters: a-z, 0-9, and _.
	 */
	Pattern ID_REGEX = Pattern.compile("[a-z0-9_]+");

	/**
	 * The unique ID of this mod or plugin. Only one mod or plugin with a given ID can be loaded.
	 * @see #ID_REGEX
	 */
	String id();

	/**
	 * The fully formatted name. Only disallowed characters are line breaks.
	 */
	String name();

	/**
	 * Loaded version as a string.
	 */
	String version();

	/**
	 * Description. No restrictions, may be multi-line.
	 */
	String description();

	/**
	 * Credits. Authors, Artists, etc.
	 * @see Credits
	 */
	Credits credits();

	/**
	 * A formatted string that contains both the name and ID of this mod or plugin.
	 * This is useful for clearly showing a mod to users, ex. in the case of an error.
	 * <br>
	 * Example: {@code 'My Mod' (ID: 'mymod')}
	 */
	default String blame() {
		return "'" + this.name() + "' (ID: '" + this.id() + "')";
	}
}
