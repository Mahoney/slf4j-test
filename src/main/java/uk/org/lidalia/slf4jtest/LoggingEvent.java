package uk.org.lidalia.slf4jtest;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;

import org.joda.time.Instant;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.lidalia.lang.Identity;
import uk.org.lidalia.lang.RichObject;
import uk.org.lidalia.slf4jext.Level;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

/**
 * <p>
 * Representation of a call to a logger for test assertion purposes.
 * </p>
 * The contract of {@link #equals(Object)} and {@link #hashCode} is that they compare the results of:
 * <ul>
 * <li>{@link #getLevel()}</li>
 * <li>{@link #getMdc()}</li>
 * <li>{@link #getMarker()}</li>
 * <li>{@link #getThrowable()}</li>
 * <li>{@link #getMessage()}</li>
 * <li>{@link #getArguments()}</li>
 * </ul>
 * <p>
 * They do NOT compare the results of {@link #getTimestamp()} or {@link #getCreatingLogger()} as this would render it impractical
 * to create appropriate expected {@link LoggingEvent}s to compare against.
 * </p>
 * <p>
 * Constructors and convenient static factory methods exist to create {@link LoggingEvent}s with appropriate
 * defaults.  These are not documented further as they should be self-evident.
 * </p>
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.TooManyMethods" })
public class LoggingEvent extends RichObject {

    public static LoggingEvent trace(final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, message, arguments);
    }

    public static LoggingEvent trace(final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, throwable, message, arguments);
    }

    public static LoggingEvent trace(final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, marker, message, arguments);
    }

    public static LoggingEvent trace(
            final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, marker, throwable, message, arguments);
    }

    public static LoggingEvent trace(final Map<String, String> mdc, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, message, arguments);
    }

    public static LoggingEvent trace(
            final Map<String, String> mdc, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, throwable, message, arguments);
    }

    public static LoggingEvent trace(
            final Map<String, String> mdc, final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, marker, message, arguments);
    }

    public static LoggingEvent trace(
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        return new LoggingEvent(Level.TRACE, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent debug(final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, message, arguments);
    }

    public static LoggingEvent debug(final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, throwable, message, arguments);
    }

    public static LoggingEvent debug(final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, marker, message, arguments);
    }

    public static LoggingEvent debug(
            final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, marker, throwable, message, arguments);
    }

    public static LoggingEvent debug(final Map<String, String> mdc, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, message, arguments);
    }

    public static LoggingEvent debug(
            final Map<String, String> mdc, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, throwable, message, arguments);
    }

    public static LoggingEvent debug(
            final Map<String, String> mdc, final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, marker, message, arguments);
    }

    public static LoggingEvent debug(
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        return new LoggingEvent(Level.DEBUG, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent info(final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, message, arguments);
    }

    public static LoggingEvent info(final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, throwable, message, arguments);
    }

    public static LoggingEvent info(final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, marker, message, arguments);
    }

    public static LoggingEvent info(
            final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, marker, throwable, message, arguments);
    }

    public static LoggingEvent info(final Map<String, String> mdc, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, message, arguments);
    }

    public static LoggingEvent info(
            final Map<String, String> mdc, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, throwable, message, arguments);
    }

    public static LoggingEvent info(
            final Map<String, String> mdc, final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, marker, message, arguments);
    }

    public static LoggingEvent info(
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        return new LoggingEvent(Level.INFO, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent warn(final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, message, arguments);
    }

    public static LoggingEvent warn(final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, throwable, message, arguments);
    }

    public static LoggingEvent warn(final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, marker, message, arguments);
    }

    public static LoggingEvent warn(
            final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, marker, throwable, message, arguments);
    }

    public static LoggingEvent warn(final Map<String, String> mdc, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, message, arguments);
    }

    public static LoggingEvent warn(
            final Map<String, String> mdc, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, throwable, message, arguments);
    }

    public static LoggingEvent warn(
            final Map<String, String> mdc, final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, marker, message, arguments);
    }

    public static LoggingEvent warn(
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        return new LoggingEvent(Level.WARN, mdc, marker, throwable, message, arguments);
    }

    public static LoggingEvent error(final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, message, arguments);
    }

    public static LoggingEvent error(final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, throwable, message, arguments);
    }

    public static LoggingEvent error(final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, marker, message, arguments);
    }

    public static LoggingEvent error(
            final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, marker, throwable, message, arguments);
    }

    public static LoggingEvent error(final Map<String, String> mdc, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, message, arguments);
    }

    public static LoggingEvent error(
            final Map<String, String> mdc, final Throwable throwable, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, throwable, message, arguments);
    }

    public static LoggingEvent error(
            final Map<String, String> mdc, final Marker marker, final String message, final Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, marker, message, arguments);
    }

    public static LoggingEvent error(
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        return new LoggingEvent(Level.ERROR, mdc, marker, throwable, message, arguments);
    }

    public LoggingEvent(final Level level, final String message, final Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), Optional.<Marker>absent(), Optional.<Throwable>absent(),
                message, arguments);
    }

    public LoggingEvent(final Level level, final Throwable throwable, final String message, final Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), Optional.<Marker>absent(), fromNullable(throwable),
                message, arguments);
    }

    public LoggingEvent(final Level level, final Marker marker, final String message, final Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), fromNullable(marker), Optional.<Throwable>absent(),
                message, arguments);
    }

    public LoggingEvent(
            final Level level, final Marker marker, final Throwable throwable, final String message, final Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), fromNullable(marker), fromNullable(throwable), message, arguments);
    }

    public LoggingEvent(final Level level, final Map<String, String> mdc, final String message, final Object... arguments) {
        this(level, mdc, Optional.<Marker>absent(), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(
            final Level level,
            final Map<String, String> mdc,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        this(level, mdc, Optional.<Marker>absent(), fromNullable(throwable), message, arguments);
    }

    public LoggingEvent(
            final Level level,
            final Map<String, String> mdc,
            final Marker marker,
            final String message,
            final Object... arguments) {
        this(level, mdc, fromNullable(marker), Optional.<Throwable>absent(), message, arguments);
    }

    public LoggingEvent(
            final Level level,
            final Map<String, String> mdc,
            final Marker marker,
            final Throwable throwable,
            final String message,
            final Object... arguments) {
        this(level, mdc, fromNullable(marker), fromNullable(throwable), message, arguments);
    }

    private LoggingEvent(
            final Level level,
            final Map<String, String> mdc,
            final Optional<Marker> marker,
            final Optional<Throwable> throwable,
            final String message,
            final Object... arguments) {
        this(Optional.<TestLogger>absent(), level, mdc, marker, throwable, message, arguments);
    }

    LoggingEvent(
            final Optional<TestLogger> creatingLogger,
            final Level level,
            final Map<String, String> mdc,
            final Optional<Marker> marker,
            final Optional<Throwable> throwable,
            final String message,
            final Object... arguments) {
        super();
        this.creatingLogger = creatingLogger;
        this.level = checkNotNull(level);
        this.mdc = ImmutableMap.copyOf(mdc);
        this.marker = checkNotNull(marker);
        this.throwable = checkNotNull(throwable);
        this.message = message;
        this.arguments = from(asList(arguments)).transform(TO_NON_NULL_VALUE).toList();
    }

    private static final Function<Object, Object> TO_NON_NULL_VALUE = new Function<Object, Object>() {
        @Override
        public Object apply(final Object input) {
            return fromNullable(input).or((Object) absent());
        }
    };

    @Identity private final Level level;
    @Identity private final ImmutableMap<String, String> mdc;
    @Identity private final Optional<Marker> marker;
    @Identity private final Optional<Throwable> throwable;
    @Identity /* @Nullable */ private final String message;
    @Identity private final ImmutableList<Object> arguments;

    private final Optional<TestLogger> creatingLogger;
    private final Instant timestamp = new Instant();
    private final String threadName = Thread.currentThread().getName();

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

    /**
     * @return the logger that created this logging event.
     * @throws IllegalStateException if this logging event was not created by a logger
     */
    public TestLogger getCreatingLogger() {
        return creatingLogger.get();
    }

    /**
     * @return the time at which this logging event was created
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @return the name of the thread that created this logging event
     */
    public String getThreadName() {
        return threadName;
    }

    void print() {
        final PrintStream output = printStreamForLevel();
        output.println(formatLogStatement());
        throwable.transform(printThrowableTo(output));
    }

    private static Function<Throwable, String> printThrowableTo(final PrintStream output) {
        return new Function<Throwable, String>() {
            @Override
            public String apply(final Throwable throwableToPrint) {
                throwableToPrint.printStackTrace(output);
                return "";
            }
        };
    }

    private String formatLogStatement() {
        return getTimestamp() + " [" + getThreadName() + "] " + getLevel() + safeLoggerName() + " - " + getFormattedMessage();
    }

    private String safeLoggerName() {
        return creatingLogger.transform(toLoggerNameString).or("");
    }

    private static final Function<TestLogger, String> toLoggerNameString = new Function<TestLogger, String>() {
        @Override
        public String apply(final TestLogger logger) {
            return " " + logger.getName();
        }
    };

    private String getFormattedMessage() {
        return MessageFormatter.arrayFormat(getMessage(), getArguments().toArray()).getMessage();
    }

    private PrintStream printStreamForLevel() {
        switch (level) {
            case ERROR:
            case WARN:
                return System.err;
            default:
                return System.out;
        }
    }
}
