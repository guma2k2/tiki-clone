package com.tiki.product.dto.response;

import com.tiki.product.entity.Category;

public record CategoryResponse(
        Integer id,
        String name
) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
