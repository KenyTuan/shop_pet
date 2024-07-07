package com.test.tutipet.dtos.promotions;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.PromotionTarget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter @Setter
public class UpdatePromotionReq {
    @NotBlank(message = MessageException.REQUIRED_NAME)
    private String name;

    @NotNull(message = MessageException.INVALID_TARGET)
    private PromotionTarget target;

    @NotNull(message = MessageException.INVALID_FROM_TIME)
    private ZonedDateTime fromTime;

    @NotNull(message = MessageException.INVALID_TO_TIME)
    private ZonedDateTime toTime;

    @NotNull(message = MessageException.INVALID_DISCOUNT_TYPE)
    private DiscountType discountType;

    @Positive(message = MessageException.REQUIRED_VALUE)
    private BigDecimal value;

    private List<Long> productIds;
}
