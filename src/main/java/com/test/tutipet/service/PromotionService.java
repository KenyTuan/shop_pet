package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.promotions.CreatePromotionReq;
import com.test.tutipet.dtos.promotions.PromotionRes;
import com.test.tutipet.dtos.promotions.UpdatePromotionReq;
import com.test.tutipet.enums.EnableStatus;

import java.util.List;

public interface PromotionService {

    List<PromotionRes> getAllPromotions();

    List<PromotionRes> getLiveAndUpcomingPromotions();

    PageRes<PromotionRes> getAllPromotionPage(String keySearch,int page, int size, String sortBy, String sortDir);

    PromotionRes getPromotionById(Long id);

    void validatePromotionByCode(String code, String token);

    PromotionRes createPromotion(CreatePromotionReq req);

    PromotionRes updatePromotion(UpdatePromotionReq req, long id);

    PromotionRes updatePromotionStatus(Long id, EnableStatus enableStatus);

    void deletePromotion(Long id);

}
