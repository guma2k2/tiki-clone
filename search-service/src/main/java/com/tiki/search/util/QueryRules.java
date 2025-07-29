package com.tiki.search.util;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.tiki.search.util.Constants.PRODUCTS.*;
import static com.tiki.search.util.ElasticSearchUtil.*;

public class QueryRules {

    private static final String BOOST_FIELD_FORMAT  = "%s^%f";

    public static final QueryRule RATING_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.rating()),
            srp -> buildRangeQuery(RATING, builder -> builder.gte(Double.valueOf(srp.rating())))
    );

    public static final QueryRule PRICE_QUERY = QueryRule.of(
            searchRequest -> Objects.nonNull(searchRequest.fromPrice()) && Objects.nonNull(searchRequest.toPrice()),
            searchRequest -> buildRangeQuery(PRICE, builder -> builder.gte(Double.valueOf(searchRequest.fromPrice())).lte(searchRequest.toPrice()))
    );

    public static final QueryRule CATEGORY_QUERY = QueryRule.of(
            sr -> Objects.nonNull(sr.category()),
            sr -> buildMatchQuery(CATEGORY, sr.category(), 1.0f)
    );

    public static final QueryRule BRAND_QUERY = QueryRule.of(
            sr -> Objects.nonNull(sr.brand()),
            sr -> buildMatchQuery(BRAND, sr.brand(), 1.0f)
    );

    private static final List<String> SEARCH_BOOST_FIELDS = List.of(
            boostField(NAME, 2.0f),
            boostField(CATEGORY, 1.5f),
            boostField(BRAND, 1.5f),
            DESCRIPTION
    );
    public static final QueryRule ATTRIBUTES_QUERY = QueryRule.of(
            sr -> sr.facet() != null && !sr.facet().isBlank(),
            sr -> buildNestedFacetQueries(ATTRIBUTES, sr.facet())
    );

    private static String boostField(String field, float boost){
        return BOOST_FIELD_FORMAT.formatted(field, boost);
    }
    public static final QueryRule SEARCH_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.query()),  // can also use Predicates.isTrue() if it is true always
            srp -> buildMultiMatchQuery(SEARCH_BOOST_FIELDS, srp.query())
    );

}
