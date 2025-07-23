package com.tiki.cart.controller;

import com.tiki.cart.dto.ApiResponse;
import com.tiki.cart.dto.response.CartResponse;
import com.tiki.cart.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @PostMapping("/storefront/add-to-cart/{productId}")
    public ResponseEntity<Void> addToCart(@PathVariable("productId") Long productId) {
        cartService.addToCart(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/storefront/{cartId}")
    public ResponseEntity<Void> removeCart(@PathVariable("cartId") String cartId) {
        cartService.removeCartByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/storefront")
    public ResponseEntity<Void> removeCart() {
        cartService.removeCartOfLoggedUser();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/storefront/selection/{selection}")
    public ResponseEntity<Void> updateSlectionCart(@PathVariable("selection") boolean selection) {
        cartService.updateSelectedOfUser(selection);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront/products/{productId}")
    public ApiResponse<CartResponse> listCarts(
            @PathVariable("productId") Long productId
    ) {
        return ApiResponse.<CartResponse>builder().result(cartService.findByProductAndUser(productId)).build();
    }

    @GetMapping("/storefront/{customerId}")
    public ResponseEntity<List<CartResponse>> listCarts(@PathVariable("customerId") String customerId) {
        return ResponseEntity.ok().body(cartService.getCartsForCustomer(customerId));
    }

    @PutMapping("/storefront/{cartId}/quantity/{quantity}")
    public ResponseEntity<Void> updateQuantity(@PathVariable("cartId") String cartId,
                                               @PathVariable("quantity") int quantity) {
        cartService.updateQuantity(cartId, quantity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/storefront/{cartId}/selection/{selection}")
    public ResponseEntity<Void> updateSelection(@PathVariable("cartId") String cartId,
                                                @PathVariable("selection") boolean selection) {
        cartService.updateSelected(cartId, selection);
        return ResponseEntity.noContent().build();
    }

}