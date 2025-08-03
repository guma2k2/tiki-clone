package com.tiki.order.dto.request;

import com.tiki.order.enums.OrderStatus;

public record OrderStatusRequest(
        Long orderId,
        OrderStatus orderStatus,
        String reasonFailed
) {
}
