package com.tiki.payment.service;

import com.tiki.payment.dto.response.PaymentResponse;
import com.tiki.payment.dto.request.PaymentPostDto;
import com.tiki.payment.dto.request.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentResponse.VNPayResponse createVNPayPayment(PaymentRequest request, HttpServletRequest httpServletRequest);

    void savePayment(PaymentPostDto request);
}
