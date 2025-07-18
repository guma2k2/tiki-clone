package com.tiki.product.repository;

import com.tiki.product.entity.VariantAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantAttributeValueRepository extends JpaRepository<VariantAttributeValue, Long> {
}
