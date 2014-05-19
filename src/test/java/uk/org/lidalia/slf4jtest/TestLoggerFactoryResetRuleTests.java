package uk.org.lidalia.slf4jtest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import uk.org.lidalia.slf4jext.LoggerFactory;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.slf4jtest.TestLoggerFactory.getLoggingEvents;

public class TestLoggerFactoryResetRuleTests {

    @Rule public TestRule resetLoggingEvents = new TestLoggerFactoryResetRule();

    @Test
    public void logOnce() {
        LoggerFactory.getLogger("logger").info("a message");
        assertThat(getLoggingEvents(), is(asList(info("a message"))));
    }

    @Test
    public void logAgain() {
        LoggerFactory.getLogger("logger").info("a message");
        assertThat(getLoggingEvents(), is(asList(info("a message"))));
    }
}
