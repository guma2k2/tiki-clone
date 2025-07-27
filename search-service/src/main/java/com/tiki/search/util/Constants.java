package com.tiki.search.util;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class Constants {

    private Constants() {}

    public static class Index {
        public static final IndexCoordinates PRODUCT = IndexCoordinates.of("products");
    }
}
