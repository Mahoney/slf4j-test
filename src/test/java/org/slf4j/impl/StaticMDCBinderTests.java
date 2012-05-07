package org.slf4j.impl;

import org.junit.Test;
import org.slf4j.helpers.BasicMarkerFactory;

import uk.org.lidalia.slf4jtest.TestMDCAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class StaticMDCBinderTests {

    @Test
    public void getMDCA() throws Exception {
        assertSame(TestMDCAdapter.class, StaticMDCBinder.SINGLETON.getMDCA().getClass());
        assertSame(StaticMDCBinder.SINGLETON.getMDCA(), StaticMDCBinder.SINGLETON.getMDCA());
    }

    @Test
    public void getMDCAdapterClassStr() throws Exception {
        assertEquals("uk.org.lidalia.slf4jtest.TestMDCAdapter", StaticMDCBinder.SINGLETON.getMDCAdapterClassStr());
    }
}
