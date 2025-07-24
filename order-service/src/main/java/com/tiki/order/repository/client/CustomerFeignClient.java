package com.tiki.order.repository.client;

import com.tiki.order.dto.ApiResponse;
import com.tiki.order.dto.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profiles", url = "http://localhost:8081/api/profiles")
public interface CustomerFeignClient {

    @GetMapping("/storefront/customer/{id}")
    ApiResponse<CustomerResponse> getCustomerById(@PathVariable("id") String id);
}