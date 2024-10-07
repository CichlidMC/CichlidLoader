package io.github.cichlidmc.cichlid.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.function.Consumer;

import io.github.cichlidmc.cichlid.impl.logging.CichlidLogger;

public class CichlidAgent {
	public static void premain(String args, Instrumentation instrumentation) {
		try {
			CichlidLoaderImpl.load(instrumentation);
		} catch (Throwable t) {
			try {
				handleError(t);
			} catch (Throwable t2) {
				// uh oh. An agent throwing an error is pretty messy, do our best to avoid it
				t.addSuppressed(t2);
				handleCatastrophe(t);
			}

			// cancel the launch
			System.exit(1);
		}
	}

	private static void handleError(Throwable t) {
		// TODO: GUI
		CichlidLogger logger = CichlidLogger.get("Cichlid");
		logger.error("Something went wrong while loading Cichlid!");
		logger.throwable(t);
	}

	private static void handleCatastrophe(Throwable t) {
		CatastropheLogger logger = new CatastropheLogger(t::addSuppressed);

		logger.println("Something went *very* wrong while loading Cichlid!");
		logger.println("Please report this at https://github.com/CichlidMC/CichlidLoader/issues");
		logger.printThrowable(t);
	}

	private static class CatastropheLogger {
		private final PrintStream console;
		private final PrintStream log;

		private CatastropheLogger(Consumer<Throwable> suppressedErrors) {
			this.console = System.err;
			this.log = new PrintStream(getLogOutputStream(suppressedErrors));
		}

		private void println(String s) {
			this.console.println(s);
			this.log.println(s);
		}

		private void printThrowable(Throwable t) {
			t.printStackTrace(this.console);
			t.printStackTrace(this.log);
		}

		private static OutputStream getLogOutputStream(Consumer<Throwable> suppressedErrors) {
			try {
				Path file = CichlidPaths.ROOT.resolve("catastrophe.txt");
				Files.deleteIfExists(file);
				return Files.newOutputStream(file, StandardOpenOption.CREATE);
			} catch (Throwable t) {
				suppressedErrors.accept(t);
				// oh well, send to the void
				return new ByteArrayOutputStream();
			}
		}
	}
}
