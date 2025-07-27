package com.tiki.search.dto.request;

/*
For an application with 1000s of attributes, we can use Map<K,V>.
Walmart follows this:
https://www.walmart.com/search?q=candy&facet=size:M||size:L||color:black
* */
public record SearchRequest(String query,
                            String category,
                            String brand,
                            Double price,
                            Integer rating,
                            String facet,
                            Integer page,
                            Integer size) {
}
