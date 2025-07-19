package com.tiki.product.dto.request;

public record CategoryCreateRequest(
        String name,
        Integer parentId
) {
}
