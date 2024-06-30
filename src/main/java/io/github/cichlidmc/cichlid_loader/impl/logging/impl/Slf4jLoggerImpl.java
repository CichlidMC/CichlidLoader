package io.github.cichlidmc.cichlid_loader.impl.logging.impl;

import io.github.cichlidmc.cichlid_loader.impl.logging.CichlidLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggerImpl implements CichlidLogger {
	private final Logger logger;

	public Slf4jLoggerImpl(String name) {
		this.logger = LoggerFactory.getLogger(name);
	}

	@Override
	public void info(String message) {
		this.logger.info(message);
	}

	@Override
	public void warn(String message) {
		this.logger.warn(message);
	}

	@Override
	public void error(String message) {
		this.logger.error(message);
	}
}
