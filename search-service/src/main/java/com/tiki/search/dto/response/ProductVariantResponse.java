package com.tiki.search.dto.response;

import java.util.Map;

public record ProductVariantResponse (
        Long  id,
        String sku,
        Double price,
        Integer quantity,
        String status,
        Map<String, String> variantAttributeValue,
        String name,
        String slug,
        String description,
        Float rating,
        Integer reviewsCount,
        String imageUrl,
        String category,
        String brand
){
}
