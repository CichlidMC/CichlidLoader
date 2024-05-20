package io.github.tropheusj.cichlid.impl.logging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import io.github.tropheusj.cichlid.api.CichlidUtils;
import io.github.tropheusj.cichlid.impl.CichlidPaths;

/**
 *
 */
public class LogFileManager {
	public static final int LOGS_TO_KEEP = 3;
	public static final String LATEST = "latest.txt";
	public static final String OLDER_FORMAT = "latest-%d.txt";
	public static final String DELETE_MARKER = "delete";

	// latest -> latest-1
	// latest-1 -> latest-2
	// latest-LOGS_TO_KEEP -> delete
	public static final Map<String, String> RENAMES = CichlidUtils.make(() -> {
		Map<String, String> map = new HashMap<>();
		map.put(LATEST, OLDER_FORMAT.formatted(1));
		for (int i = 1; i < LOGS_TO_KEEP; i++) {
			map.put(OLDER_FORMAT.formatted(i), OLDER_FORMAT.formatted(i + 1));
		}
		map.put(OLDER_FORMAT.formatted(LOGS_TO_KEEP), DELETE_MARKER);
		return map;
	});

	private static final BufferedWriter output;

	static {
		try {
			// make sure dir exists
			Path logs = CichlidPaths.INSTANCE.logs();
			Files.createDirectories(logs);
			// shift all files down 1
			try (Stream<Path> stream = Files.list(logs)) {
				stream.forEach(file -> {
					String name = file.getFileName().toString();
					String renamed = RENAMES.get(name);
					if (renamed == null)
						return;

					try {
						if (renamed.equals(DELETE_MARKER)) {
							Files.delete(file);
						} else {
							Path target = file.resolveSibling(renamed);
							Files.move(file, target);
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
			}
			// open writer, this creates a new file
			Path latest = logs.resolve(LATEST);
			output = Files.newBufferedWriter(latest);
			// shutdown hook to close the file
			Runtime.getRuntime().addShutdownHook(new Thread(new FileCloser()));

			// set up the game log
			if (GAME_LOG != null) {
				// old log, copy to temp file
				if (Files.exists(GAME_LOG)) {
					Files.move(GAME_LOG, TEMP_GAME_LOG);
				}
			} else {
				// not provided, don't write to it
				gameOutput = new BufferedWriter(new StringWriter());
				gameOutput.close();
			}
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
