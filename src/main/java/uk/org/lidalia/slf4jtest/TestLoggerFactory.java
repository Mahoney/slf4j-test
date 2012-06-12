package uk.org.lidalia.slf4jtest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    private final ConcurrentMap<String, TestLogger> loggerMap = new ConcurrentHashMap<String, TestLogger>();

    private TestLoggerFactory() {
    }

    public TestLogger getLogger(String name) {
        TestLogger newLogger = new TestLogger(name);
        return fromNullable(loggerMap.putIfAbsent(name, newLogger)).or(newLogger);
    }

    public static void clear() {
        for (TestLogger testLogger: INSTANCE.loggerMap.values()) {
            testLogger.clear();
        }
    }
}
