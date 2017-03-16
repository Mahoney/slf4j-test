package uk.org.lidalia.slf4jtest;

import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static uk.org.lidalia.slf4jext.Level.INFO;

public class TestLoggerRuleTest {
    @Rule
    public TestLoggerRule testSubject = TestLoggerRule.forClass(Slf4jUser.class);

    Slf4jUser slf4jUser = new Slf4jUser();

    @Test
    public void expectMessage_InfoMessage() throws Exception {
        testSubject.expectMessage(INFO, "Hello World!");

        slf4jUser.aMethodThatInfoLogs();
    }

    @Test
    public void expectMessage_InfoMessageAndException() throws Exception {
        testSubject.expectMessage(INFO, "Hello World!", Exception.class);

        slf4jUser.aMethodThatInfoLogsWithException();
    }

    @Test
    public void expectMessage_WhenLogNotCalled() throws Exception {
        slf4jUser.aMethodThatDoesNotLog();
    }

    private static class Slf4jUser {
        private static final Logger logger = LoggerFactory.getLogger(Slf4jUser.class);

        public void aMethodThatInfoLogs() {
            logger.info("Hello World!");
        }

        public void aMethodThatInfoLogsWithException() {
            logger.info("Hello World!", new Exception());
        }

        public void aMethodThatDoesNotLog() {
        }
    }
}
