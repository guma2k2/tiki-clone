package com.tiki.product.controller;

import com.tiki.product.dto.ApiResponse;
import com.tiki.product.dto.request.CategoryCreateRequest;
import com.tiki.product.dto.response.AttributeResponse;
import com.tiki.product.dto.response.CategoryGetByIdResponse;
import com.tiki.product.dto.response.CategoryResponse;
import com.tiki.product.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/category/parents")
    public ApiResponse<List<CategoryResponse>> getAllCategoryParents() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .message("get all category parents success")
                .result(categoryService.findAllCategoryParents())
                .build();
    }

    @PostMapping("/category")
    public ApiResponse<Void> createCategory(@RequestBody CategoryCreateRequest request) {
        categoryService.createCategory(request);
        return ApiResponse.<Void>
                    builder()
                    .build();
    }


    @GetMapping("/category/{id}")
    public ApiResponse<CategoryGetByIdResponse> getCategoryById(@PathVariable int id) {
        return ApiResponse.<CategoryGetByIdResponse>builder()
                .message("get category success")
                .result(categoryService.getById(id))
                .build();
    }


    @GetMapping("/category/{id}/attributes")
    public ApiResponse<List<AttributeResponse>> getAttributesByCategoryId(@PathVariable int id) {
        return ApiResponse.<List<AttributeResponse>>builder()
                .message("get attributes success")
                .result(categoryService.findAttributesByCategoryId(id))
                .build();
    }


}
