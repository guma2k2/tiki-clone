package com.tiki.search.entity;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.List;

@Document(indexName = "products")
@Mapping(mappingPath = "mapping/product-mapping.json")
public class Product {

    private String sku;

    private Long productId;

    private String name;

    private String slug;

    private String description;

    private Brand brand;

    private Category category;

    private Double price;

    private String image;

    private String status;

    private List<Attribute> attributes;

    @Field(name = "avg_rating")
    private Float rating;

    @Field(name = "num_of_reviews")
    private Integer reviewsCount;

}
