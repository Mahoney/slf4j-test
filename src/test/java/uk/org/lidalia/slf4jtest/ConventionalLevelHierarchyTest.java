package uk.org.lidalia.slf4jtest;

import org.junit.Test;
import uk.org.lidalia.test.Assert;

public class ConventionalLevelHierarchyTest {
    @Test
    public void notInstantiable() throws Throwable {
        Assert.assertNotInstantiable(ConventionalLevelHierarchy.class);
    }
}
