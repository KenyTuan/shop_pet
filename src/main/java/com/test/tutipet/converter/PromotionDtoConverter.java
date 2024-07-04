package com.test.tutipet.converter;

import com.test.tutipet.dtos.promotions.CreatePromotionReq;
import com.test.tutipet.dtos.promotions.PromotionRes;
import com.test.tutipet.dtos.promotions.UpdatePromotionReq;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;

import java.util.List;

public class PromotionDtoConverter {
    public static PromotionRes toResponse(Promotion promotion) {
        return new PromotionRes(
                promotion.getId(),
                promotion.getCode(),
                promotion.getTarget(),
                promotion.getFromTime(),
                promotion.getToTime(),
                promotion.getDiscountType(),
                promotion.getEnableStatus(),
                ProductDtoConverter.toResponseList(promotion.getProducts())
        );
    }

    public static List<PromotionRes> toResponseList(List<Promotion> promotions) {
        return promotions.stream().map(PromotionDtoConverter::toResponse).toList();
    }

    public static Promotion toEntity(CreatePromotionReq req) {
        Promotion promotion = Promotion.builder()
                .target(req.getTarget())
                .fromTime(req.getFromTime())
                .toTime(req.getToTime())
                .discountType(req.getDiscountType())
                .value(req.getValue())
                .enableStatus(EnableStatus.ENABLED)
                .build();
        promotion.setObjectStatus(ObjectStatus.ACTIVE);
        return promotion;
    }

    public static Promotion toEntity(UpdatePromotionReq req) {
        Promotion promotion = Promotion.builder()
                .target(req.getTarget())
                .fromTime(req.getFromTime())
                .toTime(req.getToTime())
                .discountType(req.getDiscountType())
                .value(req.getValue())
                .enableStatus(EnableStatus.ENABLED)
                .build();
        promotion.setObjectStatus(ObjectStatus.ACTIVE);
        return promotion;
    }
}
