package com.tiki.search.dto.response;

import com.tiki.search.dto.Pagination;

import java.util.List;

public record SearchResponse (
        List<ProductResponse> results,
//        List<Facet> facets,
        Pagination pagination,
        long timeTaken
) {
}
