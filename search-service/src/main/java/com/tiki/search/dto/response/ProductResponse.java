package com.tiki.search.dto.response;

import com.tiki.search.entity.Attribute;
import com.tiki.search.entity.Brand;
import com.tiki.search.entity.Category;
import com.tiki.search.entity.Product;

import java.util.List;

public record ProductResponse(
         String sku,

         String name,

         String slug,

         Brand brand,

         Category category,

         Double price,

         String image,

         List<Attribute>attributes,

         Float rating,

         Integer reviewsCount
) {


    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getSku(), product.getName(), product.getSlug(), product.getBrand(), product.getCategory(), product.getPrice(), product.getImage(),
                product.getAttributes(), product.getRating(), product.getReviewsCount());
    }
}
