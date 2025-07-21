package com.tiki.product.dto.request;

import com.tiki.product.dto.enums.ProductImageType;

public record ProductImageCreateType(
    String url,
    ProductImageType type,
    int sortOrder,
    boolean status,
    Long productVariantId
) {
}
