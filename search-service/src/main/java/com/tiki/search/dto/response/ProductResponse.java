package com.tiki.search.dto.response;

import com.tiki.search.entity.Attribute;
import com.tiki.search.entity.Brand;
import com.tiki.search.entity.Category;
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
}
