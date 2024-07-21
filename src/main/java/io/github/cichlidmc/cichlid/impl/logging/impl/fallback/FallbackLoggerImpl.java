package io.github.cichlidmc.cichlid.impl.logging.impl.fallback;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.cichlidmc.cichlid.impl.logging.CichlidLogger;

public record FallbackLoggerImpl(String name) implements CichlidLogger {
	private static final String format = "[%s] [%s] [%s] [%s]: %s";
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void info(String message) {
		this.write("INFO", message);
	}

	@Override
	public void warn(String message) {
		this.write("WARN", message);
	}

	@Override
	public void error(String message) {
		this.write("ERROR", message);
	}

	@Override
	public void throwable(Throwable t) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(data);
		t.printStackTrace(stream);
		String string = data.toString();
		for (String line : string.split(System.lineSeparator())) {
			this.error(line);
		}
	}

	private void write(String level, String message) {
		String time = timeFormat.format(new Date());
		String thread = Thread.currentThread().getName();
		String formatted = format.formatted(time, thread, this.name, level, message);
		System.out.println(formatted);
//		LogFileManager.write(formatted);
	}
}
