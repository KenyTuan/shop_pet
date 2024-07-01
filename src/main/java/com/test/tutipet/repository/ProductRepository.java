package com.test.tutipet.repository;

import com.test.tutipet.entity.Product;
import com.test.tutipet.enums.ObjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingAndObjectStatus(String name, ObjectStatus objectStatus, Pageable pageable);
}
