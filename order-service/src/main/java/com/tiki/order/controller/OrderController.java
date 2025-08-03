package com.tiki.order.controller;

import com.tiki.order.dto.ApiResponse;
import com.tiki.order.dto.request.OrderRequest;
import com.tiki.order.dto.request.OrderStatusRequest;
import com.tiki.order.dto.response.OrderGetListResponse;
import com.tiki.order.dto.response.OrderResponse;
import com.tiki.order.dto.response.ProductVariantResponse;
import com.tiki.order.enums.OrderStatus;
import com.tiki.order.service.OrderDetailService;
import com.tiki.order.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    OrderDetailService orderDetailService;


    @PostMapping("/storefront")
    public ApiResponse<Long> createOrder(@RequestBody OrderRequest orderPostDto) {
        Long orderId = orderService.createOrder(orderPostDto);
        return ApiResponse.<Long>builder().result(orderId).build();
    }

    @GetMapping("/storefront")
    public ApiResponse<List<OrderResponse>> findAllByUserId() {
        List<OrderResponse> orderDtos = orderService.findAllByUserId();
        return ApiResponse.<List<OrderResponse>>builder().result(orderDtos).build();
    }

    @GetMapping("/beseller-products")
    public ApiResponse<List<ProductVariantResponse>> get10BestSellerProducts() {
        List<ProductVariantResponse> productVariantDtos = orderDetailService.getTopProductBestSeller(10);
        return ApiResponse.<List<ProductVariantResponse>>builder().result(productVariantDtos).build();
    }

    @GetMapping("/storefront/{orderId}")
    public ApiResponse<OrderResponse> findById(@PathVariable("orderId") Long orderId) {
        OrderResponse orderDto = orderService.findById(orderId);
        return ApiResponse.<OrderResponse>builder().result(orderDto).build();
    }
    @GetMapping("/backoffice")
    public ApiResponse<List<OrderGetListResponse>> findAll() {
        List<OrderGetListResponse> orderDtos = orderService.findAll();
        return ApiResponse.<List<OrderGetListResponse>>builder().result(orderDtos).build();
    }

    @GetMapping("/backoffice/status/{orderStatus}")
    public ApiResponse<List<OrderGetListResponse>> findAllByStatus(
            @PathVariable("orderStatus") OrderStatus orderStatus
    ) {
        List<OrderGetListResponse> orderDtos = orderService.findAllByStatus(orderStatus);
        return ApiResponse.<List<OrderGetListResponse>>builder().result(orderDtos).build();

    }


    @GetMapping("/sold-num/product/{productId}")
    public ApiResponse<Long> getSoldNumByProduct(
            @PathVariable("productId") Long productId
    ) {
        return ApiResponse.<Long>builder().result(orderDetailService.getSoldNumByProduct(productId)).build();

    }

    @GetMapping("/storefront/status/{orderStatus}")
    public ApiResponse<List<OrderResponse>> findAllByUserIdAndStatus(
            @PathVariable("orderStatus") OrderStatus orderStatus
    ) {
        List<OrderResponse> orderDtos = orderService.findAllByUserAndStatus(orderStatus);
        return ApiResponse.<List<OrderResponse>>builder().result(orderDtos).build();
    }
    @PutMapping("/storefront/{orderId}/status/{orderStatus}")
    public ApiResponse<Void> updateStatusOrderById(
            @RequestBody OrderStatusRequest request
            ) {
        orderService.updateStatusOrderById(request);
        return ApiResponse.<Void>builder().result(null).message("update order status successfully").build();
    }
}
