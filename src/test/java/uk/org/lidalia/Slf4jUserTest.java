package uk.org.lidalia;

import org.junit.After;
import org.junit.Test;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;

/**
 * Created on 25-6-2019.
 */
public class Slf4jUserTest {
	Slf4jUser slf4jUser = new Slf4jUser();
	TestLogger logger = TestLoggerFactory.getTestLogger(Slf4jUser.class);

	@Test
	public void aMethodThatLogsLogsAsExpected() {
		slf4jUser.aMethodThatLogs();

		assertThat(logger.getLoggingEvents(), is(Collections.singletonList(info("Hello World!"))));
	}

	@After
	public void clearLoggers() {
		TestLoggerFactory.clear();
	}
}
