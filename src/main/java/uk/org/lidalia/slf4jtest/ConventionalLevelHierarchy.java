package uk.org.lidalia.slf4jtest;

import com.google.common.collect.ImmutableSet;
import uk.org.lidalia.slf4jutils.Level;

import static com.google.common.collect.Sets.immutableEnumSet;
import static uk.org.lidalia.slf4jutils.Level.DEBUG;
import static uk.org.lidalia.slf4jutils.Level.ERROR;
import static uk.org.lidalia.slf4jutils.Level.INFO;
import static uk.org.lidalia.slf4jutils.Level.TRACE;
import static uk.org.lidalia.slf4jutils.Level.WARN;

public class ConventionalLevelHierarchy {
    public static final ImmutableSet<Level> OFF_LEVELS = ImmutableSet.of();
    public static final ImmutableSet<Level> ERROR_LEVELS = immutableEnumSet(ERROR);
    public static final ImmutableSet<Level> WARN_LEVELS = immutableEnumSet(ERROR, WARN);
    public static final ImmutableSet<Level> INFO_LEVELS = immutableEnumSet(ERROR, WARN, INFO);
    public static final ImmutableSet<Level> DEBUG_LEVELS = immutableEnumSet(ERROR, WARN, INFO, DEBUG);
    public static final ImmutableSet<Level> TRACE_LEVELS = immutableEnumSet(ERROR, WARN, INFO, DEBUG, TRACE);

    private ConventionalLevelHierarchy() {
        throw new UnsupportedOperationException("Not instantiable");
    }
}
