package org.slf4j.impl;

import org.junit.Test;
import org.slf4j.helpers.BasicMarkerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class StaticMarkerBinderTests {

    @Test
    public void getMarkerFactory() throws Exception {
        assertSame(BasicMarkerFactory.class, StaticMarkerBinder.SINGLETON.getMarkerFactory().getClass());
        assertSame(StaticMarkerBinder.SINGLETON.getMarkerFactory(), StaticMarkerBinder.SINGLETON.getMarkerFactory());
    }

    @Test
    public void getMarkerFactoryClassStr() throws Exception {
        assertEquals("org.slf4j.helpers.BasicMarkerFactory", StaticMarkerBinder.SINGLETON.getMarkerFactoryClassStr());
    }
}
