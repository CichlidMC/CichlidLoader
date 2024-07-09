package io.github.cichlidmc.cichlid_loader.api.mod;

import java.util.regex.Pattern;

public interface ModMetadata {
	/**
	 * Regex for valid mod IDs. No length limit, cannot be empty. Valid characters: a-z, 0-9, and _.
	 */
	Pattern ID_REGEX = Pattern.compile("[a-z0-9_]+");

	/**
	 * The unique ID of this mod. Only one mod with a given ID can be loaded.
	 * @see #ID_REGEX
	 */
	String id();

	/**
	 * The fully formatted name of this mod. Only disallowed characters are line breaks.
	 */
	String name();

	/**
	 * The version of this mod.
	 */
	String version();

	/**
	 * Description of this mod. No restrictions, may be multi-line.
	 */
	String description();

	/**
	 * The credits of this mod. Authors, Artists, etc.
	 * @see Credits
	 */
	Credits credits();

	/**
	 * The entrypoints of this mod.
	 * @see Entrypoints
	 */
	Entrypoints entrypoints();

	/**
	 * A formatted string that contains both the name and ID of this mod.
	 * This is useful for clearly showing a mod to users, ex. in the case of an error.
	 * <br>
	 * Example: {@code 'My Mod' (ID: 'mymod')}
	 */
	default String blame() {
		return "'" + this.name() + "' (ID: '" + this.id() + "')";
	}
}
