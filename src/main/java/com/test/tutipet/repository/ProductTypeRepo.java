package com.test.tutipet.repository;

import com.test.tutipet.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductType, Integer> {
}
