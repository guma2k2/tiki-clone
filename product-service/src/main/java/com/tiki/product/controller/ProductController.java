package com.tiki.product.controller;

import com.tiki.product.dto.ApiResponse;
import com.tiki.product.dto.request.AttributeRequest;
import com.tiki.product.dto.request.ProductCreationRequest;
import com.tiki.product.dto.request.ProductVariantCreateRequest;
import com.tiki.product.dto.response.ProductResponse;
import com.tiki.product.entity.Product;
import com.tiki.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {

    ProductService productService;


    @PostMapping("/create")
    public ApiResponse<Void> createProduct(@RequestBody @Valid ProductCreationRequest request) {
        productService.createProduct(request);
        return ApiResponse.<Void>builder()
                .code(1201)
                .message("Product created")
                .build();
    }


    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .code(1201)
                .message("Getted product")
                .result(productService.getById(productId))
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
