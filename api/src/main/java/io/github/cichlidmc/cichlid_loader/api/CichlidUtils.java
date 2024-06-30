package io.github.cichlidmc.cichlid_loader.api;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Supplier;

public class CichlidUtils {
	/**
	 * Return the value of the given supplier.
	 * Useful for initializing static fields.
	 */
	public static <T> T make(Supplier<T> supplier) {
		return supplier.get();
	}

	/**
	 * Create a FileVisitor that will invoke the given consumer for every file.
	 */
	public static FileVisitor<Path> fileWalker(FileConsumer consumer) {
		return new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				consumer.accept(file);
				return FileVisitResult.CONTINUE;
			}
		};
	}

	public interface FileConsumer {
		void accept(Path file) throws IOException;
	}
}
