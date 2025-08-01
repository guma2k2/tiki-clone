package com.tiki.search.repository.client;

import com.tiki.search.dto.ApiResponse;
import com.tiki.search.dto.response.ProductVariantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "http://localhost:8082/products")
public interface ProductFeignClient {
    @GetMapping("/variants/{id}")
    ApiResponse<ProductVariantResponse> getProductVariantById(@PathVariable("id") Long id);
}
