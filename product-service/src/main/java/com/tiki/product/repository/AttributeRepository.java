package com.tiki.product.repository;

import com.tiki.product.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

    Optional<Attribute> findByName(String name);
}
