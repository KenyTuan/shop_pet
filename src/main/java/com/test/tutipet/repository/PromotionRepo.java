package com.test.tutipet.repository;

import com.test.tutipet.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo extends JpaRepository<Promotion, Long> {
}
