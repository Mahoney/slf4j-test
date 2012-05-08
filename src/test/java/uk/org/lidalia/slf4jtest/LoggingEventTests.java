package uk.org.lidalia.slf4jtest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Marker;

import uk.org.lidalia.slf4jutils.Level;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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
    public void equalsContract() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertTrue(event.equals(event));
        assertFalse(event.equals(null));
        assertFalse(event.equals("adifferenttype"));

        LoggingEvent equalEvent = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertTrue(event.equals(equalEvent));
        assertTrue(equalEvent.equals(event));

        LoggingEvent differentLevel = new LoggingEvent(Level.DEBUG, mdc, marker, throwable, message, arg1, arg2);
        assertFalse(event.equals(differentLevel));
        assertFalse(differentLevel.equals(event));

        LoggingEvent differentMdc = new LoggingEvent(level, new HashMap<String, String>(), marker, throwable, message, arg1, arg2);
        assertFalse(event.equals(differentMdc));
        assertFalse(differentMdc.equals(event));

        LoggingEvent differentMarker = new LoggingEvent(level, mdc, mock(Marker.class), throwable, message, arg1, arg2);
        assertFalse(event.equals(differentMarker));
        assertFalse(differentMarker.equals(event));

        LoggingEvent differentThrowable = new LoggingEvent(level, mdc, marker, new Throwable(), message, arg1, arg2);
        assertFalse(event.equals(differentThrowable));
        assertFalse(differentThrowable.equals(event));

        LoggingEvent differentMessage = new LoggingEvent(level, mdc, marker, throwable, "somethingelse", arg1, arg2);
        assertFalse(event.equals(differentMessage));
        assertFalse(differentMessage.equals(event));

        LoggingEvent differentArgs = new LoggingEvent(level, mdc, marker, throwable, message, arg1);
        assertFalse(event.equals(differentArgs));
        assertFalse(differentArgs.equals(event));
    }

    @Test
    public void hashCodeContract() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        LoggingEvent equalEvent = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertEquals(event.hashCode(), equalEvent.hashCode());

        LoggingEvent differentLevel = new LoggingEvent(Level.DEBUG, mdc, marker, throwable, message, arg1, arg2);
        assertFalse(event.hashCode() == differentLevel.hashCode());

        LoggingEvent differentMdc = new LoggingEvent(level, new HashMap<String, String>(), marker, throwable, message, arg1, arg2);
        assertFalse(event.hashCode() == differentMdc.hashCode());

        LoggingEvent differentMarker = new LoggingEvent(level, mdc, mock(Marker.class), throwable, message, arg1, arg2);
        assertFalse(event.hashCode() == differentMarker.hashCode());

        LoggingEvent differentThrowable = new LoggingEvent(level, mdc, marker, new Throwable(), message, arg1, arg2);
        assertFalse(event.hashCode() == differentThrowable.hashCode());

        LoggingEvent differentMessage = new LoggingEvent(level, mdc, marker, throwable, "somethingelse", arg1, arg2);
        assertFalse(event.hashCode() == differentMessage.hashCode());

        LoggingEvent differentArgs = new LoggingEvent(level, mdc, marker, throwable, message, arg1);
        assertFalse(event.hashCode() == differentArgs.hashCode());
    }

    @Test
    public void toStringFormat() {
        LoggingEvent event = new LoggingEvent(level, mdc, marker, throwable, message, arg1, arg2);
        assertEquals("LoggingEvent{level=" + level +
                ", mdc={key=value}, marker=Optional.of(" + marker +
                "), " +
                "throwable=Optional.of(java.lang.Throwable), message='message', arguments=[arg1, arg2]}", event.toString());
    }
}
