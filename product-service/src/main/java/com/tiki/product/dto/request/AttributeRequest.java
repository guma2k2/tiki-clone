package com.tiki.product.dto.request;

public record AttributeRequest(
        String name,
        String unit,
        String dataType
) {
}
