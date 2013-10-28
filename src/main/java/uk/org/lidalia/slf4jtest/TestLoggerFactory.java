package uk.org.lidalia.slf4jtest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.ILoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.lidalia.lang.LazyValue;
import uk.org.lidalia.lang.ThreadLocal;
import uk.org.lidalia.slf4jext.Level;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;

public final class TestLoggerFactory implements ILoggerFactory {

    private static final LazyValue<TestLoggerFactory> INSTANCE = new LazyValue<TestLoggerFactory>(new TestLoggerFactoryMaker());

    public static TestLoggerFactory getInstance() {
        return INSTANCE.call();
    }

    public static TestLogger getTestLogger(final Class<?> aClass) {
        return getInstance().getLogger(aClass);
    }

    public static TestLogger getTestLogger(final String name) {
        return getInstance().getLogger(name);
    }

    public static Map<String, TestLogger> getAllTestLoggers() {
        return getInstance().getAllLoggers();
    }

    public static void clear() {
        getInstance().clearLoggers();
    }

    public static void clearAll() {
        getInstance().clearAllLoggers();
    }

    static void reset() {
        getInstance().doReset();
    }

    public static List<LoggingEvent> getLoggingEvents() {
        return getInstance().getLoggingEventsFromLoggers();
    }

    public static List<LoggingEvent> getAllLoggingEvents() {
        return getInstance().getAllLoggingEventsFromLoggers();
    }

    private final ConcurrentMap<String, TestLogger> loggers = new ConcurrentHashMap<String, TestLogger>();
    private final List<LoggingEvent> allLoggingEvents = new CopyOnWriteArrayList<LoggingEvent>();
    private final ThreadLocal<List<LoggingEvent>> loggingEvents =
            new ThreadLocal<List<LoggingEvent>>(Suppliers.<LoggingEvent>makeEmptyMutableList());
    private volatile Level printLevel;

    private TestLoggerFactory(final Level printLevel) {
        this.printLevel = checkNotNull(printLevel);
    }

    public Level getPrintLevel() {
        return printLevel;
    }

    public ImmutableMap<String, TestLogger> getAllLoggers() {
        return ImmutableMap.copyOf(loggers);
    }

    public TestLogger getLogger(final Class<?> aClass) {
        return getLogger(aClass.getName());
    }

    public TestLogger getLogger(final String name) {
        final TestLogger newLogger = new TestLogger(name, this);
        return fromNullable(loggers.putIfAbsent(name, newLogger)).or(newLogger);
    }

    public void clearLoggers() {
        for (final TestLogger testLogger: loggers.values()) {
            testLogger.clear();
        }
        loggingEvents.get().clear();
    }

    public void clearAllLoggers() {
        for (final TestLogger testLogger: loggers.values()) {
            testLogger.clearAll();
        }
        loggingEvents.reset();
        allLoggingEvents.clear();
    }

    void doReset() {
        clearAllLoggers();
        loggers.clear();
    }

    public ImmutableList<LoggingEvent> getLoggingEventsFromLoggers() {
        return ImmutableList.copyOf(loggingEvents.get());
    }

    public List<LoggingEvent> getAllLoggingEventsFromLoggers() {
        return allLoggingEvents;
    }

    void addLoggingEvent(final LoggingEvent event) {
        loggingEvents.get().add(event);
        allLoggingEvents.add(event);
    }

    public void setPrintLevel(final Level printLevel) {
        this.printLevel = checkNotNull(printLevel);
    }

    @SuppressWarnings("PMD.AccessorClassGeneration")
    private static class TestLoggerFactoryMaker implements Callable<TestLoggerFactory> {
        @Override
        public TestLoggerFactory call() throws IOException {
            try {
                final String level = new OverridableProperties("slf4jtest").getProperty("print.level", "OFF");
                final Level printLevel = Level.valueOf(level);
                return new TestLoggerFactory(printLevel);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Invalid level name in property print.level of file slf4jtest.properties " +
                        "or System property slf4jtest.print.level", e);
            }
        }
    }
}
