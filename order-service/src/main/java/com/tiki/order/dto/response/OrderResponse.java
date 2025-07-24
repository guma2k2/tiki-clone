package com.tiki.order.dto.response;

import com.tiki.order.entity.Order;
import com.tiki.order.enums.OrderStatus;
import com.tiki.order.utils.DateFormatter;

import java.util.List;

public record OrderResponse(
        Long id,
        String receiverPhoneNumber,
        String receiverAddress,
        String receiverName,
        String note,
        String createdAt,
        OrderStatus status,
        List<OrderDetailResponse> orderDetails
) {

    public static OrderResponse from(Order order, List<OrderDetailResponse> orderDetails) {
        return new OrderResponse(order.getId(),
                order.getReceiverPhoneNumber(),
                order.getReceiverAddress(),
                order.getReceiverName(),
                order.getNote(),
                DateFormatter.convertLocalDateTime(order.getCreatedAt()),
                order.getStatus(),
                orderDetails);
    }
}
