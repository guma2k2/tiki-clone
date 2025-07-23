package com.tiki.cart.dto.response;

import com.tiki.cart.entity.Cart;

public record CartResponse (
        String id,
        ProductVariantResponse product,
        int quantity,
        boolean isSelected
) {

    public static CartResponse fromModel(Cart cart, ProductVariantResponse product) {
        return new CartResponse(cart.getId(), product, cart.getQuantity(), cart.isSelected());
    }
}