package com.test.tutipet.repository;

import com.test.tutipet.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join c.user u where u.id = ?1 ")
    Optional<Cart> findByUserId( long userId);


}
