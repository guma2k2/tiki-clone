package com.tiki.search.dto.request;

import org.springframework.util.StringUtils;

import java.util.Objects;

/*
For an application with 1000s of attributes, we can use Map<K,V>.
Walmart follows this:
https://www.walmart.com/search?q=candy&facet=size:M||size:L||color:black
* */
public record SearchRequest(String query,
                            String category,
                            String brand,
                            Double fromPrice,
                            Double toPrice,
                            Integer rating,
                            String facet,
                            Integer page,
                            Integer size) {
    public SearchRequest {
//        if(!StringUtils.hasText(query)){
//            throw new RuntimeException("query can not be empty");
//        }
        page = Objects.requireNonNullElse(page, 0);
        size = Objects.requireNonNullElse(size, 10);
    }
}
