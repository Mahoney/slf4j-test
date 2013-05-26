package org.slf4j.impl;

import org.junit.Test;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

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

    @Test
    public void getMarkerFactoryReturnsCorrectlyFromSlf4JLoggerFactory() {
        assertThat(MarkerFactory.getIMarkerFactory(), is(StaticMarkerBinder.SINGLETON.getMarkerFactory()));
    }
}
