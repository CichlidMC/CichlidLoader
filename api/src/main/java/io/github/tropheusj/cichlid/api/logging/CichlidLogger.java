package io.github.tropheusj.cichlid.api.logging;

import java.lang.reflect.Constructor;

import io.github.tropheusj.cichlid.api.entrypoint.PreLaunchEntrypoint;

/**
 * Simple logger that logs to Cichlid's log file.
 * <br>
 * You should probably only use this in plugins and {@link PreLaunchEntrypoint},
 * as this will not be visible in primary game logs.
 */
public interface CichlidLogger {
	/**
	 * Logged message format. Example: {@code [13:30:59] [Worker-Thread-19] [INFO]: <message>}
	 */
	String FORMAT = "[%s] [%s] [%s]: [%s]";

	void info(String message);
	void warn(String message);
	void error(String message);
	void fatal(String message);

	static CichlidLogger get(Class<?> clazz) {
		return get(clazz.getSimpleName());
	}

	static CichlidLogger get(String name) {
		try {
			Class<?> implClass = Class.forName("io.github.tropheusj.cichlid.impl.logging.CichlidLoggerImpl");
			Constructor<?> constructor = implClass.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			return (CichlidLogger) constructor.newInstance(name);
		} catch (Exception e) {
			throw new RuntimeException("Error creating a CichlidLogger", e);
		}
	}
}
