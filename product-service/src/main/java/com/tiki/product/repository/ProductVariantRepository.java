package com.tiki.product.repository;

import com.tiki.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {


    @Query("""
        select pv
        from ProductVariant pv 
        join fetch pv.product p
        join fetch p.category 
        join fetch p.brand
        where pv.id = :id 
    """)
    Optional<ProductVariant> findByIdCustom(Long id);
}
