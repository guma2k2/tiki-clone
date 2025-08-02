package com.tiki.payment.dto.request;

public record PaymentPostDto(

         long amount,
         String bankCode ,
         String bankTranNo ,
         String cartType ,
         String payDate,
         Long orderId
) {
}
