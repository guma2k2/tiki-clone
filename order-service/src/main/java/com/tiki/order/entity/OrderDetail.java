package com.tiki.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productVariantId;

    private Double price;

    private int quantity;

}