package com.test.tutipet.repository;

import com.test.tutipet.entity.Product;
import com.test.tutipet.enums.ObjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.objectStatus = 'ACTIVE'")
    Set<Product> findAllActiveProducts();

    Page<Product> findPagedByNameContainingAndObjectStatus(String name, ObjectStatus objectStatus, Pageable pageable);

    @Query("select p from Product p where p.id IN :productId and p.objectStatus = 'ACTIVE'")
    List<Product> findAllByIdIn(List<Long> productId);

    Optional<Product> findByNameContaining(String name);
}
