package com.tiki.cart.repository.client;

import com.tiki.cart.dto.ApiResponse;
import com.tiki.cart.dto.response.ProductVariantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "http://localhost:8090/api/products")
public interface ProductFeignClient {

    @GetMapping("/storefront/products-variant/{id}")
    ApiResponse<ProductVariantResponse> getByProductId(@PathVariable("id") Long id);
}