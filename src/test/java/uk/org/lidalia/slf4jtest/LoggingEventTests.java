package uk.org.lidalia.slf4jtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Marker;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.test.StaticTimeRule;
import uk.org.lidalia.test.SystemOutputRule;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static uk.org.lidalia.slf4jext.Level.DEBUG;
import static uk.org.lidalia.slf4jext.Level.ERROR;
import static uk.org.lidalia.slf4jext.Level.INFO;
import static uk.org.lidalia.slf4jext.Level.TRACE;
import static uk.org.lidalia.slf4jext.Level.WARN;
import static uk.org.lidalia.slf4jtest.LoggingEvent.debug;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.slf4jtest.LoggingEvent.trace;
import static uk.org.lidalia.slf4jtest.LoggingEvent.warn;
import static uk.org.lidalia.test.StaticTimeRule.alwaysStartOfEpoch;

@RunWith(JUnitParamsRunner.class)
public class LoggingEventTests {

    private static final ImmutableMap<String, String> emptyMap = ImmutableMap.of();

    @Rule public SystemOutputRule systemOutputRule = new SystemOutputRule();
    @Rule public StaticTimeRule alwaysStartOfEpoch = alwaysStartOfEpoch();

    Level level = Level.TRACE;
    Map<String, String> mdc = new HashMap<String, String>();
    {
        mdc.put("key", "value");
    }

    Marker marker = mock(Marker.class);
    Throwable throwable = new Throwable();
    String message = "message";
    Object arg1 = "arg1";
    Object arg2 = "arg2";
    List<Object> args = asList(arg1, arg2);

