package uk.org.lidalia.slf4jtest;

import com.google.common.base.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Suppliers {

    static <T> Supplier<List<T>> makeEmptyMutableList() {
        return new Supplier<List<T>>() {
            @Override
            public List<T> get() {
                return new ArrayList<>();
            }
        };
    }

    static <K, V> Supplier<Map<K, V>> makeEmptyMutableMap() {
        return new Supplier<Map<K, V>>() {
            @Override
            public Map<K, V> get() {
                return new HashMap<>();
            }
        };
    }
}
