package com.tiki.order.dto.request;

public record OrderDetailRequest(
        Long productId,
        int quantity,
        double price
) {
}
