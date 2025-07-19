package com.tiki.product.service;


import com.tiki.product.dto.request.CategoryCreateRequest;
import com.tiki.product.dto.response.AttributeResponse;
import com.tiki.product.dto.response.CategoryGetByIdResponse;
import com.tiki.product.dto.response.CategoryResponse;
import com.tiki.product.entity.Category;
import com.tiki.product.entity.CategoryAttribute;
import com.tiki.product.repository.CategoryAttributeRepository;
import com.tiki.product.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryAttributeRepository categoryAttributeRepository;


    public void createCategory(CategoryCreateRequest request) {
        // check name is duplicated
        if (checkExitedCategory(request.name(), null)) {
            throw new RuntimeException("message");
        }
        Category category = Category.builder()
                .name(request.name())
                .build();
        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId()).orElseThrow();
            category.setParent(parent);
        }
        categoryRepository.save(category);
    }

    private boolean checkExitedCategory(String name, Integer id) {
        return categoryRepository.checkExited(name, id).isPresent();
    }

    public List<CategoryResponse> findAllCategoryParents() {
        return categoryRepository.findAllCategoryParents().stream().map(CategoryResponse::from).collect(Collectors.toList());
    }


    public CategoryGetByIdResponse getById(Integer id) {
        Category category = categoryRepository.findByIdCustom(id).orElseThrow();
        if (category.getChildren().size() > 0) {
            List<CategoryResponse> children = category.getChildren().stream().map(cat -> CategoryResponse.from(cat)).collect(Collectors.toList());
            return CategoryGetByIdResponse.from(category, children);
        }
        return CategoryGetByIdResponse.from(category, null);
    }


    public List<AttributeResponse> findAttributesByCategoryId(Integer categoryId) {
        List<CategoryAttribute> categoryAttributes = categoryAttributeRepository.findByCategory(categoryId);
        return categoryAttributes.stream().map(categoryAttribute -> AttributeResponse.from(categoryAttribute.getAttribute())).collect(Collectors.toList());
    }


}
