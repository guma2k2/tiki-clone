package com.tiki.product.dto.response;

import com.tiki.product.entity.Brand;

public record BrandResponse(
        Integer id,
        String name,
        String description
) {
    public static BrandResponse from(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription());
    }
}
