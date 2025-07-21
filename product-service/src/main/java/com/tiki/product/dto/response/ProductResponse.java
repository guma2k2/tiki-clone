package com.tiki.product.dto.response;

import com.tiki.product.entity.Product;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String slug,
        String description,
        BrandResponse brand,
        CategoryResponse category,
        List<ProductVariantResponse> productVariants,
        List<ProductImageResponse> productImages
) {
    public static ProductResponse from(Product product, BrandResponse brand, CategoryResponse category, List<ProductVariantResponse> productVariants, List<ProductImageResponse> productImages) {
        return new ProductResponse(product.getId(), product.getName(), product.getSlug(), product.getDescription(), brand, category, productVariants, productImages);
    }
}
