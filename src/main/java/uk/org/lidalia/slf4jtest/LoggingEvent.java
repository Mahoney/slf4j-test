package uk.org.lidalia.slf4jtest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Marker;

import com.google.common.base.Optional;

import uk.org.lidalia.slf4jutils.Level;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

public class LoggingEvent {

    private final Level level;
    private final Map<String, String> mdc;
    private final Optional<Marker> marker;
    private final Optional<Throwable> throwable;
    private final String message;
    private final List<Object> arguments;

    public LoggingEvent(Level level, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), null, null, message, arguments);
    }

    public LoggingEvent(Level level, Throwable throwable, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), null, throwable, message, arguments);
    }

    public LoggingEvent(Level level, Marker marker, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), marker, null, message, arguments);
    }

    public LoggingEvent(Level level, Marker marker, Throwable throwable, String message, Object... arguments) {
        this(level, Collections.<String, String>emptyMap(), marker, throwable, message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdcCopy, String message, Object... arguments) {
        this(level, mdcCopy, null, null, message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Throwable throwable, String message, Object... arguments) {
        this(level, mdc, null, throwable, message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Marker marker, String message, Object... arguments) {
        this(level, mdc, marker, null, message, arguments);
    }

    public LoggingEvent(Level level, Map<String, String> mdc, Marker marker, Throwable throwable, String message, Object... arguments) {
        this.level = checkNotNull(level);
        this.mdc = fromNullable(mdc).or(Collections.<String, String>emptyMap());
        this.marker = fromNullable(marker);
        this.throwable = fromNullable(throwable);
        this.message = checkNotNull(message);
        this.arguments = Collections.unmodifiableList(asList(arguments));
    }

    public Level getLevel() {
        return level;
    }

    public Optional<Marker> getMarker() {
        return marker;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getArguments() {
        return arguments;
    }
    public Optional<Throwable> getThrowable() {
        return throwable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoggingEvent that = (LoggingEvent) o;

        if (level != that.level) return false;
        if (!mdc.equals(that.mdc)) return false;
        if (!marker.equals(that.marker)) return false;
        if (!throwable.equals(that.throwable)) return false;
        if (!message.equals(that.message)) return false;
        if (!arguments.equals(that.arguments)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + mdc.hashCode();
        result = 31 * result + marker.hashCode();
        result = 31 * result + throwable.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + arguments.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LoggingEvent{" +
                "level=" + level +
                ", mdc=" + mdc +
                ", marker=" + marker +
                ", throwable=" + throwable +
                ", message='" + message + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
