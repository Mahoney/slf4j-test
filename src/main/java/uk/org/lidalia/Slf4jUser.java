package uk.org.lidalia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 25-6-2019.
 */
public class Slf4jUser {
	private static final Logger logger = LoggerFactory.getLogger(Slf4jUser.class);

	public void aMethodThatLogs() {
		logger.info("Hello World!");
	}
}
