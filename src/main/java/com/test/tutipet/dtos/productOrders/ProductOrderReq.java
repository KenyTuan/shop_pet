package com.test.tutipet.dtos.productOrders;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductOrderReq {

    @NotNull(message = MessageException.REQUIRED_PRODUCT)
    private Long product_id;

    @Positive(message = MessageException.REQUIRED_QUANTITY)
    private int quantity;

}
