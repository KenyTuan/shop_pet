package com.test.tutipet.dtos.productCarts;

import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.entity.Product;

import java.math.BigDecimal;

public record ProductCartRes(
        Long id,
        int quantity,
        ProductRes product
) {
}
