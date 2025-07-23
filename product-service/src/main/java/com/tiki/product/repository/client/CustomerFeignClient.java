package com.tiki.product.repository.client;

import com.tiki.product.dto.ApiResponse;
import com.tiki.product.dto.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profiles", url = "http://localhost:8081/api/profiles/customers")
public interface CustomerFeignClient {

    @GetMapping("/storefront/customer/{id}")
    ApiResponse<CustomerResponse> getCustomerById(@PathVariable("id") String id);
}