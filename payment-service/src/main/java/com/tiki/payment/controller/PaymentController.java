package com.tiki.payment.controller;

import com.tiki.payment.dto.ApiResponse;
import com.tiki.payment.dto.response.PaymentResponse;
import com.tiki.payment.dto.request.PaymentPostDto;
import com.tiki.payment.dto.request.PaymentRequest;
import com.tiki.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ApiResponse<PaymentResponse.VNPayResponse> pay(@RequestBody PaymentRequest request, HttpServletRequest httpServletRequest) {
        return ApiResponse.<PaymentResponse.VNPayResponse>builder().result(paymentService.createVNPayPayment(request, httpServletRequest)).build();
    }
    @GetMapping("/vn-pay-callback")
    public ApiResponse<PaymentResponse.VNPayResponse> payCallbackHandler(@RequestParam String vnp_ResponseCode) {
        if (vnp_ResponseCode.equals("00")) {
            // redirect to some path ...
            return ApiResponse.<PaymentResponse.VNPayResponse>builder().result(new PaymentResponse.VNPayResponse("00", "Success", "")).build();
        } else {
            return ApiResponse.<PaymentResponse.VNPayResponse>builder().result(null).code(1400).build();
        }
    }

    @PostMapping("/vn-pay-success")
    public ApiResponse<Void> pay(@RequestBody PaymentPostDto request) {
        paymentService.savePayment(request);
        return ApiResponse.<Void>builder().code(1204).message("pay success").build();
    }
}
