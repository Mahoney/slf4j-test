package uk.org.lidalia.slf4jtest;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.slf4j.Marker;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jext.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static uk.org.lidalia.slf4jtest.LoggingEvent.debug;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.slf4jtest.LoggingEvent.trace;
import static uk.org.lidalia.slf4jtest.LoggingEvent.warn;
import static uk.org.lidalia.slf4jext.Level.DEBUG;
import static uk.org.lidalia.slf4jext.Level.ERROR;
import static uk.org.lidalia.slf4jext.Level.INFO;
import static uk.org.lidalia.slf4jext.Level.TRACE;
import static uk.org.lidalia.slf4jext.Level.WARN;
import static uk.org.lidalia.slf4jext.Level.enablableValueSet;

public class TestLoggerTests {

    private static final String LOGGER_NAME = "uk.org";
    private final TestLogger testLogger = new TestLogger(LOGGER_NAME, TestLoggerFactory.getInstance());
    private final Marker marker = mock(Marker.class);
    private final String message = "message{}{}{}";
    private final Object arg1 = "arg1";
    private final Object arg2 = "arg2";
    private final Object[] args = new Object[] { arg1, arg2, "arg3" };
    private final Throwable throwable = new Throwable();

    private final Map<String, String> mdcValues = new HashMap<String, String>();

    @Before
    public void setUp() {
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
        TestLogger logger = new TestLogger(name, TestLoggerFactory.getInstance());
        assertEquals(name, logger.getName());
    }

    @Test
    public void clearRemovesEvents() {
        testLogger.debug("message1");
        testLogger.debug("message2");
        assertEquals(asList(debug(mdcValues, "message1"), debug(mdcValues, "message2")), testLogger.getLoggingEvents());
        testLogger.clear();
        assertEquals(Collections.emptyList(), testLogger.getLoggingEvents());
    }

    @Test
    public void clearResetsLevel() {
        testLogger.setEnabledLevels();
        testLogger.clear();
        assertEquals(newHashSet(TRACE, DEBUG, INFO, WARN, ERROR), testLogger.getEnabledLevels());
    }

    @Test
    public void traceEnabled() {
        assertEnabledReturnsCorrectly(TRACE);
    }

