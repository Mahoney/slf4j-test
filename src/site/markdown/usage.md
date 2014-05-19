## Usage

### Making Assertions

TestLoggerFactory is the underlying implementation to which SLF4J's
LoggerFactory delegates when SLF4J Test is the bound SLF4J implementation. The
getLogger method on TestLoggerFactory returns the same Logger instance as that
on LoggerFactory, but with the additional type information to allow retrieval of
the LoggingEvents that have occurred against that Logger.

SLF4J Test provides a comprehensive set of static factory methods on
LoggingEvent to facilitate easy construction of expected events for comparison
against those that were actually received, and LoggingEvent has appropriate
equals, hashCode and toString implementations for this purpose. See the [front
](./index.html) page for an example of using the [info static factory method
](./xref/uk/org/lidalia/slf4jtest/TestLogger.html#L268)to create an expected
LoggingEvent.

### Setting the Log Level on a Logger

SLF4J Test only stores events for levels which are marked as enabled on the
Logger. By default all levels are enabled; however, this can be programatically
changed on a per logger basis using the following functions:

    Logger.setEnabledLevels(Level... levels)
    Logger.setEnabledLevelsForAllThreads(Level... levels)

It's important to note that SLF4J does *not* imply any relationship between the
levels - it is perfectly possible for a logger to be enabled for INFO but
disabled for ERROR as far as SLF4J is concerned, and hence as far as SLF4J Test
is concerned, notwithstanding the fact that implementations such as Logback and
Log4J do not permit this. If you wish to test using these common configurations,
you should use the constants defined in
uk.org.lidalia.slf4jext.ConventionalLevelHierarchy.

### Resetting Stored State

In order to have robust tests the in memory state of SLF4J Test must be in a
known state for each test run, which in turn implies that a test should clean up
after itself. The simplest way to do so is via a call to

    TestLoggerFactory.clear()

in a tear down method of some kind. If you are using JUnit then SLF4J Test
provides a Rule that will do this for you if you provide the following line in
your test class:

    @Rule public TestRule resetLoggingEvents = new TestLoggerFactoryResetRule();

More nuanced state resetting can be done on a per logger basis:

    TestLogger.clear()

or more aggressive clears will reset state across all threads:

    TestLoggerFactory.clearAll()
    TestLogger.clearAll()

### Parallel Testing

SLF4J Test is designed to facilitate tests run in parallel. Because SLF4J
Loggers are commonly shared across threads, the SLF4J Test implementation
maintains its state in ThreadLocals. The following functions:

    TestLogger.getLoggingEvents()
    TestLogger.clear()
    TestLogger.getEnabledLevels()
    TestLogger.setEnabledLevels(Level... levels)
    TestLoggerFactory.getLoggingEvents()
    TestLoggerFactory.clear()

all only affect state stored in a ThreadLocal, and thus tests which use them can
safely be parallelised without any danger of concurrent test runs affecting each
other.

### Testing Multiple Threads Logging

At times, however, it may be desirable to assert about the log messages produced
by concurrent code. To facilitate this SLF4J Test also maintains a record of
logging events from *all* threads. This state can be accessed and reset using
the following functions:

    TestLogger.getAllLoggingEvents()
    TestLogger.clearAll()
    TestLogger.setEnabledLevelsForAllThreads(Level... levels)
    TestLoggerFactory.getAllLoggingEvents()
    TestLoggerFactory.clearAll()


### Printing log statements to System out and err

It can still be useful to print log messages to System out/err as appropriate.
SLF4J Test will print messages using a standard (non-configurable) format based
on the value of the TestLoggerFactory's printLevel property. For convenience
this does respect the conventional level hierarchy where if the print level is
INFO logging events at levels WARN and ERROR will also be printed. This can be
set in any of the following ways:

#### Programatically
    TestLoggerFactory.getInstance().setPrintLevel(Level.INFO);

#### Via a System Property
Run the JVM with the following:

    -Dslf4jtest.print.level=INFO

#### Via a properties file
Place a file called slf4jtest.properties on the classpath with the following
line in it:

    print.level=INFO
