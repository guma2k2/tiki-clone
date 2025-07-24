package com.tiki.order.dto.response;

import com.tiki.order.entity.Order;
import com.tiki.order.enums.OrderStatus;
import com.tiki.order.utils.DateFormatter;

import java.util.List;

public record OrderGetListResponse(
        Long id,
        String receiverPhoneNumber,
        String receiverAddress,
        String receiverName,
        String note,
        String createdAt,
        OrderStatus status,
        List<OrderDetailResponse> orderDetails,
        CustomerResponse customer
) {
    public static OrderGetListResponse from(Order order, List<OrderDetailResponse> orderDetails, CustomerResponse customer) {
        return new OrderGetListResponse(order.getId(),
                order.getReceiverPhoneNumber(),
                order.getReceiverAddress(),
                order.getReceiverName(),
                order.getNote(),
                DateFormatter.convertLocalDateTime(order.getCreatedAt()),
                order.getStatus(),
                orderDetails,
                customer
        );
    }
}