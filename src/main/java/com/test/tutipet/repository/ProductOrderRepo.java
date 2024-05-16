package com.test.tutipet.repository;

import com.test.tutipet.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {
}
