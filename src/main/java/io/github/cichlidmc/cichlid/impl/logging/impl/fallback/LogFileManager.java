package io.github.cichlidmc.cichlid.impl.logging.impl.fallback;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.cichlidmc.cichlid.api.CichlidInternals;
import io.github.cichlidmc.cichlid.impl.CichlidPaths;

/**
 * Manages Cichlid's log files, deleting and renaming old ones.
 */
public class LogFileManager {
	public static final int LOGS_TO_KEEP = 3;
	public static final String LATEST = "log.txt";
	public static final String OLDER_FORMAT = "log-%d.txt";
	public static final String DELETE_MARKER = "delete";

	// log -> log-1
	// log-1 -> log-2
	// log-LOGS_TO_KEEP -> delete
	// iteration order matters, oldest to newest
	public static final Map<String, String> RENAMES = CichlidInternals.make(() -> {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(OLDER_FORMAT.formatted(LOGS_TO_KEEP), DELETE_MARKER);
		for (int i = LOGS_TO_KEEP; i > 0; i--) {
			map.put(OLDER_FORMAT.formatted(i - 1), OLDER_FORMAT.formatted(i));
		}
		map.put(LATEST, OLDER_FORMAT.formatted(1));
		return map;
	});

	private static final BufferedWriter output;

	static {
		try {
			// make sure dir exists
			Files.createDirectories(CichlidPaths.LOGS);
			// shift all files down 1
			for (Entry<String, String> entry : RENAMES.entrySet()) {
				Path from = CichlidPaths.LOGS.resolve(entry.getKey());
				Path to = CichlidPaths.LOGS.resolve(entry.getValue());

				if (Files.exists(from)) {
					if (entry.getValue().equals(DELETE_MARKER)) {
						Files.delete(from);
					} else {
						Files.move(from, to);
					}
				}
			}
			// open writer, this creates a new file
			Path latest = CichlidPaths.LOGS.resolve(LATEST);
			output = Files.newBufferedWriter(latest);
			// shutdown hook to close the file
			Runtime.getRuntime().addShutdownHook(new Thread(new FileCloser()));
		} catch (IOException | InvalidPathException e) {
			throw new RuntimeException("Error initializing Cichlid logging", e);
		}
	}

	public static void write(String message) {
		try {
			output.write(message);
			output.write('\n');
			output.flush();
		} catch (IOException e) {
			throw new RuntimeException("Error writing to log file", e);
		}
	}

	private static class FileCloser implements Runnable {
		@Override
		public void run() {
			try {
				output.close();
			} catch (IOException e) {
				// oh well.
			}
		}
	}
}
