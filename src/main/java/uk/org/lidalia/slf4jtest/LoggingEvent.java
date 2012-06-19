package uk.org.lidalia.slf4jtest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.joda.time.DateTime;
import org.slf4j.Marker;
import uk.org.lidalia.lang.Identity;
import uk.org.lidalia.lang.RichObject;
import uk.org.lidalia.slf4jutils.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Representation of a call to a logger for test assertion purposes.
 *
 * The contract of {@link #equals(Object)} and {@link #hashCode} is that they compare the results of:
 * <ul>
 *     <li>{@link #getLevel()}</li>
 *     <li>{@link #getMdc()}</li>
 *     <li>{@link #getMarker()}</li>
 *     <li>{@link #getThrowable()}</li>
 *     <li>{@link #getMessage()}</li>
 *     <li>{@link #getArguments()}</li>
 * </ul>
 *
 * They do NOT compare the results of {@link #getTimestamp()} as this would render it impractical to create
 * appropriate expected {@link LoggingEvent}s to compare against.
 *
 * Constructors and convenient static factory methods exist to create {@link LoggingEvent}s with appropriate
 * defaults.  These are not documented further as they should be self-evident.
 */
public class LoggingEvent extends RichObject {

    public static LoggingEvent trace(String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, message, arguments);
    }

    public static LoggingEvent trace(Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, throwable, message, arguments);
    }

    public static LoggingEvent trace(Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, marker, message, arguments);
    }

    public static LoggingEvent trace(Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, marker, throwable, message, arguments);
    }

    public static LoggingEvent trace(Map<String, String> mdcCopy, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdcCopy, message, arguments);
    }

    public static LoggingEvent trace(Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, throwable, message, arguments);
    }

    public static LoggingEvent trace(Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, marker, message, arguments);
    }

    public static LoggingEvent trace(Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent debug(String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, message, arguments);
    }

    public static LoggingEvent debug(Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, throwable, message, arguments);
    }

    public static LoggingEvent debug(Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, marker, message, arguments);
    }

    public static LoggingEvent debug(Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, marker, throwable, message, arguments);
    }

    public static LoggingEvent debug(Map<String, String> mdcCopy, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdcCopy, message, arguments);
    }

    public static LoggingEvent debug(Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, throwable, message, arguments);
    }

    public static LoggingEvent debug(Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, marker, message, arguments);
    }

    public static LoggingEvent debug(Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent info(String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, message, arguments);
    }

    public static LoggingEvent info(Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, throwable, message, arguments);
    }

    public static LoggingEvent info(Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, marker, message, arguments);
    }

    public static LoggingEvent info(Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, marker, throwable, message, arguments);
    }

    public static LoggingEvent info(Map<String, String> mdcCopy, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, mdcCopy, message, arguments);
    }

    public static LoggingEvent info(Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, throwable, message, arguments);
    }

    public static LoggingEvent info(Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, marker, message, arguments);
    }

    public static LoggingEvent info(Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent warn(String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, message, arguments);
    }

    public static LoggingEvent warn(Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, throwable, message, arguments);
    }

    public static LoggingEvent warn(Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, marker, message, arguments);
    }

    public static LoggingEvent warn(Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, marker, throwable, message, arguments);
    }

    public static LoggingEvent warn(Map<String, String> mdcCopy, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, mdcCopy, message, arguments);
    }

    public static LoggingEvent warn(Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, throwable, message, arguments);
    }

    public static LoggingEvent warn(Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, marker, message, arguments);
    }

    public static LoggingEvent warn(Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent error(String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, message, arguments);
    }

    public static LoggingEvent error(Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, throwable, message, arguments);
    }

    public static LoggingEvent error(Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, marker, message, arguments);
    }

    public static LoggingEvent error(Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, marker, throwable, message, arguments);
    }

    public static LoggingEvent error(Map<String, String> mdcCopy, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdcCopy, message, arguments);
    }

    public static LoggingEvent error(Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, throwable, message, arguments);
    }

    public static LoggingEvent error(Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, marker, message, arguments);
    }

    public static LoggingEvent error(Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, marker, throwable, message, arguments);
    }

    public LoggingEvent(Level level, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), Optional.<Marker>absent(), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(Level level, Throwable throwable, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), Optional.<Marker>absent(), of(throwable), message, arguments);
    }

    public LoggingEvent(Level level, Marker marker, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), of(marker), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(Level level, Marker marker, Throwable throwable, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), of(marker), of(throwable), message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdcCopy, String message, Object... arguments) {
        this(level, mdcCopy, Optional.<Marker>absent(), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        this(level, mdc, Optional.<Marker>absent(), of(throwable), message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        this(level, mdc, of(marker), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        this(level, mdc, of(marker), of(throwable), message, arguments);
    }

    private LoggingEvent(Level level, Map<String, String> mdc, Optional<Marker> marker, Optional<Throwable> throwable, String message, Object... arguments) {
        this.level = checkNotNull(level);
        this.mdc = ImmutableMap.copyOf(mdc);
        this.marker = checkNotNull(marker);
        this.throwable = checkNotNull(throwable);
        this.message = checkNotNull(message);
        this.arguments = ImmutableList.copyOf(arguments);
    }

    @Identity private final Level level;
    @Identity private final ImmutableMap<String, String> mdc;
    @Identity private final Optional<Marker> marker;
    @Identity private final Optional<Throwable> throwable;
    @Identity private final String message;
    @Identity private final ImmutableList<Object> arguments;

    private final DateTime timestamp = new DateTime();

    public Level getLevel() {
        return level;
    }

    public ImmutableMap<String, String> getMdc() {
        return mdc;
    }

    public Optional<Marker> getMarker() {
        return marker;
    }

    public String getMessage() {
        return message;
    }

    public ImmutableList<Object> getArguments() {
        return arguments;
    }

    public Optional<Throwable> getThrowable() {
        return throwable;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }
}
