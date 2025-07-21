package com.tiki.product.dto.response;

import com.tiki.product.entity.ProductVariant;

import java.util.List;
import java.util.Map;

public record ProductVariantResponse(
        Long  id,
        String sku,
        Double price,
        Integer quantity,
        String status,
        Map<String, String> variantAttributeValue
) {
    public static ProductVariantResponse from(ProductVariant productVariant, Map<String, String> variantAttributeValue) {
        return new ProductVariantResponse(productVariant.getId(), productVariant.getSku(), productVariant.getPrice(), productVariant.getQuantity(), productVariant.getStatus(), variantAttributeValue);
    }
}
