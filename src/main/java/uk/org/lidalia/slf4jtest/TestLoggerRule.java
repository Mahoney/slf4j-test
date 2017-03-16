package uk.org.lidalia.slf4jtest;

import com.google.common.base.Optional;
import junit.framework.AssertionFailedError;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import uk.org.lidalia.slf4jext.Level;

import java.util.LinkedList;
import java.util.List;

public class TestLoggerRule implements TestRule {
    private final TestLogger logger;
    private List<ExpectedLogEvent> expectedEvents = new LinkedList<>();

    public TestLoggerRule(Class<?> aClass) {
        logger = TestLoggerFactory.getTestLogger(aClass);
    }

    public static TestLoggerRule forClass(Class<?> aClass) {
        return new TestLoggerRule(aClass);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                    verify();
                } finally {
                    after();
                }
            }
        };
    }

    private void after() {
        logger.clear();
    }

    private void verify() {
        while (!expectedEvents.isEmpty()) {
            ExpectedLogEvent expectedLogEvent = expectedEvents.remove(0);
            boolean found = false;
            for (LoggingEvent loggingEvent : logger.getLoggingEvents()) {
                if (expectedLogEvent.matches(loggingEvent)) {
                    found = true;
                }
            }
            if (!found) {
                throw new AssertionFailedError("Expected " + expectedLogEvent);
            }
        }
    }

    public void expectMessage(Level level) {
        expectMessage(level, "");
    }

    public void expectMessage(Level level, String msg) {
        expectMessage(level, msg, null);
    }

    public void expectMessage(Level level, String msg, Class<? extends Throwable> throwableClass) {
        expectedEvents.add(new ExpectedLogEvent(level, msg, throwableClass));
    }

    private final static class ExpectedLogEvent {
        private final String message;
        private final Level level;
        private final Class<? extends Throwable> throwableClass;

        private ExpectedLogEvent(Level level, String message, Class<? extends Throwable> throwableClass) {
            this.message = message;
            this.level = level;
            this.throwableClass = throwableClass;
        }

        private boolean matches(LoggingEvent actual) {
            boolean match = actual.getMessage().contains(message);
            match &= actual.getLevel().equals(level);
            match &= matchThrowables(actual);
            return match;
        }

        private boolean matchThrowables(LoggingEvent actual) {
            Optional<Throwable> eventProxy = actual.getThrowable();
            return throwableClass == null || eventProxy != null && throwableClass.getName().equals(eventProxy.get().getClass().getName());
        }

        @Override
        public String toString() {
            return "ExpectedLogEvent{" +
                    "message='" + message + '\'' +
                    ", level=" + level +
                    ", throwableClass=" + throwableClass +
                    '}';
        }
    }

}
