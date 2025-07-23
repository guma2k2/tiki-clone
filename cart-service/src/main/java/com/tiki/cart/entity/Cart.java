package com.tiki.cart.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("carts")
public class Cart {
    @MongoId
    private String id;
    private Long productVariantId;
    private String userId;
    private int quantity;
    private boolean selected;
}
