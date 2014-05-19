## SLF4J Test

A test implementation of [SLF4J](http://www.slf4j.org/) that stores log messages
in memory and provides methods for retrieving them.

Below is a quick start; more detailed usage information is available [here.
](./usage.html) See the [JavaDocs](./apidocs/index.html) for full documentation
and the [Test Source](./xref-test/index.html) for complete examples of usage.

Details on how to depend on this library in your favourite build tool can be
found [here](./dependency-info.html).

### Getting Started

#### Setting up

SLF4J Test should be [the only SLF4J implementation on your test classpath
](http://www.slf4j.org/codes.html#multiple_bindings), and SLF4J will warn you if
there is more than one. If the module is a library this should not be an issue
as you should not be including a logging implementation as a compile or runtime
dependency anyway. If the module is an application (whether standalone or
something like a WAR that is deployed in a container) then you have two options:
separate out the logic into a library, or exclude the real implementation from
the test classpath. In Maven this can be done as so:

    <build>
      <plugins>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <configuration>
            <classpathDependencyExcludes>
              <classpathDependencyExcludes>ch.qos.logback:logback-classic</classpathDependencyExcludes>
            </classpathDependencyExcludes>
          </configuration>
        </plugin>
      </plugins>
    </build>

#### Basic example

It is a common pattern to use SLF4J in the following manner:

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    public class Slf4jUser {

        private static final Logger logger = LoggerFactory.getLogger(Slf4jUser.class);

        public void aMethodThatLogs() {
            logger.info("Hello World!");
        }
    }

This is because it is arduous to inject a Logger instance into every class that
needs to log. However, this renders it quite difficult to unit test interactions
with the logger instance; even if it is not stored statically or made final, it
will almost certainly be private, and to make it anything other than private or
final is to compromise the design for testability which is obviously
undesirable.

This library gets around this by providing an implementation of SLF4J which
stores the logging events in memory in a form that can be interrogated. So long
as slf4j-test is present as the sole SLF4J implementation on the classpath, the
class above could be tested as so:

    import org.junit.After;
    import org.junit.Test;

    import uk.org.lidalia.slf4jtest.TestLogger;
    import uk.org.lidalia.slf4jtest.TestLoggerFactory;

    import static java.util.Arrays.asList;
    import static org.hamcrest.Matchers.is;
    import static org.junit.Assert.assertThat;
    import static uk.org.lidalia.slf4jtest.LoggingEvent.info;

    public class Slf4jUserTest {

        @Rule public TestRule resetLoggingEvents = new TestLoggerFactoryResetRule();

        Slf4jUser slf4jUser = new Slf4jUser();
        TestLogger logger = TestLoggerFactory.getTestLogger(Slf4jUser.class);

        @Test
        public void aMethodThatLogsLogsAsExpected() {

            slf4jUser.aMethodThatLogs();

            assertThat(logger.getLoggingEvents(), is(asList(info("Hello World!"))));
        }
    }
