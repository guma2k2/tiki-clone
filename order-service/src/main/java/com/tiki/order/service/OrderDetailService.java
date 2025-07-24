package com.tiki.order.service;

import com.tiki.order.dto.response.ProductVariantResponse;
import com.tiki.order.enums.OrderStatus;
import com.tiki.order.repository.OrderDetailRepository;
import com.tiki.order.repository.client.ProductFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {

    OrderDetailRepository orderDetailRepository;

    ProductFeignClient productFeignClient;

    public Long getSoldNumByProduct(Long productId) {
        OrderStatus success = OrderStatus.SUCCESS;
        return orderDetailRepository.getSoldNumByProduct(productId, success);
    }

    public List<ProductVariantResponse> getTopProductBestSeller(int limit) {

        List<Long> productIds = orderDetailRepository.findTopProductsByQuantity();

        List<Long> productIdsActual = productIds.stream().limit(limit).toList();

        List<ProductVariantResponse> productVariantDtos = productIdsActual.stream().map(id -> {
            ProductVariantResponse productVariantDto = productFeignClient.getByProductId(id).getResult();
            return productVariantDto;
        }).toList();
        return productVariantDtos;
    }
}
