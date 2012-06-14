package uk.org.lidalia.slf4jtest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.slf4j.Marker;

import uk.org.lidalia.slf4jutils.Level;
import uk.org.lidalia.slf4jutils.RichLogger;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static uk.org.lidalia.slf4jutils.Level.DEBUG;
import static uk.org.lidalia.slf4jutils.Level.ERROR;
import static uk.org.lidalia.slf4jutils.Level.INFO;
import static uk.org.lidalia.slf4jutils.Level.OFF;
import static uk.org.lidalia.slf4jutils.Level.TRACE;
import static uk.org.lidalia.slf4jutils.Level.WARN;

public class TestLoggerTests {

    private static final String LOGGER_NAME = "uk.org";
    private final TestLogger testLogger = new TestLogger(LOGGER_NAME, TestLoggerFactory.INSTANCE);
    private final Marker marker = mock(Marker.class);
    private final String message = "message";
    private final Object arg1 = "arg1";
    private final Object arg2 = "arg2";
    private final Object[] args = new Object[] { arg1, arg2, "arg3" };
    private final Throwable throwable = new Throwable();

    private final Map<String, String> mdcValues = new HashMap<String, String>();

    @Before
    public void setUp() {
        testLogger.setLevel(Level.TRACE);
        mdcValues.put("key1", "value1");
        mdcValues.put("key2", "value2");
        MDC.setContextMap(mdcValues);
    }

    @After
    public void tearDown() {
        MDC.clear();
    }

    @Test
    public void name() {
        String name = RandomStringUtils.random(10);
        TestLogger logger = new TestLogger(name, TestLoggerFactory.INSTANCE);
        assertEquals(name, logger.getName());
    }

    @Test
    public void clear() {
        testLogger.debug("message1");
        testLogger.debug("message2");
        assertEquals(asList(loggingEvent(DEBUG, "message1"), loggingEvent(DEBUG, "message2")), testLogger.getLoggingEvents());
        testLogger.clear();
        assertEquals(Collections.emptyList(), testLogger.getLoggingEvents());
    }

    @Test
    public void traceEnabled() {
        assertEnabledReturnsCorrectly(TRACE, asList(TRACE), asList(DEBUG, INFO, WARN, ERROR, OFF));
    }

