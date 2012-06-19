package uk.org.lidalia.slf4jtest;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

import uk.org.lidalia.lang.ThreadLocal;
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

/**
 * Implementation of {@link Logger} which stores {@link LoggingEvent}s in memory and provides methods
 * to access and remove them in order to facilitate writing tests that assert particular logging calls were made.
 *
 * {@link LoggingEvent}s are stored in both an {@link ThreadLocal} and a normal {@link List}. The {@link #getLoggingEvents()}
 * and {@link #clear()} methods reference the {@link ThreadLocal} events. The {@link #getAllLoggingEvents()} and
 * {@link #clearAll()} methods reference all events logged on this Logger.  This is in order to facilitate parallelising
 * tests - tests that use the thread local methods can be parallelised.
 *
 * By default all Levels are enabled.
 */
public class TestLogger implements Logger {

    private static final Supplier<ImmutableSet<Level>> ALL_ENABLABLE_LEVELS_SUPPLIER = new Supplier<ImmutableSet<Level>>() {
        @Override
        public ImmutableSet<Level> get() {
            return enablableValueSet();
        }
    };
    private final String name;
    private final TestLoggerFactory testLoggerFactory;
    private final ThreadLocal<List<LoggingEvent>> loggingEvents = new ThreadLocal<List<LoggingEvent>>(new Supplier<List<LoggingEvent>>() {
        @Override
        public List<LoggingEvent> get() {
            return new ArrayList<LoggingEvent>();
        }
    });

    private final List<LoggingEvent> allLoggingEvents = new CopyOnWriteArrayList<LoggingEvent>();
    private volatile ThreadLocal<ImmutableSet<Level>> enabledLevels = new ThreadLocal<ImmutableSet<Level>>(ALL_ENABLABLE_LEVELS_SUPPLIER);

    TestLogger(String name, TestLoggerFactory testLoggerFactory) {
        this.name = name;
        this.testLoggerFactory = testLoggerFactory;
    }

    public String getName() {
        return name;
    }

    /**
     * Removed all {@link LoggingEvent}s logged by this thread and resets the enabled levels of the logger
     * to {@link uk.org.lidalia.slf4jutils.Level#enablableValueSet()} for this thread.
     */
    public void clear() {
        loggingEvents.get().clear();
        enabledLevels.remove();
    }

    /**
     * Removed ALL {@link LoggingEvent}s logged on this logger, regardless of thread,
     * and resets the enabled levels of the logger to {@link uk.org.lidalia.slf4jutils.Level#enablableValueSet()}
     * for ALL threads.
     */
    public void clearAll() {
        allLoggingEvents.clear();
        loggingEvents.reset();
        enabledLevels = new ThreadLocal<ImmutableSet<Level>>(ALL_ENABLABLE_LEVELS_SUPPLIER);
    }

    /**
     * @return all {@link LoggingEvent}s logged on this logger by this thread
     */
    public ImmutableList<LoggingEvent> getLoggingEvents() {
        return copyOf(loggingEvents.get());
    }

    /**
     * @return all {@link LoggingEvent}s logged on this logger by ANY thread
     */
    public ImmutableList<LoggingEvent> getAllLoggingEvents() {
        return copyOf(allLoggingEvents);
    }

    /**
     * @return whether this logger is trace enabled in this thread
     */
    public boolean isTraceEnabled() {
        return enabledLevels.get().contains(TRACE);
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
        return enabledLevels.get().contains(TRACE);
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

    /**
     * @return whether this logger is debug enabled in this thread
     */
    public boolean isDebugEnabled() {
        return enabledLevels.get().contains(DEBUG);
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
        return enabledLevels.get().contains(DEBUG);
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

    /**
     * @return whether this logger is info enabled in this thread
     */
    public boolean isInfoEnabled() {
        return enabledLevels.get().contains(INFO);
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
        return enabledLevels.get().contains(INFO);
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

    /**
     * @return whether this logger is warn enabled in this thread
     */
    public boolean isWarnEnabled() {
        return enabledLevels.get().contains(WARN);
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
        return enabledLevels.get().contains(WARN);
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

    /**
     * @return whether this logger is error enabled in this thread
     */
    public boolean isErrorEnabled() {
        return enabledLevels.get().contains(ERROR);
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
        return enabledLevels.get().contains(ERROR);
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
        if (enabledLevels.get().contains(event.getLevel())) {
            allLoggingEvents.add(event);
            loggingEvents.get().add(event);
            testLoggerFactory.addLoggingEvent(event);
        }
    }

    /**
     * @return the set of levels enabled for this logger on this thread
     */
    public ImmutableSet<Level> getEnabledLevels() {
        return enabledLevels.get();
    }

    /**
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(ImmutableSet<Level> enabledLevels) {
        this.enabledLevels.set(enabledLevels);
    }

    /**
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(Level... enabledLevels) {
        setEnabledLevels(immutableEnumSet(asList(enabledLevels)));
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mdc() {
        return fromNullable(MDC.getCopyOfContextMap()).or(Collections.emptyMap());
    }

    /**
     * @param enabledLevels levels which will be considered enabled for this logger IN ALL THREADS
     */
    public void setEnabledLevelsForAllThreads(final ImmutableSet<Level> enabledLevels) {
        this.enabledLevels = new ThreadLocal<ImmutableSet<Level>>(new Supplier<ImmutableSet<Level>>() {
            @Override
            public ImmutableSet<Level> get() {
                return enabledLevels;
            }
        });
    }

    /**
     * @param enabledLevels levels which will be considered enabled for this logger IN ALL THREADS
     */
    public void setEnabledLevelsForAllThreads(final Level... enabledLevels) {
        setEnabledLevelsForAllThreads(ImmutableSet.copyOf(enabledLevels));
    }
}
