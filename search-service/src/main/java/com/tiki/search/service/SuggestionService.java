package com.tiki.search.service;


import co.elastic.clients.elasticsearch.core.search.CompletionSuggester;
import co.elastic.clients.elasticsearch.core.search.FieldSuggester;
import co.elastic.clients.elasticsearch.core.search.SuggestFuzziness;
import co.elastic.clients.elasticsearch.core.search.Suggester;
import com.tiki.search.dto.request.SuggestionRequest;
import com.tiki.search.util.Constants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SuggestionService {
    ElasticsearchOperations elasticsearchOperations;

    static String FUZZY_LEVEL = "1";
    static Integer FUZZY_PREFIX_LENGTH = 1;
    static String SUGGEST_NAME = "search-term-suggest";
    static String SEARCH_TERM = "name.completion";

    public List<String> fetchSuggestions(SuggestionRequest request) {
        log.info("suggestion request: {}", request);
        NativeQuery suggestQuery = toSuggestQuery(request);
        SearchHits<Object> searchHits = elasticsearchOperations.search(suggestQuery, Object.class, Constants.Index.PRODUCT);

        return Optional.ofNullable(searchHits.getSuggest())
                .map(s -> s.getSuggestion(SUGGEST_NAME))
                .stream()
                .map(suggestion -> suggestion.getEntries())
                .flatMap(Collection::stream)
                .map(entry -> entry.getOptions())
                .flatMap(Collection::stream)
                .map(option -> option.getText()).toList();
    }


    private NativeQuery toSuggestQuery(SuggestionRequest request) {
        Suggester suggester = buildCompletionSuggester(SUGGEST_NAME, SEARCH_TERM, request.prefix(), request.limit());

        return NativeQuery.builder()
                .withSuggester(suggester)
                .withMaxResults(0)
                .withSourceFilter(FetchSourceFilter.of(b -> b.withExcludes("*")))
                .build();
    }

    private Suggester buildCompletionSuggester(String suggestName, String field, String prefix, int limit) {
        SuggestFuzziness suggestFuzziness = SuggestFuzziness.of(builder -> builder.fuzziness(FUZZY_LEVEL).prefixLength(FUZZY_PREFIX_LENGTH));
        CompletionSuggester completionSuggester = CompletionSuggester.of(builder -> builder.field(field)
                .size(limit).fuzzy(suggestFuzziness).skipDuplicates(true));
        FieldSuggester fieldSuggester = FieldSuggester.of(builder -> builder.prefix(prefix).completion(completionSuggester));
        return Suggester.of(builder -> builder.suggesters(suggestName, fieldSuggester));
    }


}
