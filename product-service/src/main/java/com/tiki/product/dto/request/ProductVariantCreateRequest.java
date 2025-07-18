package com.tiki.product.dto.request;

import java.util.Map;

public record ProductVariantCreateRequest (
        Double price,
        Integer quantity,
        Map<String, String> attributes // VD:
) {
}
