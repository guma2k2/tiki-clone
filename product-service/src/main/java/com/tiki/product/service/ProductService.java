package com.tiki.product.service;


import com.tiki.product.dto.request.AttributeRequest;
import com.tiki.product.dto.request.ProductCreationRequest;
import com.tiki.product.dto.request.ProductImageCreateType;
import com.tiki.product.dto.request.ProductVariantCreateRequest;
import com.tiki.product.entity.*;
import com.tiki.product.repository.*;
import com.tiki.product.ultility.SkuGenerator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService {


    ProductRepository productRepository;
    ProductAttributeValueRepository productAttributeValueRepository;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    AttributeRepository attributeRepository;
    ProductVariantRepository productVariantRepository;
    private final ProductImageRepository productImageRepository;


    @PreAuthorize("hasRole('SELLER')")
    public void createProduct(ProductCreationRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var sellerId = authentication.getName();

        // check product name is unique
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow();
        Brand brand = brandRepository.findById(request.brandId()).orElseThrow();

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .category(category)
                .brand(brand)
                .slug("")
                .sellerId(sellerId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        request.attributes().entrySet().stream().forEach(entry -> {
            Attribute attribute = attributeRepository.findById(entry.getKey()).orElseThrow();

            ProductAttributeValue productAttributeValue = ProductAttributeValue.builder()
                    .value(entry.getValue())
                    .attribute(attribute)
                    .product(savedProduct)
                    .build();
            productAttributeValueRepository.save(productAttributeValue);
        });
    }

    public void createProductVariants(Long productId, List<ProductVariantCreateRequest> requests) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        List<ProductVariant> variants = new ArrayList<>();

        for (ProductVariantCreateRequest req : requests) {
            ProductVariant variant = new ProductVariant();
            variant.setProduct(product);
            variant.setPrice(req.price());
            variant.setQuantity(req.quantity());

            // Tự sinh SKU
            String sku = SkuGenerator.generateSku(product.getName(), req.attributes());
            variant.setSku(sku);

            // Gán attribute values
            List<VariantAttributeValue> values = req.attributes().entrySet().stream()
                    .map(e -> {
                        VariantAttributeValue val = new VariantAttributeValue();

                        Attribute attribute = attributeRepository.findByName(e.getKey()).orElseThrow();
                        val.setAttribute(attribute);
                        val.setValue(e.getValue());
                        val.setProductVariant(variant);
                        return val;
                    }).collect(Collectors.toList());

            variant.setAttributeValues(values);
            variants.add(variant);
        }

        productVariantRepository.saveAll(variants);
    }

    public void createAttribute(AttributeRequest request) {
        Attribute attribute = Attribute.builder()
                .name(request.name())
                .dataType(request.dataType())
                .unit(request.unit())
                .build();
        attributeRepository.save(attribute);
    }

    public void createProductImage(ProductImageCreateType productImageCreateType) {
        Product product = productRepository.findById(productImageCreateType.productId()).orElseThrow();
        ProductImage productImage = ProductImage.builder()
                .product(product)
                .url(productImageCreateType.url())
                .type(productImageCreateType.type())
                .sortOrder(productImageCreateType.sortOrder())
                .status(productImageCreateType.status())
                .build();
        Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(productImageCreateType.productVariantId());
        if (productVariantOptional.isPresent()) {
            ProductVariant productVariant = productVariantOptional.get();
            productImage.setProductVariant(productVariant)  ;
        }

        productImageRepository.save(productImage);
    }
}
