package uk.org.lidalia.slf4jtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Marker;

import com.google.common.base.Optional;

import uk.org.lidalia.slf4jutils.Level;

import static com.google.common.base.Optional.of;
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
        this.mdc = Collections.unmodifiableMap(new HashMap<String, String>(mdc));
        this.marker = checkNotNull(marker);
        this.throwable = checkNotNull(throwable);
        this.message = checkNotNull(message);
        this.arguments = Collections.unmodifiableList(new ArrayList<Object>(asList(arguments)));
    }

    public Level getLevel() {
        return level;
    }

    public Map<String, String> getMdc() {
        return mdc;
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
