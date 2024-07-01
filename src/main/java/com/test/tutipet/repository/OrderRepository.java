package com.test.tutipet.repository;

import com.test.tutipet.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.user.id = ?1 and o.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE")
    Optional<Order> findByUserId(long id);
}
