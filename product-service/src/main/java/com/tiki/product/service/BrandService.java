package com.tiki.product.service;

import com.tiki.product.dto.request.BrandCreateRequest;
import com.tiki.product.entity.Brand;
import com.tiki.product.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BrandService {

    BrandRepository brandRepository;


    public void createBrand(BrandCreateRequest request) {
        // check brand name is duplicated
        if (checkExitedBrand(request.name(), null)) {
            throw new RuntimeException("");
        }
        Brand brand = Brand.builder()
                .name(request.name())
                .description(request.description())
                .build();
        brandRepository.save(brand);
    }

    private boolean checkExitedBrand(String name, Integer id) {
        return brandRepository.checkExited(name, id).isPresent();
    }

}
