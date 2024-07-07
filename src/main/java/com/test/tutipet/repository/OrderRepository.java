package com.test.tutipet.repository;

import com.test.tutipet.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.user.id = ?1 and o.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE")
    Optional<Order> findByUserId(long id);

    @Query("select o from Order o where o.user.email = ?1 and o.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE")
    List<Order> findAllByUserEmail(String email);

    @Query("select o from Order o where o.user.email = :email " +
            "and o.code = :code")
    Optional<Order> findByUserEmailAndCode(@Param("email") String email,@Param("code") String code);

    @Query("select o from Order o where o.user.email = :email  and o.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE")
    Optional<Order> findByUserEmail(@Param("email") String email);
}
