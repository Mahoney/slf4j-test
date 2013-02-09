package uk.org.lidalia.slf4jtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.lidalia.lang.ThreadLocal;
import uk.org.lidalia.slf4jext.Level;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Sets.immutableEnumSet;
import static java.util.Arrays.asList;
import static uk.org.lidalia.slf4jext.Level.DEBUG;
import static uk.org.lidalia.slf4jext.Level.ERROR;
import static uk.org.lidalia.slf4jext.Level.INFO;
import static uk.org.lidalia.slf4jext.Level.TRACE;
import static uk.org.lidalia.slf4jext.Level.WARN;
import static uk.org.lidalia.slf4jext.Level.enablableValueSet;

/**
 * Implementation of {@link Logger} which stores {@link LoggingEvent}s in memory and provides methods
 * to access and remove them in order to facilitate writing tests that assert particular logging calls were made.
 *
 * {@link LoggingEvent}s are stored in both an {@link ThreadLocal} and a normal {@link List}. The {@link #getLoggingEvents()}
 * and {@link #clear()} methods reference the {@link ThreadLocal} events. The {@link #getAllLoggingEvents()} and
 * {@link #clearAll()} methods reference all events logged on this Logger.  This is in order to facilitate parallelising
 * tests - tests that use the thread local methods can be parallelised.
 *
 * By default all Levels are enabled.  It is important to note that the conventional hierarchical notion of Levels, where
 * info being enabled implies warn and error being enabled, is not a requirement of the SLF4J API, so the
 * {@link #setEnabledLevels(ImmutableSet)}, {@link #setEnabledLevels(Level...)},
 * {@link #setEnabledLevelsForAllThreads(ImmutableSet)}, {@link #setEnabledLevelsForAllThreads(Level...)} and the various
 * isXxxxxEnabled() methods make no assumptions about this hierarchy.  If you wish to use traditional hierarchical setups you may
 * do so by passing the constants in {@link uk.org.lidalia.slf4jext.ConventionalLevelHierarchy} to
 * {@link #setEnabledLevels(ImmutableSet)} or {@link #setEnabledLevelsForAllThreads(ImmutableSet)}.
 */
public class TestLogger implements Logger { // NOPMD interface has too many methods, we have to implement

    private static final Supplier<ImmutableSet<Level>> ALL_ENABLABLE_LEVELS_SUPPLIER = new Supplier<ImmutableSet<Level>>() {
        @Override
        public ImmutableSet<Level> get() {
            return enablableValueSet();
        }
    };
    private final String name;
    private final TestLoggerFactory testLoggerFactory;
    private final ThreadLocal<List<LoggingEvent>> loggingEvents =
            new ThreadLocal<>(new Supplier<List<LoggingEvent>>() {
        @Override
        public List<LoggingEvent> get() {
            return new ArrayList<>();
        }
    });

    private final List<LoggingEvent> allLoggingEvents = new CopyOnWriteArrayList<LoggingEvent>();
    private volatile ThreadLocal<ImmutableSet<Level>> enabledLevels =
            new ThreadLocal<>(ALL_ENABLABLE_LEVELS_SUPPLIER);

    TestLogger(final String name, final TestLoggerFactory testLoggerFactory) {
        this.name = name;
        this.testLoggerFactory = testLoggerFactory;
    }

    public String getName() {
        return name;
    }

    /**
     * Removed all {@link LoggingEvent}s logged by this thread and resets the enabled levels of the logger
     * to {@link uk.org.lidalia.slf4jext.Level#enablableValueSet()} for this thread.
     */
    public void clear() {
        loggingEvents.get().clear();
        enabledLevels.remove();
    }

