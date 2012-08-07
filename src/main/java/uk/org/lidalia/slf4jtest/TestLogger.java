package uk.org.lidalia.slf4jtest;

import com.google.common.base.Optional;
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
import static com.google.common.base.Optional.of;
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
 * By default all Levels are enabled.  It is important to note that the conventional hierarchical notion of Levels, where
 * info being enabled implies warn and error being enabled, is not a requirement of the SLF4J API, so the
 * {@link #setEnabledLevels(ImmutableSet)}, {@link #setEnabledLevels(Level...)},
 * {@link #setEnabledLevelsForAllThreads(ImmutableSet)}, {@link #setEnabledLevelsForAllThreads(Level...)} and the various
 * isXxxxxEnabled() methods make no assumptions about this hierarchy.  If you wish to use traditional hierarchical setups you may
 * do so by passing the constants in {@link uk.org.lidalia.slf4jutils.ConventionalLevelHierarchy} to
 * {@link #setEnabledLevels(ImmutableSet)} or {@link #setEnabledLevelsForAllThreads(ImmutableSet)}.
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
        log(TRACE, message);
    }

    public void trace(String format, Object arg) {
        log(TRACE, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        log(TRACE, format, arg1, arg2);
    }

    public void trace(String format, Object[] args) {
        log(TRACE, format, args);
    }

    public void trace(String msg, Throwable throwable) {
        log(TRACE, msg, throwable);
    }

    public boolean isTraceEnabled(Marker marker) {
        return enabledLevels.get().contains(TRACE);
    }

    public void trace(Marker marker, String msg) {
        log(TRACE, marker, msg);
    }

    public void trace(Marker marker, String format, Object arg) {
        log(TRACE, marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        log(TRACE, marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object[] args) {
        log(TRACE, marker, format, args);
    }

    public void trace(Marker marker, String msg, Throwable throwable) {
        log(TRACE, marker, msg, throwable);
    }

    /**
     * @return whether this logger is debug enabled in this thread
     */
    public boolean isDebugEnabled() {
        return enabledLevels.get().contains(DEBUG);
    }

    public void debug(String message) {
        log(DEBUG, message);
    }

    public void debug(String format, Object arg) {
        log(DEBUG, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        log(DEBUG, format, arg1, arg2);
    }

    public void debug(String format, Object[] args) {
        log(DEBUG, format, args);
    }

    public void debug(String msg, Throwable throwable) {
        log(DEBUG, msg, throwable);
    }

    public boolean isDebugEnabled(Marker marker) {
        return enabledLevels.get().contains(DEBUG);
    }

    public void debug(Marker marker, String msg) {
        log(DEBUG, marker, msg);
    }

    public void debug(Marker marker, String format, Object arg) {
        log(DEBUG, marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        log(DEBUG, marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object[] args) {
        log(DEBUG, marker, format, args);
    }

    public void debug(Marker marker, String msg, Throwable throwable) {
        log(DEBUG, marker, msg, throwable);
    }

    /**
     * @return whether this logger is info enabled in this thread
     */
    public boolean isInfoEnabled() {
        return enabledLevels.get().contains(INFO);
    }

    public void info(String message) {
        log(INFO, message);
    }

    public void info(String format, Object arg) {
        log(INFO, format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        log(INFO, format, arg1, arg2);
    }

    public void info(String format, Object[] args) {
        log(INFO, format, args);
    }

    public void info(String msg, Throwable throwable) {
        log(INFO, msg, throwable);
    }

    public boolean isInfoEnabled(Marker marker) {
        return enabledLevels.get().contains(INFO);
    }

    public void info(Marker marker, String msg) {
        log(INFO, marker, msg);
    }

    public void info(Marker marker, String format, Object arg) {
        log(INFO, marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        log(INFO, marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object[] args) {
        log(INFO, marker, format, args);
    }

    public void info(Marker marker, String msg, Throwable throwable) {
        log(INFO, marker, msg, throwable);
    }

    /**
     * @return whether this logger is warn enabled in this thread
     */
    public boolean isWarnEnabled() {
        return enabledLevels.get().contains(WARN);
    }

    public void warn(String message) {
        log(WARN, message);
    }

    public void warn(String format, Object arg) {
        log(WARN, format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        log(WARN, format, arg1, arg2);
    }

    public void warn(String format, Object[] args) {
        log(WARN, format, args);
    }

    public void warn(String msg, Throwable throwable) {
        log(WARN, msg, throwable);
    }

    public boolean isWarnEnabled(Marker marker) {
        return enabledLevels.get().contains(WARN);
    }

    public void warn(Marker marker, String msg) {
        log(WARN, marker, msg);
    }

    public void warn(Marker marker, String format, Object arg) {
        log(WARN, marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        log(WARN, marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object[] args) {
        log(WARN, marker, format, args);
    }

    public void warn(Marker marker, String msg, Throwable throwable) {
        log(WARN, marker, msg, throwable);
    }

    /**
     * @return whether this logger is error enabled in this thread
     */
    public boolean isErrorEnabled() {
        return enabledLevels.get().contains(ERROR);
    }

    public void error(String message) {
        log(ERROR, message);
    }

    public void error(String format, Object arg) {
        log(ERROR, format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        log(ERROR, format, arg1, arg2);
    }

    public void error(String format, Object[] args) {
        log(ERROR, format, args);
    }

    public void error(String msg, Throwable throwable) {
        log(ERROR, msg, throwable);
    }

    public boolean isErrorEnabled(Marker marker) {
        return enabledLevels.get().contains(ERROR);
    }

    public void error(Marker marker, String msg) {
        log(ERROR, marker, msg);
    }

    public void error(Marker marker, String format, Object arg) {
        log(ERROR, marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        log(ERROR, marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object[] args) {
        log(ERROR, marker, format, args);
    }

    public void error(Marker marker, String msg, Throwable throwable) {
        log(ERROR, marker, msg, throwable);
    }

    private void log(Level level, String format, Object... args) {
        addLoggingEvent(level, Optional.<Marker>absent(), Optional.<Throwable>absent(), format, args);
    }

    private void log(Level level, String msg, Throwable throwable) {
        addLoggingEvent(level, Optional.<Marker>absent(), of(throwable), msg);
    }

    private void log(Level level, Marker marker, String format, Object... args) {
        addLoggingEvent(level, of(marker), Optional.<Throwable>absent(), format, args);
    }

    private void log(Level level, Marker marker, String msg, Throwable throwable) {
        addLoggingEvent(level, of(marker), of(throwable), msg);
    }

    private void addLoggingEvent(Level level, Optional<Marker> marker, Optional<Throwable> throwable, String format, Object... args) {
        if (enabledLevels.get().contains(level)) {
            LoggingEvent event = new LoggingEvent(of(this), level, mdc(), marker, throwable, format, args);
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

    private void optionallyPrint(LoggingEvent event) {
        if (printEnabled()) {
            event.print();
        }
    }

    private boolean printEnabled() {
        return true;
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
     * {@link uk.org.lidalia.slf4jutils.ConventionalLevelHierarchy}
     *
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(ImmutableSet<Level> enabledLevels) {
        this.enabledLevels.set(enabledLevels);
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by passing the constants in
     * {@link uk.org.lidalia.slf4jutils.ConventionalLevelHierarchy} to {@link #setEnabledLevels(ImmutableSet)}
     *
     * @param enabledLevels levels which will be considered enabled for this logger IN THIS THREAD;
     *                      does not affect enabled levels for this logger in other threads
     */
    public void setEnabledLevels(Level... enabledLevels) {
        setEnabledLevels(immutableEnumSet(asList(enabledLevels)));
    }

    /**
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by using the constants in
     * {@link uk.org.lidalia.slf4jutils.ConventionalLevelHierarchy}
     *
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
     * The conventional hierarchical notion of Levels, where info being enabled implies warn and error being enabled, is not a
     * requirement of the SLF4J API, so all levels you wish to enable must be passed explicitly to this method.  If you wish to
     * use traditional hierarchical setups you may conveniently do so by passing the constants in
     * {@link uk.org.lidalia.slf4jutils.ConventionalLevelHierarchy} to {@link #setEnabledLevelsForAllThreads(ImmutableSet)}
     *
     * @param enabledLevels levels which will be considered enabled for this logger IN ALL THREADS
     */
    public void setEnabledLevelsForAllThreads(final Level... enabledLevels) {
        setEnabledLevelsForAllThreads(ImmutableSet.copyOf(enabledLevels));
    }
}
