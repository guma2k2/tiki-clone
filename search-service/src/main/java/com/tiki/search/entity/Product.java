package com.tiki.search.entity;


import com.tiki.search.dto.response.ProductVariantResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Document(indexName = "products")
@Mapping(mappingPath = "es-config/product-mapping.json")
@Setting(settingPath = "es-config/product-setting.json")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    private Long id;

    private String sku;

    private String name;

    private String slug;

    private String description;

    private String brand;

    private String category;

    private Double price;

    @Field(name = "image_url")
    private String imageUrl;

    private String status;

    private List<Attribute> attributes;

    @Field(name = "avg_rating")
    private Float rating;

    @Field(name = "num_of_reviews")
    private Integer reviewsCount;

    public static Product fromProductVariantResponse(ProductVariantResponse response) {
        Product product = new Product();

        product.setId(response.id());
        product.setSku(response.sku());
        product.setName(response.name());
        product.setSlug(response.slug());
        product.setDescription(response.description());
        product.setBrand(response.brand());
        product.setCategory(response.category());
        product.setPrice(response.price());
        product.setImageUrl(response.imageUrl());
        product.setStatus(response.status());
        product.setRating(response.rating());
        product.setReviewsCount(response.reviewsCount());

        if (response.variantAttributeValue() != null) {
            List<Attribute> attributeList = Attribute.fromAttributeMap(response.variantAttributeValue());
            product.setAttributes(attributeList);
        } else {
            product.setAttributes(List.of()); // or null
        }

        return product;
    }
}
