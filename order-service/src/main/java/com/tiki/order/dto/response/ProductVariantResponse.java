package com.tiki.order.dto.response;

import java.util.List;

public record ProductVariantResponse(
        Long id,
        String name,
        String image,
        int quantity,
        Double price,
        List<String> productAttributeValues,
        SellerResponse seller
) {
}
