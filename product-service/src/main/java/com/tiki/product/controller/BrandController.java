package com.tiki.product.controller;

import com.tiki.product.dto.ApiResponse;
import com.tiki.product.dto.request.BrandCreateRequest;
import com.tiki.product.dto.request.CategoryCreateRequest;
import com.tiki.product.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;


    @PostMapping("/brand")
    public ApiResponse<Void> createBrand(@RequestBody BrandCreateRequest request) {
        brandService.createBrand(request);
        return ApiResponse.<Void>
                 builder()
                .build();
    }
}
