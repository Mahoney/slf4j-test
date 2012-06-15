package uk.org.lidalia.slf4jtest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.ILoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.common.base.Optional.fromNullable;

public class TestLoggerFactory implements ILoggerFactory {

    private static final TestLoggerFactory INSTANCE = new TestLoggerFactory();

    public static TestLoggerFactory getInstance() {
        return INSTANCE;
    }

    public static TestLogger getTestLogger(Class<?> aClass) {
        return getInstance().getLogger(aClass);
    }

    public static TestLogger getTestLogger(String name) {
        return getInstance().getLogger(name);
    }

    public static Map<String, TestLogger> getAllTestLoggers() {
        return getInstance().getAllLoggers();
    }

    public static void clear() {
        getInstance().clearLoggers();
    }

    static void reset() {
        getInstance().doReset();
    }

    public static List<LoggingEvent> getLoggingEvents() {
        return getInstance().getLoggingEventsFromLoggers();
    }

    private final ConcurrentMap<String, TestLogger> loggerMap = new ConcurrentHashMap<String, TestLogger>();
    private final List<LoggingEvent> loggingEvents = new CopyOnWriteArrayList<LoggingEvent>();

    private TestLoggerFactory() {
    }

    public ImmutableMap<String, TestLogger> getAllLoggers() {
        return ImmutableMap.copyOf(loggerMap);
    }

    public TestLogger getLogger(Class<?> aClass) {
        return getLogger(aClass.getName());
    }

    public TestLogger getLogger(String name) {
        TestLogger newLogger = new TestLogger(name, this);
        return fromNullable(loggerMap.putIfAbsent(name, newLogger)).or(newLogger);
    }

    public void clearLoggers() {
        for (TestLogger testLogger: loggerMap.values()) {
            testLogger.clear();
        }
        loggingEvents.clear();
    }

    void doReset() {
        loggerMap.clear();
        loggingEvents.clear();
    }

    public ImmutableList<LoggingEvent> getLoggingEventsFromLoggers() {
        return ImmutableList.copyOf(loggingEvents);
    }

    void addLoggingEvent(LoggingEvent event) {
        loggingEvents.add(event);
    }
}
