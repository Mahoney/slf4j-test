package org.slf4j.impl;

import org.junit.Test;

import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class StaticLoggerBinderTests {

    @Test
    public void getLoggerFactory() throws Exception {
        assertSame(TestLoggerFactory.getInstance(), StaticLoggerBinder.getSingleton().getLoggerFactory());
    }

    @Test
    public void getLoggerFactoryClassStr() throws Exception {
        assertEquals("uk.org.lidalia.slf4jtest.TestLoggerFactory", StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr());
    }

    @Test
    public void requestedApiVersion() throws Exception {
        assertEquals("1.6", StaticLoggerBinder.REQUESTED_API_VERSION);
    }
}
