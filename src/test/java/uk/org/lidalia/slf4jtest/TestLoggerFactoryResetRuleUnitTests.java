package uk.org.lidalia.slf4jtest;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import uk.org.lidalia.lang.Task;
import uk.org.lidalia.slf4jext.Level;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static uk.org.lidalia.lang.Exceptions.throwUnchecked;
import static uk.org.lidalia.slf4jext.Level.DEBUG;
import static uk.org.lidalia.slf4jext.Level.INFO;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.test.ShouldThrow.shouldThrow;

public class TestLoggerFactoryResetRuleUnitTests {

    TestLoggerFactoryResetRule resetRule = new TestLoggerFactoryResetRule();

    @Test
    public void resetsThreadLocalData() throws Throwable {

        final TestLogger logger = TestLoggerFactory.getTestLogger("logger_name");
        logger.setEnabledLevels(INFO, DEBUG);
        logger.info("a message");

        resetRule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
            }
        }, Description.EMPTY).evaluate();


        assertThat(TestLoggerFactory.getLoggingEvents(), is(Collections.<LoggingEvent>emptyList()));
        assertThat(logger.getLoggingEvents(), is(Collections.<LoggingEvent>emptyList()));
        assertThat(logger.getEnabledLevels(), is(Level.enablableValueSet()));
    }

    @Test
    public void resetsThreadLocalDataOnException() throws Throwable {

        final TestLogger logger = TestLoggerFactory.getTestLogger("logger_name");
        logger.setEnabledLevels(INFO, DEBUG);
        logger.info("a message");

        final Exception toThrow = new Exception();
        Exception thrown = shouldThrow(Exception.class, new Task() {
            @Override
            public void perform() throws Exception {
                try {
                    resetRule.apply(new Statement() {
                        @Override
                        public void evaluate() throws Throwable {
                            throw toThrow;
                        }
                    }, Description.EMPTY).evaluate();
                } catch (Throwable throwable) {
                    throwUnchecked(throwable);
                }
            }
        });

        assertThat(thrown, is(toThrow));
        assertThat(TestLoggerFactory.getLoggingEvents(), is(Collections.<LoggingEvent>emptyList()));
        assertThat(logger.getLoggingEvents(), is(Collections.<LoggingEvent>emptyList()));
        assertThat(logger.getEnabledLevels(), is(Level.enablableValueSet()));
    }

    @Test
    public void doesNotResetNonThreadLocalData() throws Throwable {

        final TestLogger logger = TestLoggerFactory.getTestLogger("logger_name");
        logger.info("a message");

        resetRule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
            }
        }, Description.EMPTY).evaluate();

        final List<LoggingEvent> loggedEvents = asList(info("a message"));

        assertThat(TestLoggerFactory.getAllLoggingEvents(), is(loggedEvents));
        assertThat(logger.getAllLoggingEvents(), is(loggedEvents));
    }

    @Before
    public void resetTestLoggerFactory() {
        TestLoggerFactory.reset();
    }
}
