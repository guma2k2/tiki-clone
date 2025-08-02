package com.tiki.payment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long amount;
    private String bankCode ;
    private String bankTranNo ;
    private String cartType ;
    private String payDate;
    private Long orderId;
}