    @Test
    public void traceMessage() {
        testLogger.trace(message);

        assertEquals(asList(trace(mdcValues, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageOneArg() {
        testLogger.trace(message, arg1);

        assertEquals(asList(trace(mdcValues, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageTwoArgs() {
        testLogger.trace(message, arg1, arg2);

        assertEquals(asList(trace(mdcValues, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageManyArgs() {
        testLogger.trace(message, args);

        assertEquals(asList(trace(mdcValues, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMessageThrowable() {
        testLogger.trace(message, throwable);

        assertEquals(asList(trace(mdcValues, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceEnabledMarker() {
        assertEnabledReturnsCorrectly(TRACE, marker);
    }

    @Test
    public void traceMarkerMessage() {
        testLogger.trace(marker, message);

        assertEquals(asList(trace(mdcValues, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageOneArg() {
        testLogger.trace(marker, message, arg1);

        assertEquals(asList(trace(mdcValues, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageTwoArgs() {
        testLogger.trace(marker, message, arg1, arg2);

        assertEquals(asList(trace(mdcValues, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageManyArgs() {
        testLogger.trace(marker, message, args);

        assertEquals(asList(trace(mdcValues, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void traceMarkerMessageThrowable() {
        testLogger.trace(marker, message, throwable);

        assertEquals(asList(trace(mdcValues, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugEnabled() {
        assertEnabledReturnsCorrectly(DEBUG);
    }

    @Test
    public void debugMessage() {
        testLogger.debug(message);

        assertEquals(asList(debug(mdcValues, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageOneArg() {
        testLogger.debug(message, arg1);

        assertEquals(asList(debug(mdcValues, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageTwoArgs() {
        testLogger.debug(message, arg1, arg2);

        assertEquals(asList(debug(mdcValues, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageManyArgs() {
        testLogger.debug(message, args);

        assertEquals(asList(debug(mdcValues, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMessageThrowable() {
        testLogger.debug(message, throwable);

        assertEquals(asList(debug(mdcValues, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugEnabledMarker() {
        assertEnabledReturnsCorrectly(DEBUG, marker);
    }

    @Test
    public void debugMarkerMessage() {
        testLogger.debug(marker, message);

        assertEquals(asList(debug(mdcValues, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageOneArg() {
        testLogger.debug(marker, message, arg1);

        assertEquals(asList(debug(mdcValues, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageTwoArgs() {
        testLogger.debug(marker, message, arg1, arg2);

        assertEquals(asList(debug(mdcValues, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageManyArgs() {
        testLogger.debug(marker, message, args);

        assertEquals(asList(debug(mdcValues, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void debugMarkerMessageThrowable() {
        testLogger.debug(marker, message, throwable);

        assertEquals(asList(debug(mdcValues, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoEnabled() {
        assertEnabledReturnsCorrectly(INFO);
    }

    @Test
    public void infoMessage() {
        testLogger.info(message);

        assertEquals(asList(info(mdcValues, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageOneArg() {
        testLogger.info(message, arg1);

        assertEquals(asList(info(mdcValues, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageTwoArgs() {
        testLogger.info(message, arg1, arg2);

        assertEquals(asList(info(mdcValues, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageManyArgs() {
        testLogger.info(message, args);

        assertEquals(asList(info(mdcValues, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMessageThrowable() {
        testLogger.info(message, throwable);

        assertEquals(asList(info(mdcValues, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoEnabledMarker() {
        assertEnabledReturnsCorrectly(INFO, marker);
    }

    @Test
    public void infoMarkerMessage() {
        testLogger.info(marker, message);

        assertEquals(asList(info(mdcValues, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageOneArg() {
        testLogger.info(marker, message, arg1);

        assertEquals(asList(info(mdcValues, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageTwoArgs() {
        testLogger.info(marker, message, arg1, arg2);

        assertEquals(asList(info(mdcValues, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageManyArgs() {
        testLogger.info(marker, message, args);

        assertEquals(asList(info(mdcValues, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void infoMarkerMessageThrowable() {
        testLogger.info(marker, message, throwable);

        assertEquals(asList(info(mdcValues, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnEnabled() {
        assertEnabledReturnsCorrectly(WARN);
    }

    @Test
    public void warnMessage() {
        testLogger.warn(message);

        assertEquals(asList(warn(mdcValues, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageOneArg() {
        testLogger.warn(message, arg1);

        assertEquals(asList(warn(mdcValues, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageTwoArgs() {
        testLogger.warn(message, arg1, arg2);

        assertEquals(asList(warn(mdcValues, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageManyArgs() {
        testLogger.warn(message, args);

        assertEquals(asList(warn(mdcValues, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMessageThrowable() {
        testLogger.warn(message, throwable);

        assertEquals(asList(warn(mdcValues, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnEnabledMarker() {
        assertEnabledReturnsCorrectly(WARN, marker);
    }

    @Test
    public void warnMarkerMessage() {
        testLogger.warn(marker, message);

        assertEquals(asList(warn(mdcValues, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageOneArg() {
        testLogger.warn(marker, message, arg1);

        assertEquals(asList(warn(mdcValues, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageTwoArgs() {
        testLogger.warn(marker, message, arg1, arg2);

        assertEquals(asList(warn(mdcValues, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageManyArgs() {
        testLogger.warn(marker, message, args);

        assertEquals(asList(warn(mdcValues, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void warnMarkerMessageThrowable() {
        testLogger.warn(marker, message, throwable);

        assertEquals(asList(warn(mdcValues, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorEnabled() {
        assertEnabledReturnsCorrectly(ERROR);
    }

    @Test
    public void errorMessage() {
        testLogger.error(message);

        assertEquals(asList(error(mdcValues, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageOneArg() {
        testLogger.error(message, arg1);

        assertEquals(asList(error(mdcValues, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageTwoArgs() {
        testLogger.error(message, arg1, arg2);

        assertEquals(asList(error(mdcValues, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageManyArgs() {
        testLogger.error(message, args);

        assertEquals(asList(error(mdcValues, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMessageThrowable() {
        testLogger.error(message, throwable);

        assertEquals(asList(error(mdcValues, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorEnabledMarker() {
        assertEnabledReturnsCorrectly(ERROR, marker);
    }

    @Test
    public void errorMarkerMessage() {
        testLogger.error(marker, message);

        assertEquals(asList(error(mdcValues, marker, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageOneArg() {
        testLogger.error(marker, message, arg1);

        assertEquals(asList(error(mdcValues, marker, message, arg1)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageTwoArgs() {
        testLogger.error(marker, message, arg1, arg2);

        assertEquals(asList(error(mdcValues, marker, message, arg1, arg2)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageManyArgs() {
        testLogger.error(marker, message, args);

        assertEquals(asList(error(mdcValues, marker, message, args)), testLogger.getLoggingEvents());
    }

    @Test
    public void errorMarkerMessageThrowable() {
        testLogger.error(marker, message, throwable);

        assertEquals(asList(error(mdcValues, marker, throwable, message)), testLogger.getLoggingEvents());
    }

    @Test
    public void loggerSetToOff() {
        logsIfEnabled();
    }

    @Test
    public void loggerSetToError() {
        logsIfEnabled(ERROR);
    }

    @Test
    public void loggerSetToWarn() {
        logsIfEnabled(ERROR, WARN);
    }

    @Test
    public void loggerSetToInfo() {
        logsIfEnabled(ERROR, WARN, INFO);
    }

    @Test
    public void loggerSetToDebug() {
        logsIfEnabled(ERROR, WARN, INFO, DEBUG);
    }

    @Test
    public void loggerSetToTrace() {
        logsIfEnabled(ERROR, WARN, INFO, DEBUG, TRACE);
    }

    @Test
    public void getLoggingEventsReturnsCopyNotView() {
        testLogger.debug(message);
        List<LoggingEvent> loggingEvents = testLogger.getLoggingEvents();
        testLogger.info(message);

        assertEquals(asList(debug(mdcValues, message)), loggingEvents);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getLoggingEventsReturnsUnmodifiableList() {
        List<LoggingEvent> loggingEvents = testLogger.getLoggingEvents();
        loggingEvents.add(debug("hello"));
    }

    @Test
    public void getLoggingEventsOnlyReturnsEventsLoggedInThisThread() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.info(message);
            }
        });
        t.start();
        t.join();
        assertEquals(Collections.emptyList(), testLogger.getLoggingEvents());
    }

    @Test
    public void getAllLoggingEventsReturnsEventsLoggedInAllThreads() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.info(message);
            }
        });
        t.start();
        t.join();
        testLogger.info(message);
        assertEquals(asList(info(mdcValues, message), info(mdcValues, message)), testLogger.getAllLoggingEvents());
    }

    @Test
    public void clearOnlyClearsEventsLoggedInThisThread() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.info(message);
            }
        });
        t.start();
        t.join();
        testLogger.clear();
        assertEquals(asList(info(mdcValues, message)), testLogger.getAllLoggingEvents());
    }

    @Test
    public void clearAllClearsEventsLoggedInAllThreads() throws InterruptedException {
        testLogger.info(message);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.info(message);
                testLogger.clearAll();
            }
        });
        t.start();
        t.join();
        assertEquals(emptyList(), testLogger.getAllLoggingEvents());
        assertEquals(emptyList(), testLogger.getLoggingEvents());
    }

    @Test
    public void setEnabledLevelOnlyChangesLevelForCurrentThread() throws Exception {
        final AtomicReference<ImmutableSet<Level>> inThreadEnabledLevels = new AtomicReference<ImmutableSet<Level>>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.setEnabledLevels(Level.WARN, Level.ERROR);
                inThreadEnabledLevels.set(testLogger.getEnabledLevels());
            }
        });
        t.start();
        t.join();
        assertEquals(ImmutableSet.of(Level.WARN, Level.ERROR), inThreadEnabledLevels.get());
        assertEquals(Level.enablableValueSet(), testLogger.getEnabledLevels());
    }

    @Test
    public void clearOnlyChangesLevelForCurrentThread() throws Exception {
        testLogger.setEnabledLevels(Level.WARN, Level.ERROR);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.clear();
            }
        });
        t.start();
        t.join();
        assertEquals(ImmutableSet.of(Level.WARN, Level.ERROR), testLogger.getEnabledLevels());
    }

    @Test
    public void setEnabledLevelsForAllThreads() throws Exception {
        final AtomicReference<ImmutableSet<Level>> inThreadEnabledLevels = new AtomicReference<ImmutableSet<Level>>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.setEnabledLevelsForAllThreads(Level.WARN, Level.ERROR);
                inThreadEnabledLevels.set(testLogger.getEnabledLevels());
            }
        });
        t.start();
        t.join();
        assertEquals(ImmutableSet.of(Level.WARN, Level.ERROR), inThreadEnabledLevels.get());
        assertEquals(ImmutableSet.of(Level.WARN, Level.ERROR), testLogger.getEnabledLevels());
    }

    @Test
    public void clearAllChangesAllLevels() throws Exception {
        testLogger.setEnabledLevels(Level.WARN, Level.ERROR);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                testLogger.clearAll();
            }
        });
        t.start();
        t.join();
        assertEquals(Level.enablableValueSet(), testLogger.getEnabledLevels());
    }

    private void logsIfEnabled(Level... shouldLog) {
        testLogger.setEnabledLevels(shouldLog);
        testLogger.error(message);
        testLogger.warn(message);
        testLogger.info(message);
        testLogger.debug(message);
        testLogger.trace(message);

        List<LoggingEvent> expectedEvents = Lists.transform(asList(shouldLog), new Function<Level, LoggingEvent>() {
            @Override
            public LoggingEvent apply(Level level) {
                return new LoggingEvent(level, mdcValues, message);
            }
        });

        assertEquals(expectedEvents, testLogger.getLoggingEvents());
        testLogger.clear();
    }

    private void assertEnabledReturnsCorrectly(Level levelToTest) {
        testLogger.setEnabledLevels(levelToTest);
        assertTrue("Logger level set to " + levelToTest + " means " + levelToTest + " should be enabled",
                new Logger(testLogger).isEnabled(levelToTest));

        Set<Level> disabledLevels = difference(enablableValueSet(), newHashSet(levelToTest));
        for (Level disabledLevel: disabledLevels) {
            assertFalse("Logger level set to " + levelToTest + " means " + levelToTest + " should be disabled",
                    new Logger(testLogger).isEnabled(disabledLevel));
        }
    }

    private void assertEnabledReturnsCorrectly(Level levelToTest, Marker marker) {
        testLogger.setEnabledLevels(levelToTest);
        assertTrue("Logger level set to " + levelToTest + " means " + levelToTest + " should be enabled",
                new Logger(testLogger).isEnabled(levelToTest, marker));

        Set<Level> disabledLevels = difference(enablableValueSet(), newHashSet(levelToTest));
        for (Level disabledLevel: disabledLevels) {
            assertFalse("Logger level set to " + levelToTest + " means " + levelToTest + " should be disabled",
                    new Logger(testLogger).isEnabled(disabledLevel, marker));
        }
    }
}
