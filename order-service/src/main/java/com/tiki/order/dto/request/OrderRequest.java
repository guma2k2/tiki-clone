package com.tiki.order.dto.request;

import java.util.List;

public record OrderRequest (
        String receiverPhoneNumber,
        String receiverAddress,
        String receiverName,
        String note,
        List<OrderDetailRequest> orderDetails
) {
}
