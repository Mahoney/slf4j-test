package uk.org.lidalia.slf4jtest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.ILoggerFactory;

import static com.google.common.base.Optional.fromNullable;

public class TestLoggerFactory implements ILoggerFactory {

    public static final TestLoggerFactory INSTANCE = new TestLoggerFactory();

    public static TestLogger getTestLogger(Class<?> aClass) {
        return INSTANCE.getLogger(aClass.getName());
    }

    public static TestLogger getTestLogger(String name) {
        return INSTANCE.getLogger(name);
    }

    public static Map<String, TestLogger> getAllTestLoggers() {
        return INSTANCE.getAllLoggers();
    }

    public static void clear() {
        INSTANCE.doClear();
    }

    public static List<LoggingEvent> getAllLoggingEvents() {
        return INSTANCE.getEveryLoggingEvent();
    }

    private final ConcurrentMap<String, TestLogger> loggerMap = new ConcurrentHashMap<String, TestLogger>();
    private final List<LoggingEvent> loggingEvents = new CopyOnWriteArrayList<LoggingEvent>();

    private TestLoggerFactory() {
    }

    public Map<String, TestLogger> getAllLoggers() {
        return Collections.unmodifiableMap(new HashMap<String, TestLogger>(loggerMap));
    }

    public TestLogger getLogger(String name) {
        TestLogger newLogger = new TestLogger(name, this);
        return fromNullable(loggerMap.putIfAbsent(name, newLogger)).or(newLogger);
    }

    public void doClear() {
        for (TestLogger testLogger: loggerMap.values()) {
            testLogger.clear();
        }
        loggingEvents.clear();
    }

    public List<LoggingEvent> getEveryLoggingEvent() {
        return loggingEvents;
    }

    void addLoggingEvent(LoggingEvent event) {
        loggingEvents.add(event);
    }
}
