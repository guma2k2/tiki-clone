package com.tiki.search.dto.request;

public record SuggestionRequest(
        String prefix,
        Integer limit
) {
}
