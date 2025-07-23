package com.tiki.cart.repository;

import com.tiki.cart.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepositoryCustom {
    Optional<Cart> findByProductAndUser(Long productId, String userId);
    void updateQuantity(String cartId, int quantity);
    void updateSelected(String cartId, boolean selected);
    void updateSelectedByUserId(String userId, boolean selected);
    void deleteCart(String cartId);
    void deleteCartByUser(String userId);
    List<Cart> findByUserId(String userId);
}