package com.test.tutipet.dtos.promotions;

import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.PromotionTarget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record PromotionRes (
        Long id,
        String name,
        String code,
        BigDecimal value,
        PromotionTarget target,
        ZonedDateTime fromTime,
        ZonedDateTime toTime,
        DiscountType discountType,
        EnableStatus enableStatus,
        List<ProductRes> products
) implements Serializable {
}
