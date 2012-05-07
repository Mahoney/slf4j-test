package uk.org.lidalia.slf4jtest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.org.lidalia.slf4jtest.TestLoggerFactory.INSTANCE;

public class TestLoggerFactoryTests {

    @Test
    public void getLoggerDifferentNames() throws Exception {
        TestLogger logger1 = INSTANCE.getLogger("name1");
        TestLogger logger2 = INSTANCE.getLogger("name2");

        assertFalse(logger1 == logger2);
    }

    @Test
    public void getLoggerSameNames() throws Exception {
        TestLogger logger1 = INSTANCE.getLogger("name1");
        TestLogger logger2 = INSTANCE.getLogger("name1");

        assertTrue(logger1 == logger2);
    }

    @Test
    public void staticGetTestLoggerStringReturnsSame() throws Exception {
        TestLogger logger1 = TestLoggerFactory.getTestLogger("name1");
        TestLogger logger2 = INSTANCE.getLogger("name1");

        assertTrue(logger1 == logger2);
    }

    @Test
    public void staticGetTestLoggerClassReturnsSame() throws Exception {
        TestLogger logger1 = TestLoggerFactory.getTestLogger(String.class);
        TestLogger logger2 = INSTANCE.getLogger("java.lang.String");

        assertTrue(logger1 == logger2);
    }

    @Test
    public void clear() throws Exception {
        TestLogger logger1 = INSTANCE.getLogger("name1");
        logger1.trace("hello");
        assertEquals(1, logger1.getLoggingEvents().size());
        TestLogger logger2 = INSTANCE.getLogger("name2");
        logger2.trace("world");
        assertEquals(1, logger2.getLoggingEvents().size());

        TestLoggerFactory.clear();

        assertEquals(0, logger1.getLoggingEvents().size());
        assertEquals(0, logger2.getLoggingEvents().size());
    }
}
