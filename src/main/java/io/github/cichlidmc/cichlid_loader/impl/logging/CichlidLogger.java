package io.github.cichlidmc.cichlid_loader.impl.logging;

import java.util.function.Function;

import io.github.cichlidmc.cichlid_loader.api.CichlidUtils;
import io.github.cichlidmc.cichlid_loader.impl.logging.impl.Log4jLoggerImpl;
import io.github.cichlidmc.cichlid_loader.impl.logging.impl.fallback.FallbackLoggerImpl;

public interface CichlidLogger {
	Function<String, CichlidLogger> FACTORY = CichlidUtils.make(() -> {
		try {
			return Log4jLoggerImpl::new;
		} catch (Throwable t) {
			FallbackLoggerImpl logger = new FallbackLoggerImpl(CichlidLogger.class.getSimpleName());
			logger.info("Log4j not present, using fallback logger");
			return FallbackLoggerImpl::new;
		}
	});

	void info(String message);
	void warn(String message);
	void error(String message);

	static CichlidLogger get(Class<?> clazz) {
		return get(clazz.getSimpleName());
	}

	static CichlidLogger get(String name) {
		return FACTORY.apply(name);
	}
}