    @Test
    public void constructorMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(emptyMap));
        assertThat(event.getMarker(), isAbsent());
        assertThat(event.getThrowable(), isAbsent());
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, throwable, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(emptyMap));
        assertThat(event.getMarker(), isAbsent());
        assertThat(event.getThrowable(), is(of(throwable)));
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMarkerMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, marker, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(emptyMap));
        assertThat(event.getMarker(), is(of(marker)));
        assertThat(event.getThrowable(), isAbsent());
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMarkerThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, marker, throwable, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(emptyMap));
        assertThat(event.getMarker(), is(of(marker)));
        assertThat(event.getThrowable(), is(of(throwable)));
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMdcMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(mdc));
        assertThat(event.getMarker(), isAbsent());
        assertThat(event.getThrowable(), isAbsent());
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMdcThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, throwable, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(mdc));
        assertThat(event.getMarker(), isAbsent());
        assertThat(event.getThrowable(), is(of(throwable)));
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMdcMarkerMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(mdc));
        assertThat(event.getMarker(), is(of(marker)));
        assertThat(event.getThrowable(), isAbsent());
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void constructorMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event.getLevel(), is(level));
        assertThat(event.getMdc(), is(mdc));
        assertThat(event.getMarker(), is(of(marker)));
        assertThat(event.getThrowable(), is(of(throwable)));
        assertThat(event.getMessage(), is(message));
        assertThat(event.getArguments(), is(args));
    }

    @Test
    public void traceMessageArgs() {
        LoggingEvent event = trace(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceThrowableMessageArgs() {
        LoggingEvent event = trace(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMarkerMessageArgs() {
        LoggingEvent event = trace(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMarkerThrowableMessageArgs() {
        LoggingEvent event = trace(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMdcMessageArgs() {
        LoggingEvent event = trace(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMdcThrowableMessageArgs() {
        LoggingEvent event = trace(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMdcMarkerMessageArgs() {
        LoggingEvent event = trace(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void traceMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = trace(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMessageArgs() {
        LoggingEvent event = debug(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugThrowableMessageArgs() {
        LoggingEvent event = debug(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMarkerMessageArgs() {
        LoggingEvent event = debug(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMarkerThrowableMessageArgs() {
        LoggingEvent event = debug(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMdcMessageArgs() {
        LoggingEvent event = debug(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMdcThrowableMessageArgs() {
        LoggingEvent event = debug(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMdcMarkerMessageArgs() {
        LoggingEvent event = debug(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void debugMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = debug(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMessageArgs() {
        LoggingEvent event = info(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoThrowableMessageArgs() {
        LoggingEvent event = info(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMarkerMessageArgs() {
        LoggingEvent event = info(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMarkerThrowableMessageArgs() {
        LoggingEvent event = info(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMdcMessageArgs() {
        LoggingEvent event = info(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMdcThrowableMessageArgs() {
        LoggingEvent event = info(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMdcMarkerMessageArgs() {
        LoggingEvent event = info(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void infoMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = info(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMessageArgs() {
        LoggingEvent event = warn(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnThrowableMessageArgs() {
        LoggingEvent event = warn(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMarkerMessageArgs() {
        LoggingEvent event = warn(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMarkerThrowableMessageArgs() {
        LoggingEvent event = warn(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMdcMessageArgs() {
        LoggingEvent event = warn(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMdcThrowableMessageArgs() {
        LoggingEvent event = warn(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMdcMarkerMessageArgs() {
        LoggingEvent event = warn(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void warnMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = warn(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMessageArgs() {
        LoggingEvent event = error(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorThrowableMessageArgs() {
        LoggingEvent event = error(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMarkerMessageArgs() {
        LoggingEvent event = error(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMarkerThrowableMessageArgs() {
        LoggingEvent event = error(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMdcMessageArgs() {
        LoggingEvent event = error(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMdcThrowableMessageArgs() {
        LoggingEvent event = error(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMdcMarkerMessageArgs() {
        LoggingEvent event = error(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, marker, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void errorMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = error(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, marker, throwable, message, arg1, arg2);
        assertThat(event, is(expected));
    }

    @Test
    public void mdcIsSnapshotInTime() {
        Map<String, String> mdc = new HashMap<String, String>();
        mdc.put("key", "value1");
        Map<String, String> mdcAtStart = new HashMap<String, String>(mdc);
        LoggingEvent event = new LoggingEvent(level, mdc, message);
        mdc.put("key", "value2");
        assertThat(event.getMdc(), is(mdcAtStart));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void mdcNotModifiable() throws Throwable {
        Map<String, String> mdc = new HashMap<String, String>();
        mdc.put("key", "value1");
        final LoggingEvent event = new LoggingEvent(level, mdc, message);
        event.getMdc().put("anything", "whatever");
    }

    @Test
    public void argsIsSnapshotInTime() {
        Object[] args = new Object[]{arg1, arg2};
        Object[] argsAtStart = Arrays.copyOf(args, args.length);
        LoggingEvent event = new LoggingEvent(level, message, args);
        args[0] = "differentArg";
        assertThat(event.getArguments(), is(asList(argsAtStart)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void argsNotModifiable() throws Throwable {
        final LoggingEvent event = new LoggingEvent(level, message, arg1);
        event.getArguments().add(arg2);
    }

    @Test
    public void timestamp() {
        LoggingEvent event = info("Message");
        assertThat(event.getTimestamp(), is(alwaysStartOfEpoch.getInstant()));
    }

    @Test(expected = IllegalStateException.class)
    public void creatingLoggerNotPresent() {
        info("message").getCreatingLogger();
    }

    @Test
    public void creatingLoggerPresent() {
        final TestLogger logger = TestLoggerFactory.getInstance().getLogger("logger");
        logger.info("message");
        final LoggingEvent event = logger.getLoggingEvents().get(0);
        assertThat(event.getCreatingLogger(), is(logger));
    }

    @Test
    public void printToStandardOutNoThrowable() {
        LoggingEvent event = new LoggingEvent(INFO, "message with {}", "argument");
        event.print();

        assertThat(systemOutputRule.getSystemOut(),
                is("1970-01-01T00:00:00.000Z ["+Thread.currentThread().getName()+"] INFO - message with argument"+lineSeparator()));
    }

    @Test
    public void printToStandardOutWithThrowable() {
        LoggingEvent event = new LoggingEvent(INFO, new Exception(), "message");
        event.print();

        assertThat(systemOutputRule.getSystemOut(),
                startsWith("1970-01-01T00:00:00.000Z ["+Thread.currentThread().getName()+"] INFO - message"+lineSeparator()
                        + "java.lang.Exception"+lineSeparator()
                        + "\tat"
                ));
    }

    @Test
    @Parameters({"TRACE", "DEBUG", "INFO"})
    public void printInfoAndBelow(Level level) {
        LoggingEvent event = new LoggingEvent(level, "message with {}", "argument");
        event.print();
        assertThat(systemOutputRule.getSystemOut(), is(not("")));
        assertThat(systemOutputRule.getSystemErr(), is(""));
    }

    @Test
    @Parameters({"WARN", "ERROR"})
    public void printWarnAndAbove(Level level) {
        LoggingEvent event = new LoggingEvent(level, "message with {}", "argument");
        event.print();
        assertThat(systemOutputRule.getSystemErr(), is(not("")));
        assertThat(systemOutputRule.getSystemOut(), is(""));
    }

    @Test
    public void nullArgument() {
        LoggingEvent event = new LoggingEvent(level, "message with null arg", null, null);
        assertThat(event, is(new LoggingEvent(level, "message with null arg", absent(), absent())));
    }

    @After
    public void reset() {
        TestLoggerFactory.reset();
    }

    @SuppressWarnings("unchecked")
    private Matcher<Optional<?>> isAbsent() {
        final Matcher optionalMatcher = is(Optional.absent());
        return (Matcher<Optional<?>>) optionalMatcher;
    }

    private static String lineSeparator() {
        return System.getProperty("line.separator");
    }
}
