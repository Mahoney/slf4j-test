## SLF4J Test

A test implementation of [SLF4J](http://www.slf4j.org/) that stores log messages in memory and provides methods for retrieving them.

See the [JavaDocs](./apidocs/index.html) for full documentation and the [Test Source](./xref-test/index.html) for complete
examples of usage.

Details on how to depend on this library in your favourite build tool can be found [here](./dependency-info.html).

### Scope

Since there should only ever be [one SLF4J implementation on your classpath](http://www.slf4j.org/codes.html#multiple_bindings)
this library only works as a tool for testing other libraries. It should be added to the test classpath of libraries that use
the SLF4J API. If you currently produce your entire application as a single module / project, and so depend on an SLF4J
implementation like [Logback](http://logback.qos.ch/) in the run or compile time dependencies, you will need to break it up if you
wish to use this library reliably.

### Purpose

It is a common pattern to use SLF4J in the following manner:

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    public class Slf4jUser {

        private static final Logger logger = LoggerFactory.getLogger(Slf4jUser.class);

        public void aMethodThatLogs() {
            logger.info("Hello World!");
        }
    }

This is because it is arduous to inject a Logger instance into every class that needs to log. However, this renders it quite
difficult to unit test interactions with the logger instance; even if it is not stored statically or made final, it will almost
certainly be private, and to make it anything other than private or final is to compromise the design for testability which is
obviously undesirable.

This library gets around this by providing an implementation of SLF4J which stores the logging events in memory in a form that can
be interrogated. So long as slf4j-test is present as the sole SLF4J implementation on the classpath, the class above could be
tested as so:

    import org.junit.After;
    import org.junit.Test;

    import uk.org.lidalia.slf4jtest.TestLogger;
    import uk.org.lidalia.slf4jtest.TestLoggerFactory;

    import static java.util.Arrays.asList;
    import static org.hamcrest.Matchers.is;
    import static org.junit.Assert.assertThat;
    import static uk.org.lidalia.slf4jtest.LoggingEvent.info;

    public class Slf4jUserTest {

        Slf4jUser slf4jUser = new Slf4jUser();
        TestLogger logger = TestLoggerFactory.getTestLogger(Slf4jUser.class);

        @Test
        public void aMethodThatLogsLogsAsExpected() {
            slf4jUser.aMethodThatLogs();

            assertThat(logger.getLoggingEvents(), is(asList(info("Hello World!"))));
        }

        @After
        public void clearLoggers() {
            TestLoggerFactory.clear();
        }
    }
