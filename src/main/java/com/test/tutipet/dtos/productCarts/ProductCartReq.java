package com.test.tutipet.dtos.productCarts;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProductCartReq {

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @NotNull(message = "Product Id is required.")
    private Long productId;

    @Positive(message = "Quantity must be greater than 0")
    private BigDecimal totalProduct;
}
