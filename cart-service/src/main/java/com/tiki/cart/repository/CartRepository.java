package com.tiki.cart.repository;

import com.tiki.cart.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    @Query("{ 'productVariantId': ?0, 'userId': ?1 }")
    Optional<Cart> findByProductAndUser(Long productId, String customerId);


}
