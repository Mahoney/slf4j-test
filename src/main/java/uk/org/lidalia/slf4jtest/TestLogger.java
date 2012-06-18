package uk.org.lidalia.slf4jtest;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

import uk.org.lidalia.lang.SafeThreadLocal;
import uk.org.lidalia.slf4jutils.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Sets.immutableEnumSet;
import static java.util.Arrays.asList;
import static uk.org.lidalia.slf4jutils.Level.DEBUG;
import static uk.org.lidalia.slf4jutils.Level.ERROR;
import static uk.org.lidalia.slf4jutils.Level.INFO;
import static uk.org.lidalia.slf4jutils.Level.TRACE;
import static uk.org.lidalia.slf4jutils.Level.WARN;
import static uk.org.lidalia.slf4jutils.Level.enablableValueSet;

public class TestLogger implements Logger {

    private final String name;
    private final TestLoggerFactory testLoggerFactory;
    private final SafeThreadLocal<List<LoggingEvent>> loggingEvents = new SafeThreadLocal<List<LoggingEvent>>(new Supplier<List<LoggingEvent>>() {
        @Override
        public List<LoggingEvent> get() {
            return new ArrayList<LoggingEvent>();
        }
    });

    private final List<LoggingEvent> allLoggingEvents = new CopyOnWriteArrayList<LoggingEvent>();
    private volatile ImmutableSet<Level> enabledLevels = enablableValueSet();

    TestLogger(String name, TestLoggerFactory testLoggerFactory) {
        this.name = name;
        this.testLoggerFactory = testLoggerFactory;
    }

    public String getName() {
        return name;
    }

    public void clear() {
        getOrInitialiseLoggingEvents().clear();
        enabledLevels = enablableValueSet();
    }

    public void clearAll() {
        allLoggingEvents.clear();
        loggingEvents.reset();
    }

    public ImmutableList<LoggingEvent> getLoggingEvents() {
        return copyOf(getOrInitialiseLoggingEvents());
    }

    public ImmutableList<LoggingEvent> getAllLoggingEvents() {
        return copyOf(allLoggingEvents);
    }

    public boolean isTraceEnabled() {
        return enabledLevels.contains(TRACE);
    }

    public void trace(String message) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), message));
    }

    public void trace(String format, Object arg) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), format, arg));
    }

    public void trace(String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), format, arg1, arg2));
    }

    public void trace(String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), format, args));
    }

    public void trace(String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), throwable, msg));
    }

    public boolean isTraceEnabled(Marker marker) {
        return enabledLevels.contains(TRACE);
    }

    public void trace(Marker marker, String msg) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), marker, msg));
    }

    public void trace(Marker marker, String format, Object arg) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), marker, format, arg));
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), marker, format, arg1, arg2));
    }

    public void trace(Marker marker, String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), marker, format, args));
    }

    public void trace(Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(TRACE, mdc(), marker, throwable, msg));
    }

    public boolean isDebugEnabled() {
        return enabledLevels.contains(DEBUG);
    }

    public void debug(String message) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), message));
    }

    public void debug(String format, Object arg) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), format, arg));
    }

    public void debug(String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), format, arg1, arg2));
    }

    public void debug(String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), format, args));
    }

    public void debug(String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), throwable, msg));
    }

    public boolean isDebugEnabled(Marker marker) {
        return enabledLevels.contains(DEBUG);
    }

    public void debug(Marker marker, String msg) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), marker, msg));
    }

    public void debug(Marker marker, String format, Object arg) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), marker, format, arg));
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), marker, format, arg1, arg2));
    }

    public void debug(Marker marker, String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), marker, format, args));
    }

    public void debug(Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(DEBUG, mdc(), marker, throwable, msg));
    }

    public boolean isInfoEnabled() {
        return enabledLevels.contains(INFO);
    }

    public void info(String message) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), message));
    }

    public void info(String format, Object arg) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), format, arg));
    }

    public void info(String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), format, arg1, arg2));
    }

    public void info(String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), format, args));
    }

    public void info(String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), throwable, msg));
    }

    public boolean isInfoEnabled(Marker marker) {
        return enabledLevels.contains(INFO);
    }

    public void info(Marker marker, String msg) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), marker, msg));
    }

    public void info(Marker marker, String format, Object arg) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), marker, format, arg));
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), marker, format, arg1, arg2));
    }

    public void info(Marker marker, String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), marker, format, args));
    }

    public void info(Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(INFO, mdc(), marker, throwable, msg));
    }

    public boolean isWarnEnabled() {
        return enabledLevels.contains(WARN);
    }

    public void warn(String message) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), message));
    }

    public void warn(String format, Object arg) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), format, arg));
    }

    public void warn(String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), format, arg1, arg2));
    }

    public void warn(String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), format, args));
    }

    public void warn(String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), throwable, msg));
    }

    public boolean isWarnEnabled(Marker marker) {
        return enabledLevels.contains(WARN);
    }

    public void warn(Marker marker, String msg) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), marker, msg));
    }

    public void warn(Marker marker, String format, Object arg) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), marker, format, arg));
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), marker, format, arg1, arg2));
    }

    public void warn(Marker marker, String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), marker, format, args));
    }

    public void warn(Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(WARN, mdc(), marker, throwable, msg));
    }

    public boolean isErrorEnabled() {
        return enabledLevels.contains(ERROR);
    }

    public void error(String message) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), message));
    }

    public void error(String format, Object arg) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), format, arg));
    }

    public void error(String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), format, arg1, arg2));
    }

    public void error(String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), format, args));
    }

    public void error(String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), throwable, msg));
    }

    public boolean isErrorEnabled(Marker marker) {
        return enabledLevels.contains(ERROR);
    }

    public void error(Marker marker, String msg) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), marker, msg));
    }

    public void error(Marker marker, String format, Object arg) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), marker, format, arg));
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), marker, format, arg1, arg2));
    }

    public void error(Marker marker, String format, Object[] args) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), marker, format, args));
    }

    public void error(Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(new LoggingEvent(ERROR, mdc(), marker, throwable, msg));
    }

    private void addLoggingEvent(LoggingEvent event) {
        if (enabledLevels.contains(event.getLevel())) {
            allLoggingEvents.add(event);
            getOrInitialiseLoggingEvents().add(event);
            testLoggerFactory.addLoggingEvent(event);
        }
    }

    public ImmutableSet<Level> getEnabledLevels() {
        return enabledLevels;
    }

    public void setEnabledLevels(ImmutableSet<Level> enabledLevels) {
        this.enabledLevels = enabledLevels;
    }

    public void setEnabledLevels(Level... enabledLevels) {
        setEnabledLevels(immutableEnumSet(asList(enabledLevels)));
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mdc() {
        return fromNullable(MDC.getCopyOfContextMap()).or(Collections.emptyMap());
    }

    private List<LoggingEvent> getOrInitialiseLoggingEvents() {
        List<LoggingEvent> events = loggingEvents.get();
        loggingEvents.set(events);
        return events;
    }
}
