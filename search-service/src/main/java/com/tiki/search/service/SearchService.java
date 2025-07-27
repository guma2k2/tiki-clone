package com.tiki.search.service;

import com.tiki.search.dto.ApiResponse;
import com.tiki.search.dto.request.SearchRequest;
import com.tiki.search.dto.response.SearchResponse;
import com.tiki.search.entity.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    ElasticsearchOperations elasticsearchOperations;

    public ApiResponse<SearchResponse> search(SearchRequest request) {
        return null;
    }


}
