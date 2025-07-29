package com.tiki.search.util;

import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggester;
import co.elastic.clients.elasticsearch.core.search.FieldSuggester;
import co.elastic.clients.elasticsearch.core.search.SuggestFuzziness;
import co.elastic.clients.elasticsearch.core.search.Suggester;


import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.tiki.search.util.Constants.PRODUCTS.ATTRIBUTE_NAME;
import static com.tiki.search.util.Constants.PRODUCTS.ATTRIBUTE_VALUE;

public class ElasticSearchUtil {
    public static Query buildTermQuery(String field, String value, float boost) {
        var termQuery = TermQuery.of(builder -> builder.field(field)
                .value(value)
                .boost(boost)
                .caseInsensitive(true));
        return Query.of(builder -> builder.term(termQuery));
    }


    public static Query buildMatchQuery(String field, String value, float boost) {
        var matchQuery = MatchQuery.of(builder -> builder
                .field(field)
                .query(value)
                .boost(boost)
                .operator(Operator.And)
        );
        return Query.of(builder -> builder.match(matchQuery));
    }
    public static Query buildRangeQuery(String field, UnaryOperator<NumberRangeQuery.Builder> function) {
        var numberRangeQuery = NumberRangeQuery.of(builder -> function.apply(builder.field(field)));
        var rangeQuery = RangeQuery.of(builder -> builder.number(numberRangeQuery));
        return Query.of(builder -> builder.range(rangeQuery));
    }


    public static Query buildMultiMatchQuery(List<String> fields, String searchTerm) {
        var multiMatchQuery = MultiMatchQuery.of(builder -> builder.query(searchTerm)
                .fields(fields)
                .fuzziness(Constants.Fuzzy.LEVEL)
                .prefixLength(Constants.Fuzzy.PREFIX_LENGTH)
                .type(TextQueryType.MostFields)
                .operator(Operator.And));
        return Query.of(builder -> builder.multiMatch(multiMatchQuery));
    }

    public static Query buildNestedFacetQueries(String field, String facetString) {
        List<Query> nestedFilters = Arrays.stream(facetString.split("\\|\\|"))
                .filter(pair -> pair.contains(":"))
                .map(pair -> {
                    String[] parts = pair.split(":", 2);
                    String name = parts[0].trim();
                    String value = parts[1].trim();

                    return Query.of(q -> q.nested(n -> n
                            .path(field)
                            .query(nq -> nq.bool(b -> b
                                    .must(List.of(
                                            Query.of(m1 -> m1.match(t -> t.field(ATTRIBUTE_NAME).query(name))),
                                            Query.of(m2 -> m2.match(t -> t.field(ATTRIBUTE_VALUE).query(value)))
                                    ))
                            ))
                    ));
                })
                .toList();

        return Query.of(q -> q.bool(b -> b.filter(nestedFilters)));
    }
}
