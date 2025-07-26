package com.tiki.product.service;


import com.tiki.product.dto.request.AttributeRequest;
import com.tiki.product.dto.request.ProductCreationRequest;
import com.tiki.product.dto.request.ProductImageCreateRequest;
import com.tiki.product.dto.request.ProductVariantCreateRequest;
import com.tiki.product.dto.response.*;
import com.tiki.product.entity.*;
import com.tiki.product.repository.*;
import com.tiki.product.ultility.SkuGenerator;
import com.tiki.product.ultility.SlugGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    ProductImageRepository productImageRepository;
    VariantAttributeValueRepository variantAttributeValueRepository;


    public ProductResponse getById(Long id) {
        Product product = productRepository.findByIdCustom(id).orElseThrow();

        CategoryResponse categoryResponse = CategoryResponse.from(product.getCategory());
        BrandResponse brandResponse = BrandResponse.from(product.getBrand());

        // get productVariants
        List<ProductVariantResponse> productVariants = getProductVariants(product);


        // get productImages
        List<ProductImageResponse> productImages = getProductImages(product);

        Map<String, String> productAttributeValues = getProductAttributeValues(product);

        return ProductResponse.from(product, brandResponse, categoryResponse, productVariants, productImages, productAttributeValues);
    }

    private Map<String, String> getProductAttributeValues(Product product) {
        List<ProductAttributeValue> productAttributeValues = productAttributeValueRepository.findByProduct(product.getId());
        Map<String, String> variantAttributeValueMap = new HashMap<>();
        productAttributeValues.forEach(variantAttributeValue -> {
            variantAttributeValueMap.put(variantAttributeValue.getAttribute().getName(), variantAttributeValue.getValue());
        });
        return variantAttributeValueMap;
    }

    private List<ProductImageResponse> getProductImages(Product product) {
        List<ProductImageResponse> productImages = productImageRepository.findByProductId(product.getId()).stream()
                .map(productImage -> ProductImageResponse.from(productImage)).collect(Collectors.toList());
        return productImages;
    }

    private List<ProductVariantResponse> getProductVariants(Product product) {
        List<ProductVariantResponse> productVariantResponses = new ArrayList<>();
        product.getProductVariants().forEach(productVariant -> {
            List<VariantAttributeValue> variantAttributeValues = variantAttributeValueRepository.findByProductVariant(productVariant.getId());
            Map<String, String> variantAttributeValueMap = new HashMap<>();
            variantAttributeValues.forEach(variantAttributeValue -> {
                variantAttributeValueMap.put(variantAttributeValue.getAttribute().getName(), variantAttributeValue.getValue());
            });
            ProductVariantResponse productVariantResponse = ProductVariantResponse.from(productVariant, variantAttributeValueMap);
            productVariantResponses.add(productVariantResponse);
        });
        return productVariantResponses;
    }


//    @PreAuthorize("hasRole('SELLER')")
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
                .slug(SlugGenerator.toSlug(request.name()))
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

        saveProductVariants(request.productVariants(), product.getId());
        saveProductImages(request.productImages(), product.getId());

    }

    private void saveProductVariants(List<ProductVariantCreateRequest> requests, Long productId) {
        if (!requests.isEmpty()) {
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
    }

    public void createAttribute(AttributeRequest request) {
        Attribute attribute = Attribute.builder()
                .name(request.name())
                .dataType(request.dataType())
                .unit(request.unit())
                .build();
        attributeRepository.save(attribute);
    }

    private void saveProductImages(List<ProductImageCreateRequest> productImageCreateRequests, Long productId) {
       if (!productImageCreateRequests.isEmpty()) {
           Product product = productRepository.findById(productId).orElseThrow();
           for (ProductImageCreateRequest productImageRequest : productImageCreateRequests) {
               ProductImage productImage = ProductImage.builder()
                       .product(product)
                       .url(productImageRequest.url())
                       .type(productImageRequest.type())
                       .sortOrder(productImageRequest.sortOrder())
                       .status(productImageRequest.status())
                       .build();
               Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(productImageRequest.productVariantId());
               if (productVariantOptional.isPresent()) {
                   ProductVariant productVariant = productVariantOptional.get();
                   productImage.setProductVariant(productVariant);
               }

               productImageRepository.save(productImage);
           }
       }
    }
}
