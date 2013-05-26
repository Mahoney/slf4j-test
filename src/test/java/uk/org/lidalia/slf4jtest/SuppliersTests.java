package uk.org.lidalia.slf4jtest;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static uk.org.lidalia.slf4jtest.Suppliers.makeEmptyMutableList;
import static uk.org.lidalia.slf4jtest.Suppliers.makeEmptyMutableMap;
import static uk.org.lidalia.test.Assert.isNotInstantiable;

public class SuppliersTests {

    @Test
    public void makeEmptyMutableListAlwaysMakesANewList() {
        Supplier<List<String>> listSupplier = makeEmptyMutableList();
        List<String> list1 = listSupplier.get();
        List<String> list2 = listSupplier.get();
        assertThat(list1, is(not(sameInstance(list2))));
    }

    @Test
    public void makeEmptyMutableListMakesAMutableList() {
        List<String> list = Suppliers.<String>makeEmptyMutableList().get();
        list.add("value");
        assertThat(list, is(asList("value")));
    }

    @Test
    public void makeEmptyMutableMapAlwaysMakesANewMap() {
        Supplier<Map<String, String>> mapSupplier = makeEmptyMutableMap();
        Map<String, String> map1 = mapSupplier.get();
        Map<String, String> map2 = mapSupplier.get();
        assertThat(map1, is(not(sameInstance(map2))));
    }

    @Test
    public void makeEmptyMutableMapMakesAMutableMap() {
        Map<String, String> map = Suppliers.<String, String>makeEmptyMutableMap().get();
        map.put("key", "value");
        Map<String, String> expected = ImmutableMap.of("key", "value");
        assertThat(map, is(expected));
    }

    @Test
    public void notInstantiable() {
        assertThat(Suppliers.class, isNotInstantiable());
    }
}
