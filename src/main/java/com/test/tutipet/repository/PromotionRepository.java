package com.test.tutipet.repository;

import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.ObjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("select p from Promotion p where p.objectStatus = 'ACTIVE'")
    List<Promotion> findAllActivePromotions();

    @Query("SELECT p FROM Promotion p " +
            "WHERE p.fromTime <= :startTime " +
            "AND p.toTime >= :now And p.objectStatus = 'ACTIVE'")
    List<Promotion> findAllActivePromotionsByLiveAndUpcoming(@Param("now") ZonedDateTime now, @Param("startTime") ZonedDateTime startTime);

    Page<Promotion> findByNameContainingAndObjectStatus(String name, ObjectStatus objectStatus, Pageable pageable);

    @Query("select count(p) > 0 from Promotion p " +
            "where p.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE and p.code = ?1")
    boolean existsByCode(String code);

    @Query("SELECT COUNT(p) > 0 FROM Promotion p WHERE p.code = :code " +
            "AND p.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE" +
            " AND EXISTS (SELECT u FROM User u WHERE u.email = :email)")
    boolean existsByCodeAndUserEmail(@Param("code") String code, @Param("email") String email);


    @Query("select p from Promotion p WHERE p.code = :code " +
    "AND p.objectStatus = com.test.tutipet.enums.ObjectStatus.ACTIVE " +
    "AND EXISTS (SELECT u FROM User u WHERE u.email = :email)")
    Optional<Promotion> findByCodeAndUserEmail(@Param("code") String code, @Param("email") String email);
}
