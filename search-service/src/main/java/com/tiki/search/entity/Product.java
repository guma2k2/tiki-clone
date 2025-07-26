package com.tiki.search.entity;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "garments")
@Mapping(mappingPath = "sec05/index-mapping.json")
public class Product {
}
