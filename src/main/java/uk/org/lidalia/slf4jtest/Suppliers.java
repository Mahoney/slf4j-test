package uk.org.lidalia.slf4jtest;

import com.google.common.base.Supplier;

import java.util.ArrayList;
import java.util.List;

class Suppliers {

    static final Supplier<List<LoggingEvent>> makeEmptyMutableList = new Supplier<List<LoggingEvent>>() {
        @Override
        public List<LoggingEvent> get() {
            return new ArrayList<>();
        }
    };
}
