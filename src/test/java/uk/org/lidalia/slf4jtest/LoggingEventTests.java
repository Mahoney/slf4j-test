package uk.org.lidalia.slf4jtest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Marker;
import uk.org.lidalia.slf4jext.Level;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static uk.org.lidalia.slf4jtest.LoggingEvent.debug;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;
import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
import static uk.org.lidalia.slf4jtest.LoggingEvent.trace;
import static uk.org.lidalia.slf4jtest.LoggingEvent.warn;
import static uk.org.lidalia.slf4jext.Level.DEBUG;
import static uk.org.lidalia.slf4jext.Level.ERROR;
import static uk.org.lidalia.slf4jext.Level.INFO;
import static uk.org.lidalia.slf4jext.Level.TRACE;
import static uk.org.lidalia.slf4jext.Level.WARN;

public class LoggingEventTests {

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
        assertEquals(level, event.getLevel());
        assertEquals(Collections.emptyMap(), event.getMdc());
        assertEquals(absent(), event.getMarker());
        assertEquals(absent(), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, throwable, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(Collections.emptyMap(), event.getMdc());
        assertEquals(absent(), event.getMarker());
        assertEquals(of(throwable), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
     public void constructorMarkerMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, marker, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(Collections.emptyMap(), event.getMdc());
        assertEquals(of(marker), event.getMarker());
        assertEquals(absent(), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorMarkerThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, marker, throwable, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(Collections.emptyMap(), event.getMdc());
        assertEquals(of(marker), event.getMarker());
        assertEquals(of(throwable), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorMdcMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(mdc, event.getMdc());
        assertEquals(absent(), event.getMarker());
        assertEquals(absent(), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorMdcThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, throwable, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(mdc, event.getMdc());
        assertEquals(absent(), event.getMarker());
        assertEquals(of(throwable), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorMdcMarkerMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(mdc, event.getMdc());
        assertEquals(of(marker), event.getMarker());
        assertEquals(absent(), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void constructorMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(level, event.getLevel());
        assertEquals(mdc, event.getMdc());
        assertEquals(of(marker), event.getMarker());
        assertEquals(of(throwable), event.getThrowable());
        assertEquals(message, event.getMessage());
        assertEquals(args, event.getArguments());
    }

    @Test
    public void traceMessageArgs() {
        LoggingEvent event = trace(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceThrowableMessageArgs() {
        LoggingEvent event = trace(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMarkerMessageArgs() {
        LoggingEvent event = trace(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMarkerThrowableMessageArgs() {
        LoggingEvent event = trace(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMdcMessageArgs() {
        LoggingEvent event = trace(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMdcThrowableMessageArgs() {
        LoggingEvent event = trace(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMdcMarkerMessageArgs() {
        LoggingEvent event = trace(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void traceMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = trace(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(TRACE, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMessageArgs() {
        LoggingEvent event = debug(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugThrowableMessageArgs() {
        LoggingEvent event = debug(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMarkerMessageArgs() {
        LoggingEvent event = debug(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMarkerThrowableMessageArgs() {
        LoggingEvent event = debug(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMdcMessageArgs() {
        LoggingEvent event = debug(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMdcThrowableMessageArgs() {
        LoggingEvent event = debug(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMdcMarkerMessageArgs() {
        LoggingEvent event = debug(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void debugMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = debug(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(DEBUG, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMessageArgs() {
        LoggingEvent event = info(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoThrowableMessageArgs() {
        LoggingEvent event = info(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMarkerMessageArgs() {
        LoggingEvent event = info(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMarkerThrowableMessageArgs() {
        LoggingEvent event = info(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMdcMessageArgs() {
        LoggingEvent event = info(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMdcThrowableMessageArgs() {
        LoggingEvent event = info(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMdcMarkerMessageArgs() {
        LoggingEvent event = info(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void infoMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = info(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(INFO, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMessageArgs() {
        LoggingEvent event = warn(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnThrowableMessageArgs() {
        LoggingEvent event = warn(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMarkerMessageArgs() {
        LoggingEvent event = warn(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMarkerThrowableMessageArgs() {
        LoggingEvent event = warn(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMdcMessageArgs() {
        LoggingEvent event = warn(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMdcThrowableMessageArgs() {
        LoggingEvent event = warn(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMdcMarkerMessageArgs() {
        LoggingEvent event = warn(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void warnMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = warn(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(WARN, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMessageArgs() {
        LoggingEvent event = error(message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorThrowableMessageArgs() {
        LoggingEvent event = error(throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMarkerMessageArgs() {
        LoggingEvent event = error(marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMarkerThrowableMessageArgs() {
        LoggingEvent event = error(marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMdcMessageArgs() {
        LoggingEvent event = error(mdc, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMdcThrowableMessageArgs() {
        LoggingEvent event = error(mdc, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMdcMarkerMessageArgs() {
        LoggingEvent event = error(mdc, marker, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, marker, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void errorMdcMarkerThrowableMessageArgs() {
        LoggingEvent event = error(mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent expected = new LoggingEvent(ERROR, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(expected, event);
    }

    @Test
    public void mdcIsSnapshotInTime() {
        Map<String, String> mdc = new HashMap<String, String>();
        mdc.put("key", "value1");
        Map<String, String> mdcAtStart = new HashMap<String, String>(mdc);
        LoggingEvent event = new LoggingEvent(level, mdc, message);
        mdc.put("key", "value2");
        assertEquals(mdcAtStart, event.getMdc());
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
        Object[] args = new Object[] { arg1, arg2 };
        Object[] argsAtStart = Arrays.copyOf(args, args.length);
        LoggingEvent event = new LoggingEvent(level, message, args);
        args[0] = "differentArg";
        assertEquals(asList(argsAtStart), event.getArguments());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void argsNotModifiable() throws Throwable {
        final LoggingEvent event = new LoggingEvent(level, message, arg1);
        event.getArguments().add(arg2);
    }

    @Test
    public void timestamp() {
        DateTime now = new DateTime();
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        LoggingEvent event = info("Message");
        assertEquals(now, event.getTimestamp());
    }

    @After
    public void turnTimeBackToNow() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}