    /**
     * Removed ALL {@link LoggingEvent}s logged on this logger, regardless of thread,
     * and resets the enabled levels of the logger to {@link uk.org.lidalia.slf4jext.Level#enablableValueSet()}
     * for ALL threads.
     */
    public void clearAll() {
        allLoggingEvents.clear();
        loggingEvents.reset();
        enabledLevels = new ThreadLocal<>(ALL_ENABLABLE_LEVELS_SUPPLIER);
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
    @Override
    public boolean isTraceEnabled() {
        return enabledLevels.get().contains(TRACE);
    }

    @Override
    public void trace(final String message) {
        log(TRACE, message);
    }

    @Override
    public void trace(final String format, final Object arg) {
        log(TRACE, format, arg);
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        log(TRACE, format, arg1, arg2);
    }

    @Override
    public void trace(final String format, final Object... args) {
        log(TRACE, format, args);
    }

    @Override
    public void trace(final String msg, final Throwable throwable) {
        log(TRACE, msg, throwable);
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return enabledLevels.get().contains(TRACE);
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        log(TRACE, marker, msg);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        log(TRACE, marker, format, arg);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log(TRACE, marker, format, arg1, arg2);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object... args) {
        log(TRACE, marker, format, args);
    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable throwable) {
        log(TRACE, marker, msg, throwable);
    }

    /**
     * @return whether this logger is debug enabled in this thread
     */
    @Override
    public boolean isDebugEnabled() {
        return enabledLevels.get().contains(DEBUG);
    }

    @Override
    public void debug(final String message) {
        log(DEBUG, message);
    }

    @Override
    public void debug(final String format, final Object arg) {
        log(DEBUG, format, arg);
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        log(DEBUG, format, arg1, arg2);
    }

    @Override
    public void debug(final String format, final Object... args) {
        log(DEBUG, format, args);
    }

    @Override
    public void debug(final String msg, final Throwable throwable) {
        log(DEBUG, msg, throwable);
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return enabledLevels.get().contains(DEBUG);
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        log(DEBUG, marker, msg);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        log(DEBUG, marker, format, arg);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log(DEBUG, marker, format, arg1, arg2);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object... args) {
        log(DEBUG, marker, format, args);
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable throwable) {
        log(DEBUG, marker, msg, throwable);
    }

    /**
     * @return whether this logger is info enabled in this thread
     */
    @Override
    public boolean isInfoEnabled() {
        return enabledLevels.get().contains(INFO);
    }

    @Override
    public void info(final String message) {
        log(INFO, message);
    }

    @Override
    public void info(final String format, final Object arg) {
        log(INFO, format, arg);
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        log(INFO, format, arg1, arg2);
    }

    @Override
    public void info(final String format, final Object... args) {
        log(INFO, format, args);
    }

    @Override
    public void info(final String msg, final Throwable throwable) {
        log(INFO, msg, throwable);
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return enabledLevels.get().contains(INFO);
    }

    @Override
    public void info(final Marker marker, final String msg) {
        log(INFO, marker, msg);
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        log(INFO, marker, format, arg);
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log(INFO, marker, format, arg1, arg2);
    }

    @Override
    public void info(final Marker marker, final String format, final Object... args) {
        log(INFO, marker, format, args);
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable throwable) {
        log(INFO, marker, msg, throwable);
    }

    /**
     * @return whether this logger is warn enabled in this thread
     */
    @Override
    public boolean isWarnEnabled() {
        return enabledLevels.get().contains(WARN);
    }

    @Override
    public void warn(final String message) {
        log(WARN, message);
    }

    @Override
    public void warn(final String format, final Object arg) {
        log(WARN, format, arg);
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        log(WARN, format, arg1, arg2);
    }

    @Override
    public void warn(final String format, final Object... args) {
        log(WARN, format, args);
    }

    @Override
    public void warn(final String msg, final Throwable throwable) {
        log(WARN, msg, throwable);
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return enabledLevels.get().contains(WARN);
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        log(WARN, marker, msg);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        log(WARN, marker, format, arg);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log(WARN, marker, format, arg1, arg2);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object... args) {
        log(WARN, marker, format, args);
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable throwable) {
        log(WARN, marker, msg, throwable);
    }

    /**
     * @return whether this logger is error enabled in this thread
     */
    @Override
    public boolean isErrorEnabled() {
        return enabledLevels.get().contains(ERROR);
    }

    @Override
    public void error(final String message) {
        log(ERROR, message);
    }

    @Override
    public void error(final String format, final Object arg) {
        log(ERROR, format, arg);
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        log(ERROR, format, arg1, arg2);
    }

    @Override
    public void error(final String format, final Object... args) {
        log(ERROR, format, args);
    }

    @Override
    public void error(final String msg, final Throwable throwable) {
        log(ERROR, msg, throwable);
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return enabledLevels.get().contains(ERROR);
    }

    @Override
    public void error(final Marker marker, final String msg) {
        log(ERROR, marker, msg);
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        log(ERROR, marker, format, arg);
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log(ERROR, marker, format, arg1, arg2);
    }

    @Override
    public void error(final Marker marker, final String format, final Object... args) {
        log(ERROR, marker, format, args);
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable throwable) {
        log(ERROR, marker, msg, throwable);
    }

    private void log(final Level level, final String format, final Object... args) {
        addLoggingEvent(level, Optional.<Marker>absent(), Optional.<Throwable>absent(), format, args);
    }

    private void log(final Level level, final String msg, final Throwable throwable) { //NOPMD PMD wrongly thinks unused...
        addLoggingEvent(level, Optional.<Marker>absent(), of(throwable), msg);
    }

    private void log(final Level level, final Marker marker, final String format, final Object... args) {
        addLoggingEvent(level, of(marker), Optional.<Throwable>absent(), format, args);
    }

    private void log(final Level level, final Marker marker, final String msg, final Throwable throwable) {
        addLoggingEvent(level, of(marker), of(throwable), msg);
    }

    private void addLoggingEvent(
            final Level level,
            final Optional<Marker> marker,
            final Optional<Throwable> throwable,
            final String format,
            final Object... args) {
        if (enabledLevels.get().contains(level)) {
            final LoggingEvent event = new LoggingEvent(of(this), level, mdc(), marker, throwable, format, args);
            allLoggingEvents.add(event);
            loggingEvents.get().add(event);
            testLoggerFactory.addLoggingEvent(event);
            optionallyPrint(event);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mdc() {
        return fromNullable(MDC.getCopyOfContextMap()).or(Collections.emptyMap());
    }

    private void optionallyPrint(final LoggingEvent event) {
        if (testLoggerFactory.getPrintLevel().compareTo(event.getLevel()) <= 0) {
            event.print();
        }
    }

    /**
     * @return the set of levels enabled for this logger on this thread
     */
    public ImmutableSet<Level> getEnabledLevels() {
        return enabledLevels.get();
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by using the constants in
     * {@link uk.org.lidalia.slf4jext.ConventionalLevelHierarchy}
     *
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(final ImmutableSet<Level> enabledLevels) {
        this.enabledLevels.set(enabledLevels);
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by passing the constants in
     * {@link uk.org.lidalia.slf4jext.ConventionalLevelHierarchy} to {@link #setEnabledLevels(ImmutableSet)}
     *
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(final Level... enabledLevels) {
        setEnabledLevels(immutableEnumSet(asList(enabledLevels)));
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by using the constants in
     * {@link uk.org.lidalia.slf4jext.ConventionalLevelHierarchy}
     *
     * @param enabledLevelsForAllThreads levels which will be considered enabled for this logger IN ALL THREADS
     */
    public void setEnabledLevelsForAllThreads(final ImmutableSet<Level> enabledLevelsForAllThreads) {
        this.enabledLevels = new ThreadLocal<ImmutableSet<Level>>(new Supplier<ImmutableSet<Level>>() {
            @Override
            public ImmutableSet<Level> get() {
                return enabledLevelsForAllThreads;
            }
        });
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by passing the constants in
     * {@link uk.org.lidalia.slf4jext.ConventionalLevelHierarchy} to {@link #setEnabledLevelsForAllThreads(ImmutableSet)}
     *
     * @param enabledLevelsForAllThreads levels which will be considered enabled for this logger IN ALL THREADS
     */
    public void setEnabledLevelsForAllThreads(final Level... enabledLevelsForAllThreads) {
        setEnabledLevelsForAllThreads(ImmutableSet.copyOf(enabledLevelsForAllThreads));
    }
}
