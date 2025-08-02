package com.tiki.order.service;

import com.tiki.order.command.CreateOrderCommand;
import com.tiki.order.dto.OrderDetailDto;
import com.tiki.order.dto.request.OrderDetailRequest;
import com.tiki.order.dto.request.OrderRequest;
import com.tiki.order.dto.response.*;
import com.tiki.order.entity.Order;
import com.tiki.order.entity.OrderDetail;
import com.tiki.order.enums.OrderStatus;
import com.tiki.order.repository.OrderDetailRepository;
import com.tiki.order.repository.OrderRepository;
import com.tiki.order.repository.client.CustomerFeignClient;
import com.tiki.order.repository.client.ProductFeignClient;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;

    CommandGateway commandGateway;

    ProductFeignClient productFeignClient;
    CustomerFeignClient customerFeignClient;

    public Long createOrder(OrderRequest orderPostDto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = Order.builder()
                .note(orderPostDto.note())
                .receiverAddress(orderPostDto.receiverAddress())
                .receiverName(orderPostDto.receiverName())
                .receiverPhoneNumber(orderPostDto.receiverPhoneNumber())
                .status(OrderStatus.PENDING)
                .customerId(userId)
                .createdAt(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepository.saveAndFlush(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderPostDetail : orderPostDto.orderDetails()) {
            OrderDetail orderDetail = OrderDetail
                    .builder()
                    .order(savedOrder)
                    .productVariantId(orderPostDetail.productId())
                    .quantity(orderPostDetail.quantity())
                    .price(orderPostDetail.price())
                    .build();
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);

        List<OrderDetailDto> orderDetailDtos = orderPostDto.orderDetails().stream().map(orderDetailRequest -> new OrderDetailDto(orderDetailRequest)).collect(Collectors.toList());
        commandGateway.send(new CreateOrderCommand(order, orderDetailDtos));
        return savedOrder.getId();
    }


    public List<OrderGetListResponse> findAll() {
        List<Order> orders = orderRepository.findAllCustom();
        List<OrderGetListResponse> orderDtos = orders.stream().map(order -> {
            CustomerResponse customerDto = customerFeignClient.getCustomerById(order.getCustomerId()).getResult();
            List<OrderDetailResponse> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductVariantId();
                ProductVariantResponse productVariantDto = productFeignClient.getByProductId(productId).getResult();
                return OrderDetailResponse.from(orderDetail, productVariantDto);
            }).toList();
            return OrderGetListResponse.from(order, orderDetailDtos, customerDto);
        }).toList();
        return orderDtos;
    }


    public OrderResponse findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        List<OrderDetailResponse> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
            Long productId = orderDetail.getProductVariantId();
            ProductVariantResponse productVariantResponse = productFeignClient.getByProductId(productId).getResult();
            return OrderDetailResponse.from(orderDetail, productVariantResponse);
        }).toList();

        return OrderResponse.from(order, orderDetailDtos);
    }


    public List<OrderResponse> findAllByUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderResponse> orderDtos = orders.stream().map(order -> {
            List<OrderDetailResponse> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductVariantId();
                ProductVariantResponse productVariantResponse = productFeignClient.getByProductId(productId).getResult();
                return OrderDetailResponse.from(orderDetail, productVariantResponse);
            }).toList();
            return OrderResponse.from(order, orderDetailDtos);
        }).toList();
        return orderDtos;
    }


    public List<OrderResponse> findAllByUserAndStatus(OrderStatus status) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = orderRepository.findAllByUserIdAndStatus(userId, status);
        List<OrderResponse> orderDtos = orders.stream().map(order -> {
            List<OrderDetailResponse> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductVariantId();
                ProductVariantResponse productVariantDto = productFeignClient.getByProductId(productId).getResult();
                return OrderDetailResponse.from(orderDetail, productVariantDto);
            }).toList();
            return OrderResponse.from(order, orderDetailDtos);
        }).toList();
        return orderDtos;
    }


    @Transactional
    public void updateStatusOrderById(Long orderId, OrderStatus orderStatus) {
        orderRepository.updateStatusById(orderId, orderStatus);
    }


    public List<OrderGetListResponse> findAllByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findAllByStatus(orderStatus);
        List<OrderGetListResponse> orderDtos = orders.stream().map(order -> {
            CustomerResponse customerDto = customerFeignClient.getCustomerById(order.getCustomerId()).getResult();
            List<OrderDetailResponse> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductVariantId();
                ProductVariantResponse productVariantDto = productFeignClient.getByProductId(productId).getResult();
                return OrderDetailResponse.from(orderDetail, productVariantDto);
            }).toList();
            return OrderGetListResponse.from(order, orderDetailDtos, customerDto);
        }).toList();
        return orderDtos;
    }
}
