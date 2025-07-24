package com.tiki.order.dto.response;


import com.tiki.order.entity.OrderDetail;

public record OrderDetailResponse(
        Long id,
        ProductVariantResponse productVariant,
        Double price,
        int quantity
) {
    public static OrderDetailResponse from(OrderDetail orderDetail, ProductVariantResponse productVariantDto) {
        return new OrderDetailResponse(orderDetail.getId(), productVariantDto, productVariantDto.price(), orderDetail.getQuantity());
    }
}
