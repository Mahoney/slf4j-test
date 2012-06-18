package uk.org.lidalia.slf4jtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static uk.org.lidalia.slf4jtest.LoggingEvent.debug;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.slf4jtest.LoggingEvent.trace;
import static uk.org.lidalia.slf4jtest.TestLoggerFactory.getInstance;
import static uk.org.lidalia.slf4jutils.Level.WARN;

public class TestLoggerFactoryTests {

    @Test
    public void getLoggerDifferentNames() throws Exception {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name2");

        assertNotSame(logger1, logger2);
    }

    @Test
    public void getLoggerSameNames() throws Exception {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name1");

        assertSame(logger1, logger2);
    }

    @Test
    public void staticGetTestLoggerStringReturnsSame() throws Exception {
        TestLogger logger1 = TestLoggerFactory.getTestLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name1");

        assertSame(logger1, logger2);
    }

    @Test
    public void staticGetTestLoggerClassReturnsSame() throws Exception {
        TestLogger logger1 = TestLoggerFactory.getTestLogger(String.class);
        TestLogger logger2 = getInstance().getLogger("java.lang.String");

        assertSame(logger1, logger2);
    }

    @Test
    public void clear() throws Exception {
        TestLogger logger1 = getInstance().getLogger("name1");
        logger1.trace("hello");
        assertEquals(1, logger1.getLoggingEvents().size());
        TestLogger logger2 = getInstance().getLogger("name2");
        logger2.trace("world");
        assertEquals(1, logger2.getLoggingEvents().size());

        TestLoggerFactory.clear();

        assertEquals(0, logger1.getLoggingEvents().size());
        assertEquals(0, logger2.getLoggingEvents().size());
        assertEquals(0, TestLoggerFactory.getLoggingEvents().size());
    }

    @Test
    public void getAllLoggingEvents() throws Exception {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name2");
        logger1.trace("hello");
        logger2.trace("world");
        logger1.trace("here");
        logger2.trace("I am");

        assertEquals(asList(
                trace("hello"),
                trace("world"),
                trace("here"),
                trace("I am")),
                TestLoggerFactory.getLoggingEvents());
    }

    @Test
    public void getAllLoggingEventsDoesNotAddToMultipleLoggers() throws Exception {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name2");
        logger1.trace("hello");
        logger2.trace("world");

        assertEquals(asList(
                trace("hello")),
                logger1.getLoggingEvents());
        assertEquals(asList(
                trace("world")),
                logger2.getLoggingEvents());
    }

    @Test
    public void getAllLoggingEventsDoesNotGetEventsForLoggersNotEnabled() {
        TestLogger logger = getInstance().getLogger("name1");
        logger.setEnabledLevels(WARN);
        logger.info("hello");

        assertEquals(emptyList(), TestLoggerFactory.getLoggingEvents());
    }

    @Test
    public void getAllTestLoggers() {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLogger logger2 = getInstance().getLogger("name2");
        Map<String, TestLogger> expected = new HashMap<String, TestLogger>();
        expected.put("name1", logger1);
        expected.put("name2", logger2);
        assertEquals(expected, TestLoggerFactory.getAllTestLoggers());
    }

    @Test
    public void clearDoesNotRemoveLoggers() {
        TestLogger logger1 = getInstance().getLogger("name1");
        TestLoggerFactory.clear();

        Map<String, TestLogger> expected = new HashMap<String, TestLogger>();
        expected.put("name1", logger1);
        assertEquals(expected, TestLoggerFactory.getAllTestLoggers());
    }

    @Test
    public void resetRemovesAllLoggers() {
        getInstance().getLogger("name1");

        TestLoggerFactory.reset();

        assertEquals(emptyMap(), TestLoggerFactory.getAllTestLoggers());
    }

    @Test
    public void resetRemovesAllLoggingEvents() {
        getInstance().getLogger("name1").info("hello");

        TestLoggerFactory.reset();

        assertEquals(emptyList(), TestLoggerFactory.getLoggingEvents());
    }

    @Test
    public void getLoggingEventsReturnsCopyNotView() {
        getInstance().getLogger("name1").debug("hello");
        List<LoggingEvent> loggingEvents = TestLoggerFactory.getLoggingEvents();
        getInstance().getLogger("name1").info("world");
        assertEquals(asList(debug("hello")), loggingEvents);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getLoggingEventsReturnsUnmodifiableList() {
        List<LoggingEvent> loggingEvents = TestLoggerFactory.getLoggingEvents();
        loggingEvents.add(debug("hello"));
    }

    @Test
    public void getAllLoggersReturnsCopyNotView() {
        TestLogger logger1 = getInstance().getLogger("name1");
        Map<String, TestLogger> allTestLoggers = TestLoggerFactory.getAllTestLoggers();
        getInstance().getLogger("name2");

        Map<String, TestLogger> expected = new HashMap<String, TestLogger>();
        expected.put("name1", logger1);
        assertEquals(expected, allTestLoggers);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllLoggersReturnsUnmodifiableList() {
        Map<String, TestLogger> allTestLoggers = TestLoggerFactory.getAllTestLoggers();
        allTestLoggers.put("newlogger", new TestLogger("newlogger", TestLoggerFactory.getInstance()));
    }

    @Test
    public void getLoggingEventsOnlyReturnsEventsLoggedInThisThread() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                TestLoggerFactory.getTestLogger("name1").info("hello");
            }
        });
        t.start();
        t.join();
        assertEquals(Collections.emptyList(), TestLoggerFactory.getLoggingEvents());
    }

    @Test
    public void getAllLoggingEventsReturnsEventsLoggedInAllThreads() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                TestLoggerFactory.getTestLogger("name1").info("hello");
            }
        });
        t.start();
        t.join();
        TestLoggerFactory.getTestLogger("name1").info("hello");
        assertEquals(asList(info("hello"), info("hello")), TestLoggerFactory.getAllLoggingEvents());
    }

    @Test
    public void clearOnlyClearsEventsLoggedInThisThread() throws InterruptedException {
        final TestLogger logger = TestLoggerFactory.getTestLogger("name");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("hello");
            }
        });
        t.start();
        t.join();
        TestLoggerFactory.clear();
        assertEquals(asList(info("hello")), TestLoggerFactory.getAllLoggingEvents());
    }

    @Test
    public void clearAllClearsEventsLoggedInAllThreads() throws InterruptedException {
        final TestLogger logger = TestLoggerFactory.getTestLogger("name");
        logger.info("hello");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("hello");
                TestLoggerFactory.clearAll();
            }
        });
        t.start();
        t.join();
        assertEquals(emptyList(), TestLoggerFactory.getAllLoggingEvents());
        assertEquals(emptyList(), TestLoggerFactory.getLoggingEvents());
    }


    @Before @After
    public void resetLoggerFactory() {
        TestLoggerFactory.reset();
    }
}
