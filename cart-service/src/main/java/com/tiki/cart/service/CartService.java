package com.tiki.cart.service;

import com.tiki.cart.dto.response.CartResponse;
import com.tiki.cart.dto.response.ProductVariantResponse;
import com.tiki.cart.entity.Cart;
import com.tiki.cart.repository.CartRepository;
import com.tiki.cart.repository.CartRepositoryCustom;
import com.tiki.cart.repository.client.ProductFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartService {
    CartRepository cartRepository;
    ProductFeignClient productFeignClient;
    CartRepositoryCustom cartRepositoryCustom;
    public void addToCart(Long productId) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cart = cartRepository.findByProductAndUser(productId, customerId);
        if (cart.isPresent()) {
            int currentQuantity = cart.get().getQuantity();
            cartRepositoryCustom.updateQuantity(cart.get().getId(), currentQuantity + 1);
        }else {
            Cart newCart = Cart.builder()
                    .quantity(1)
                    .productVariantId(productId)
                    .userId(customerId)
                    .selected(false)
                    .build();
            cartRepository.save(newCart);
        }
    }


    public void removeCartOfLoggedUser() {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartRepositoryCustom.deleteCartByUser(customerId);
    }


    public CartResponse findByProductAndUser(Long productId) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartRepository.findByProductAndUser(productId, customerId).orElseThrow();
        ProductVariantResponse productVariantDto = productFeignClient.getByProductId(productId).getResult();
        return CartResponse.fromModel(cart, productVariantDto);
    }

    public List<CartResponse> getCartsForCustomer(String customerId) {
        List<Cart> carts = cartRepositoryCustom.findByUserId(customerId);
        List<CartResponse> cartDtos = carts.stream().map(cart -> {
            Long productId = cart.getProductVariantId();
            ProductVariantResponse productVariantDto = productFeignClient.getByProductId(productId).getResult();
            return CartResponse.fromModel(cart, productVariantDto);
        }).toList();
        return cartDtos;
    }

    public void updateQuantity(String CartId, int quantity) {
        cartRepositoryCustom.updateQuantity(CartId, quantity);
    }

    public void updateSelected(String cartId, boolean isSelected) {
        cartRepositoryCustom.updateSelected(cartId, isSelected);
    }
    public void updateSelectedOfUser(boolean selection) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartRepositoryCustom.updateSelectedByUserId(customerId, selection);
    }

    public void removeCartByCartId(String cartId) {
        cartRepositoryCustom.deleteCart(cartId);
    }
}
