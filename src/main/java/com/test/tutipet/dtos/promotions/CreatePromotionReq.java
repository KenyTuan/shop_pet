package com.test.tutipet.dtos.promotions;


import com.test.tutipet.constants.MessageException;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.PromotionTarget;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter @Builder
public class CreatePromotionReq {

    @Pattern(regexp = "^(ORDER|PRODUCT)$", message = MessageException.INVALID_TARGET)
    private PromotionTarget target;

    @NotNull(message = MessageException.INVALID_FROM_TIME)
    private ZonedDateTime fromTime;

    @NotNull(message = MessageException.INVALID_TO_TIME)
    private ZonedDateTime toTime;

    @Pattern(regexp = "^(PERCENTAGE|SPECIFIC)$",message = MessageException.INVALID_DISCOUNT_TYPE)
    private DiscountType discountType;

    @Positive(message = MessageException.REQUIRED_VALUE)
    private BigDecimal value;

    @NotNull(message = MessageException.REQUIRED_LIST_PRODUCT)
    private List<Long> productIds;
}
