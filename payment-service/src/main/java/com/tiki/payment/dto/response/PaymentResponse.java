package com.tiki.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class PaymentResponse {
    @Builder
    @AllArgsConstructor
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
