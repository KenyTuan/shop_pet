package com.test.tutipet.repository;

import com.test.tutipet.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCartRepo extends JpaRepository<ProductCart, Long> {
}
