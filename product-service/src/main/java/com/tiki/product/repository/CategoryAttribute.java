package com.tiki.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAttribute extends JpaRepository<CategoryAttribute, Integer> {
}
