package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public final class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    public static final String REQUESTED_API_VERSION = "1.6";

    private StaticLoggerBinder() { }

    public ILoggerFactory getLoggerFactory() {
        return TestLoggerFactory.getInstance();
    }

    public String getLoggerFactoryClassStr() {
        return TestLoggerFactory.class.getName();
    }
}
