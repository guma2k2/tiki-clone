package com.tiki.search.service;

import com.tiki.search.dto.ApiResponse;
import com.tiki.search.dto.Pagination;
import com.tiki.search.dto.request.SearchRequest;
import com.tiki.search.dto.response.ProductResponse;
import com.tiki.search.dto.response.SearchResponse;
import com.tiki.search.entity.Product;
import com.tiki.search.util.Constants;
import com.tiki.search.util.NativeQueryBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    ElasticsearchOperations elasticsearchOperations;

    public SearchResponse search(SearchRequest request) {
        NativeQuery searchQuery = NativeQueryBuilder.toSearchQuery(request);
        SearchHits<Product> searchHits = elasticsearchOperations.search(searchQuery, Product.class, Constants.Index.PRODUCT);

        return buildResponse(request, searchHits);
    }

    private SearchResponse buildResponse(SearchRequest request, SearchHits<Product> searchHits) {

        List<Product> products = searchHits.getSearchHits().stream()
                .map(productSearchHit -> productSearchHit.getContent()).toList();


        List<ProductResponse> results = products.stream().map(product -> ProductResponse.from(product)).toList();

        SearchPage<Product> searchPage = SearchHitSupport.searchPageFor(searchHits, PageRequest.of(request.page(), request.size()));


        Pagination pagination = new Pagination(
                searchPage.getNumber(),
                searchPage.getNumberOfElements(),
                searchPage.getTotalElements(),
                searchPage.getTotalPages()
        );


        return new SearchResponse(results, pagination, searchHits.getExecutionDuration().toMillis());
    }


}
