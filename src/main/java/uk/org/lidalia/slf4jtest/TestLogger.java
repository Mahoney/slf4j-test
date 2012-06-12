package uk.org.lidalia.slf4jtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

import uk.org.lidalia.slf4jutils.Level;

import static com.google.common.base.Optional.fromNullable;

public class TestLogger implements Logger {

    private final String name;
    private final List<LoggingEvent> loggingEvents = new CopyOnWriteArrayList<LoggingEvent>();
    private Level level = Level.TRACE;

    TestLogger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void clear() {
        loggingEvents.clear();
    }

    public List<LoggingEvent> getLoggingEvents() {
        return Collections.unmodifiableList(new ArrayList<LoggingEvent>(loggingEvents));
    }

    public boolean isTraceEnabled() {
        return level.isTraceEnabled();
    }

    public void trace(String message) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), message));
    }

    public void trace(String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), format, arg));
    }

    public void trace(String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), format, arg1, arg2));
    }

    public void trace(String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), format, args));
    }

    public void trace(String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), throwable, msg));
    }

    public boolean isTraceEnabled(Marker marker) {
        return level.isTraceEnabled();
    }

    public void trace(Marker marker, String msg) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), marker, msg));
    }

    public void trace(Marker marker, String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), marker, format, arg));
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), marker, format, arg1, arg2));
    }

    public void trace(Marker marker, String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), marker, format, args));
    }

    public void trace(Marker marker, String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.TRACE, mdc(), marker, throwable, msg));
    }

    public boolean isDebugEnabled() {
        return level.isDebugEnabled();
    }

    public void debug(String message) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), message));
    }

    public void debug(String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), format, arg));
    }

    public void debug(String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), format, arg1, arg2));
    }

    public void debug(String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), format, args));
    }

    public void debug(String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), throwable, msg));
    }

    public boolean isDebugEnabled(Marker marker) {
        return level.isDebugEnabled();
    }

    public void debug(Marker marker, String msg) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), marker, msg));
    }

    public void debug(Marker marker, String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), marker, format, arg));
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), marker, format, arg1, arg2));
    }

    public void debug(Marker marker, String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), marker, format, args));
    }

    public void debug(Marker marker, String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.DEBUG, mdc(), marker, throwable, msg));
    }

    public boolean isInfoEnabled() {
        return level.isInfoEnabled();
    }

    public void info(String message) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), message));
    }

    public void info(String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), format, arg));
    }

    public void info(String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), format, arg1, arg2));
    }

    public void info(String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), format, args));
    }

    public void info(String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), throwable, msg));
    }

    public boolean isInfoEnabled(Marker marker) {
        return level.isInfoEnabled();
    }

    public void info(Marker marker, String msg) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), marker, msg));
    }

    public void info(Marker marker, String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), marker, format, arg));
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), marker, format, arg1, arg2));
    }

    public void info(Marker marker, String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), marker, format, args));
    }

    public void info(Marker marker, String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.INFO, mdc(), marker, throwable, msg));
    }

    public boolean isWarnEnabled() {
        return level.isWarnEnabled();
    }

    public void warn(String message) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), message));
    }

    public void warn(String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), format, arg));
    }

    public void warn(String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), format, arg1, arg2));
    }

    public void warn(String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), format, args));
    }

    public void warn(String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), throwable, msg));
    }

    public boolean isWarnEnabled(Marker marker) {
        return level.isWarnEnabled();
    }

    public void warn(Marker marker, String msg) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), marker, msg));
    }

    public void warn(Marker marker, String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), marker, format, arg));
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), marker, format, arg1, arg2));
    }

    public void warn(Marker marker, String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), marker, format, args));
    }

    public void warn(Marker marker, String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.WARN, mdc(), marker, throwable, msg));
    }

    public boolean isErrorEnabled() {
        return level.isErrorEnabled();
    }

    public void error(String message) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), message));
    }

    public void error(String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), format, arg));
    }

    public void error(String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), format, arg1, arg2));
    }

    public void error(String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), format, args));
    }

    public void error(String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), throwable, msg));
    }

    public boolean isErrorEnabled(Marker marker) {
        return level.isErrorEnabled();
    }

    public void error(Marker marker, String msg) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), marker, msg));
    }

    public void error(Marker marker, String format, Object arg) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), marker, format, arg));
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), marker, format, arg1, arg2));
    }

    public void error(Marker marker, String format, Object[] args) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), marker, format, args));
    }

    public void error(Marker marker, String msg, Throwable throwable) {
        loggingEvents.add(new LoggingEvent(Level.ERROR, mdc(), marker, throwable, msg));
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mdc() {
        return fromNullable(MDC.getCopyOfContextMap()).or(Collections.emptyMap());
    }
}
