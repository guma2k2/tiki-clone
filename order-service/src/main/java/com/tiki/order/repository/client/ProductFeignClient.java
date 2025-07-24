package com.tiki.order.repository.client;

import com.tiki.order.dto.ApiResponse;
import com.tiki.order.dto.response.ProductVariantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "http://localhost:8090/api/products")
public interface ProductFeignClient {

    @GetMapping("/storefront/products-variant/{id}")
    ApiResponse<ProductVariantResponse> getByProductId(@PathVariable("id") Long id);
}