package com.tiki.search.service;

import com.tiki.search.dto.response.ProductVariantResponse;
import com.tiki.search.entity.Product;
import com.tiki.search.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;

    public void save(ProductVariantResponse response) {
        Product product = Product.fromProductVariantResponse(response);
        productRepository.save(product);
    }
    public void delete(Long id) {

        productRepository.deleteById(id);
    }
}