    @Test
    public void traceMessage() {
        testLogger.trace(message);

        assertEquals(asList(loggingEvent(TRACE, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageOneArg() {
        testLogger.trace(message, arg1);

        assertEquals(asList(loggingEvent(TRACE, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageTwoArgs() {
        testLogger.trace(message, arg1, arg2);

        assertEquals(asList(loggingEvent(TRACE, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageManyArgs() {
        testLogger.trace(message, args);

        assertEquals(asList(loggingEvent(TRACE, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageThrowable() {
        testLogger.trace(message, throwable);

        assertEquals(asList(loggingEvent(TRACE, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceEnabledMarker() {
        assertEnabledReturnsCorrectly(TRACE, marker, asList(TRACE), asList(DEBUG, INFO, WARN, ERROR, OFF));
    }

    @Test
    public void traceMarkerMessage() {
        testLogger.trace(marker, message);

        assertEquals(asList(loggingEvent(TRACE, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageOneArg() {
        testLogger.trace(marker, message, arg1);

        assertEquals(asList(loggingEvent(TRACE, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageTwoArgs() {
        testLogger.trace(marker, message, arg1, arg2);

        assertEquals(asList(loggingEvent(TRACE, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageManyArgs() {
        testLogger.trace(marker, message, args);

        assertEquals(asList(loggingEvent(TRACE, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageThrowable() {
        testLogger.trace(marker, message, throwable);

        assertEquals(asList(loggingEvent(TRACE, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugEnabled() {
        assertEnabledReturnsCorrectly(DEBUG, asList(TRACE, DEBUG), asList(INFO, WARN, ERROR, OFF));
    }

    @Test
    public void debugMessage() {
        testLogger.debug(message);

        assertEquals(asList(loggingEvent(DEBUG, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageOneArg() {
        testLogger.debug(message, arg1);

        assertEquals(asList(loggingEvent(DEBUG, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageTwoArgs() {
        testLogger.debug(message, arg1, arg2);

        assertEquals(asList(loggingEvent(DEBUG, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageManyArgs() {
        testLogger.debug(message, args);

        assertEquals(asList(loggingEvent(DEBUG, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageThrowable() {
        testLogger.debug(message, throwable);

        assertEquals(asList(loggingEvent(DEBUG, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugEnabledMarker() {
        assertEnabledReturnsCorrectly(DEBUG, marker, asList(TRACE, DEBUG), asList(INFO, WARN, ERROR, OFF));
    }

    @Test
    public void debugMarkerMessage() {
        testLogger.debug(marker, message);

        assertEquals(asList(loggingEvent(DEBUG, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageOneArg() {
        testLogger.debug(marker, message, arg1);

        assertEquals(asList(loggingEvent(DEBUG, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageTwoArgs() {
        testLogger.debug(marker, message, arg1, arg2);

        assertEquals(asList(loggingEvent(DEBUG, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageManyArgs() {
        testLogger.debug(marker, message, args);

        assertEquals(asList(loggingEvent(DEBUG, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageThrowable() {
        testLogger.debug(marker, message, throwable);

        assertEquals(asList(loggingEvent(DEBUG, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoEnabled() {
        assertEnabledReturnsCorrectly(INFO, asList(TRACE, DEBUG, INFO), asList(WARN, ERROR, OFF));
    }

    @Test
    public void infoMessage() {
        testLogger.info(message);

        assertEquals(asList(loggingEvent(INFO, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageOneArg() {
        testLogger.info(message, arg1);

        assertEquals(asList(loggingEvent(INFO, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageTwoArgs() {
        testLogger.info(message, arg1, arg2);

        assertEquals(asList(loggingEvent(INFO, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageManyArgs() {
        testLogger.info(message, args);

        assertEquals(asList(loggingEvent(INFO, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageThrowable() {
        testLogger.info(message, throwable);

        assertEquals(asList(loggingEvent(INFO, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoEnabledMarker() {
        assertEnabledReturnsCorrectly(INFO, marker, asList(TRACE, DEBUG, INFO), asList(WARN, ERROR, OFF));
    }

    @Test
    public void infoMarkerMessage() {
        testLogger.info(marker, message);

        assertEquals(asList(loggingEvent(INFO, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageOneArg() {
        testLogger.info(marker, message, arg1);

        assertEquals(asList(loggingEvent(INFO, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageTwoArgs() {
        testLogger.info(marker, message, arg1, arg2);

        assertEquals(asList(loggingEvent(INFO, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageManyArgs() {
        testLogger.info(marker, message, args);

        assertEquals(asList(loggingEvent(INFO, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageThrowable() {
        testLogger.info(marker, message, throwable);

        assertEquals(asList(loggingEvent(INFO, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnEnabled() {
        assertEnabledReturnsCorrectly(WARN, asList(TRACE, DEBUG, INFO, WARN), asList(ERROR, OFF));
    }

    @Test
    public void warnMessage() {
        testLogger.warn(message);

        assertEquals(asList(loggingEvent(WARN, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageOneArg() {
        testLogger.warn(message, arg1);

        assertEquals(asList(loggingEvent(WARN, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageTwoArgs() {
        testLogger.warn(message, arg1, arg2);

        assertEquals(asList(loggingEvent(WARN, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageManyArgs() {
        testLogger.warn(message, args);

        assertEquals(asList(loggingEvent(WARN, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageThrowable() {
        testLogger.warn(message, throwable);

        assertEquals(asList(loggingEvent(WARN, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnEnabledMarker() {
        assertEnabledReturnsCorrectly(WARN, marker, asList(TRACE, DEBUG, INFO, WARN), asList(ERROR, OFF));
    }

    @Test
    public void warnMarkerMessage() {
        testLogger.warn(marker, message);

        assertEquals(asList(loggingEvent(WARN, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageOneArg() {
        testLogger.warn(marker, message, arg1);

        assertEquals(asList(loggingEvent(WARN, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageTwoArgs() {
        testLogger.warn(marker, message, arg1, arg2);

        assertEquals(asList(loggingEvent(WARN, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageManyArgs() {
        testLogger.warn(marker, message, args);

        assertEquals(asList(loggingEvent(WARN, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageThrowable() {
        testLogger.warn(marker, message, throwable);

        assertEquals(asList(loggingEvent(WARN, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorEnabled() {
        assertEnabledReturnsCorrectly(ERROR, asList(TRACE, DEBUG, INFO, WARN, ERROR), asList(OFF));
    }

    @Test
    public void errorMessage() {
        testLogger.error(message);

        assertEquals(asList(loggingEvent(ERROR, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageOneArg() {
        testLogger.error(message, arg1);

        assertEquals(asList(loggingEvent(ERROR, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageTwoArgs() {
        testLogger.error(message, arg1, arg2);

        assertEquals(asList(loggingEvent(ERROR, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageManyArgs() {
        testLogger.error(message, args);

        assertEquals(asList(loggingEvent(ERROR, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageThrowable() {
        testLogger.error(message, throwable);

        assertEquals(asList(loggingEvent(ERROR, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorEnabledMarker() {
        assertEnabledReturnsCorrectly(ERROR, marker, asList(TRACE, DEBUG, INFO, WARN, ERROR), asList(OFF));
    }

    @Test
    public void errorMarkerMessage() {
        testLogger.error(marker, message);

        assertEquals(asList(loggingEvent(ERROR, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageOneArg() {
        testLogger.error(marker, message, arg1);

        assertEquals(asList(loggingEvent(ERROR, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageTwoArgs() {
        testLogger.error(marker, message, arg1, arg2);

        assertEquals(asList(loggingEvent(ERROR, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageManyArgs() {
        testLogger.error(marker, message, args);

        assertEquals(asList(loggingEvent(ERROR, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageThrowable() {
        testLogger.error(marker, message, throwable);

        assertEquals(asList(loggingEvent(ERROR, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    private void assertEnabledReturnsCorrectly(Level levelToTest, List<Level> enabledLevels, List<Level> disabledLevels) {
        for (Level enabledLevel: enabledLevels) {
            testLogger.setLevel(enabledLevel);
            assertTrue("Logger level set to " + testLogger.getLevel() + " means " + levelToTest + " should be enabled",
                    new RichLogger(testLogger).isEnabled(levelToTest));
        }
        for (Level disabledLevel: disabledLevels) {
            testLogger.setLevel(disabledLevel);
            assertFalse("Logger level set to " + testLogger.getLevel() + " means " + levelToTest + " should be disabled",
                    new RichLogger(testLogger).isEnabled(levelToTest));
        }
    }

    private void assertEnabledReturnsCorrectly(Level levelToTest, Marker marker, List<Level> enabledLevels, List<Level> disabledLevels) {
        for (Level enabledLevel: enabledLevels) {
            testLogger.setLevel(enabledLevel);
            assertTrue("Logger level set to " + testLogger.getLevel() + " means " + levelToTest + " should be enabled",
                    new RichLogger(testLogger).isEnabled(levelToTest, marker));
        }
        for (Level disabledLevel: disabledLevels) {
            testLogger.setLevel(disabledLevel);
            assertFalse("Logger level set to " + testLogger.getLevel() + " means " + levelToTest + " should be disabled",
                    new RichLogger(testLogger).isEnabled(levelToTest, marker));
        }
    }

    private LoggingEvent loggingEvent(Level level, String message, Object... arguments) {
        return new LoggingEvent(level, mdcValues, message, arguments);
    }

    private LoggingEvent loggingEvent(Level level, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(level, mdcValues, throwable, message, arguments);
    }

    private LoggingEvent loggingEvent(Level level, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(level, mdcValues, marker, message, arguments);
    }

    private LoggingEvent loggingEvent(Level level, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(level, mdcValues, marker, throwable, message, arguments);
    }
}
