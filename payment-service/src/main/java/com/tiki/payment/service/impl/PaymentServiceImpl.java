package com.tiki.payment.service.impl;

import com.tiki.payment.config.VNPayConfig;
import com.tiki.payment.dto.response.PaymentResponse;
import com.tiki.payment.dto.request.PaymentPostDto;
import com.tiki.payment.dto.request.PaymentRequest;
import com.tiki.payment.model.Payment;
import com.tiki.payment.repository.PaymentRepository;
import com.tiki.payment.service.PaymentService;
import com.tiki.payment.utils.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPayConfig vnPayConfig;

    private final PaymentRepository paymentRepository;
    @Override
    public PaymentResponse.VNPayResponse createVNPayPayment(PaymentRequest request, HttpServletRequest httpServletRequest) {
        long amount = request.amount() * 100L;
        String bankCode = request.bankCode();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(request);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(httpServletRequest));
        //build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        queryUrl += "&vnp_SecureHash=" + VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentResponse.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    @Override
    public void savePayment(PaymentPostDto request) {
        Payment payment = Payment.builder()
                .bankTranNo(request.bankTranNo())
                .payDate(request.payDate())
                .orderId(request.orderId())
                .amount(request.amount())
                .cartType(request.cartType())
                .bankCode(request.bankCode())
                .build();
        paymentRepository.save(payment);
        // Todo : update order Status to success
    }
}
