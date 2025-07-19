package com.tiki.product.controller;

import com.tiki.product.dto.ApiResponse;
import com.tiki.product.dto.request.AttributeRequest;
import com.tiki.product.dto.request.ProductCreationRequest;
import com.tiki.product.dto.request.ProductVariantCreateRequest;
import com.tiki.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {

    ProductService productService;


    @PostMapping
    public ApiResponse<Void> createProduct(@RequestBody @Valid ProductCreationRequest request) {
        productService.createProduct(request);
        return ApiResponse.<Void>builder()
                .code(1201)
                .message("Product created")
                .build();
    }

    @PostMapping("/{productId}/variants")
    public ApiResponse<Void> createProductVariants(@RequestBody @Valid List<ProductVariantCreateRequest> request, @PathVariable Long productId) {
        productService.createProductVariants(productId, request);
        return ApiResponse.<Void>builder()
                .code(1201)
                .message("Product created")
                .build();
    }


    @PostMapping("/attribute")
    public ApiResponse<Void> createAttribute(@RequestBody @Valid AttributeRequest request) {
        productService.createAttribute(request);
        return ApiResponse.<Void>builder()
                .code(1201)
                .message("Attribute created")
                .build();
    }

}
