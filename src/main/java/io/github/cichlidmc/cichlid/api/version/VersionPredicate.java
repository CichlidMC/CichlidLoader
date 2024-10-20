package io.github.cichlidmc.cichlid.api.version;

import java.util.function.Predicate;

import io.github.cichlidmc.cichlid.impl.version.parser.VersionPredicateParser;

/**
 * A predicate for {@link Version}s.
 */
public interface VersionPredicate extends Predicate<Version> {
	default boolean test(String version) {
		return this.test(Version.parse(version));
	}

	/**
	 * Parse a version predicate from the given string. Syntax is as follows:
	 * <ul>
	 *     <li>Version operators: {@code <=, >=, ==, !=, <, >}</li>
	 *     <li>Boolean operators: {@code &&, ||}, same precedence as Java</li>
	 *     <li>Parentheses: May be used to explicitly group boolean operations</li>
	 *     <li>Versions: Any string after a version operator until parentheses or a boolean op are hit.</li>
	 *     <li>Whitespace: All whitespace is ignored.</li>
	 * </ul>
	 * Examples:
	 * <ul>
	 *     <li>{@code >=1.21.1}</li>
	 *     <li>{@code >=1.20 && <= 1.21}</li>
	 *     <li>{@code (>= 1.16 && <1.17) && !=1.16.3}</li>
	 *     <li>{@code >=24w10a && <24w12a}</li>
	 *     <li>{@code (>=0.5.1.a && <0.5.1.d) || >=0.5.1.f}</li>
	 *     <li>{@code ==0.6.0-beta.2}</li>
	 * </ul>
	 * @throws VersionPredicateSyntaxException if the predicate is malformed
	 */
	static VersionPredicate parse(String string) throws VersionPredicateSyntaxException {
		return VersionPredicateParser.parse(string);
	}
}
