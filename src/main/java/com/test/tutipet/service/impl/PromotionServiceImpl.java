package com.test.tutipet.service.impl;

import com.test.tutipet.repository.PromotionRepo;
import com.test.tutipet.service.PromotionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepo promotionRepo;
}
