package com.tiki.search.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.tiki.search.dto.request.SearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NativeQueryBuilder {

    private static List<QueryRule>  NESTED_QUERY_RULES = Arrays.asList();
    private static final List<QueryRule> FILTER_QUERY_RULES = List.of(
            QueryRules.RATING_QUERY,
            QueryRules.CATEGORY_QUERY,
            QueryRules.BRAND_QUERY,
            QueryRules.PRICE_QUERY,
            QueryRules.ATTRIBUTES_QUERY
    );

    private static final List<QueryRule> MUST_QUERY_RULES = List.of(
            QueryRules.SEARCH_QUERY
    );



//    public static NativeQuery toSuggestQuery(SuggestionRequestParameters parameters) {
//        var suggester = ElasticsearchUtil.buildCompletionSuggester(
//                Constants.Suggestion.SUGGEST_NAME,
//                Constants.Suggestion.SEARCH_TERM,
//                parameters.prefix(),
//                parameters.limit()
//        );
//        return NativeQuery.builder()
//                .withSuggester(suggester)
//                .withMaxResults(0) // We do not want any results object
//                .withSourceFilter(FetchSourceFilter.of(b -> b.withExcludes("*"))) // disable fetching the source object
//                .build();
//    }

    public static NativeQuery toSearchQuery(SearchRequest parameters) {
        var filterQueries = buildQueries(FILTER_QUERY_RULES, parameters);
        var mustQueries = buildQueries(MUST_QUERY_RULES, parameters);
        var boolQuery = BoolQuery.of(builder -> builder.filter(filterQueries)
                .must(mustQueries));
        return NativeQuery.builder()
                .withQuery(Query.of(builder -> builder.bool(boolQuery)))
                .withPageable(PageRequest.of(parameters.page(), parameters.size()))
                .withTrackTotalHits(true)
                .build();
    }

    private static List<Query> buildQueries(List<QueryRule> queryRules, SearchRequest parameters) {
        return queryRules.stream()
                .map(qr -> qr.build(parameters))
                .flatMap(Optional::stream)
                .toList();
    }
}
