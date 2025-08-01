package com.tiki.search.util;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class Constants {

    private Constants() {}

    public static class Index {
        public static final IndexCoordinates PRODUCT = IndexCoordinates.of("products");
    }

    public static class Fuzzy {
        public static final String LEVEL = "1";
        public static final int PREFIX_LENGTH = 1;
    }


    public static class PRODUCTS {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY = "category";
        public static final String BRAND = "brand";

        public static final String ATTRIBUTE_NAME = "attributes.name";
        public static final String ATTRIBUTE_VALUE = "attributes.value";
        public static final String RATING = "avg_rating";
        public static final String ATTRIBUTES = "attributes";
        public static final String PRICE = "price";

    }
}
