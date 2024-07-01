package com.test.tutipet.dtos.productCarts;


import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductCartReq {

    @Positive(message = MessageException.REQUIRED_QUANTITY)
    private int quantity;

    @NotNull(message = MessageException.REQUIRED_PRODUCT)
    private Long productId;

}
