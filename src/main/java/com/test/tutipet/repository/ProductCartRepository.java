package com.test.tutipet.repository;

import com.test.tutipet.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

    @Query("select pc from ProductCart pc, Cart c, User u where u.id = ?1 " +
            "and u.id = c.user.id and c.id = pc.cart.id " +
            "and u.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE")
    Iterable<ProductCart> findByUserId(Long userId);

    List<ProductCart> findByProductId(Long productId);
}
