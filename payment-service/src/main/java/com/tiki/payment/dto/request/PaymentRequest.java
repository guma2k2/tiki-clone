package com.tiki.payment.dto.request;

public record PaymentRequest(
        int amount,
        String bankCode,
        Long orderId
) {
}
