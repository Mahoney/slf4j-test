package uk.org.lidalia.slf4jtest;

import com.google.common.collect.ImmutableMap;
import org.slf4j.spi.MDCAdapter;

import java.util.HashMap;
import java.util.Map;

public class TestMDCAdapter implements MDCAdapter {

    private final InheritableThreadLocal<Map<String, String>> value = new InheritableThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };

    public void put(final String key, final String val) {
        value.get().put(key, val);
    }

    public String get(final String key) {
        return value.get().get(key);
    }

    public void remove(final String key) {
        value.get().remove(key);
    }

    public void clear() {
        value.get().clear();
    }

    public ImmutableMap<String, String> getCopyOfContextMap() {
        return ImmutableMap.copyOf(value.get());
    }

    @SuppressWarnings("unchecked")
    public void setContextMap(final Map contextMap) {
        value.set(new HashMap<String, String>(contextMap));
    }
}
