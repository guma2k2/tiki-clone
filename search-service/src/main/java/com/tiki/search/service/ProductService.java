package com.tiki.search.service;

import com.tiki.search.dto.response.ProductVariantResponse;
import com.tiki.search.entity.Product;
import com.tiki.search.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {

    ProductRepository productRepository;

    public void save(ProductVariantResponse response) {
        Product product = Product.fromProductVariantResponse(response);
        Product savedProduct = productRepository.save(product);
        log.info("Saved product " + savedProduct);
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
