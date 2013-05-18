package org.slf4j.impl;

import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

public final class StaticMarkerBinder implements MarkerFactoryBinder {

    public static final org.slf4j.impl.StaticMarkerBinder SINGLETON = new org.slf4j.impl.StaticMarkerBinder();

    private final IMarkerFactory markerFactory = new BasicMarkerFactory();

    private StaticMarkerBinder() { }

    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    public String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.class.getName();
    }
}
