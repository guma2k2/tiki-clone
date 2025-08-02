package com.tiki.order.dto;

import com.tiki.order.dto.request.OrderDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long productId;
    private int quantity;

    public OrderDetailDto(OrderDetailRequest orderDetailRequest) {
        this.productId = orderDetailRequest.productId();
        this.quantity = orderDetailRequest.quantity();
    }
}
