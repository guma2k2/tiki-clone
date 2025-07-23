package com.tiki.product.dto.request;

public record ReviewCreateRequest(
        Long productVariantId,
        String content,
        int ratingStar
) {
}
